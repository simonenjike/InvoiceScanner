
# Invoice Scanner Service  
## Phase 4 – Implementierung

**Autor:** Simone Njike  
**Datum:** 09.12.2025  
**Version:** 1.2  

---

## 1. Ziel und Umfang
Diese Phase beschreibt die konkrete technische Implementierung des *Invoice Scanner Service*.
Sie dient als technische Referenz für Entwicklung, Tests und Betrieb.

Der Fokus liegt auf:
- Aufbau der Code- und Paketstruktur
- Implementierung der REST-Schnittstelle
- Umsetzung des Geschäftsprozesses „Rechnung scannen“
- IBAN-Erkennung und Blacklist-Prüfung
- Fehlerbehandlung, Logging sowie Performance-Aspekten

Die Implementierung erfolgt in **Java 21** mit **Spring Boot 3.5.7**.

---

## 2. Projekt- und Paketstruktur
Die Anwendung ist modular aufgebaut und folgt einer klaren Schichtenarchitektur:

```
sim.refpro.invoicescanner
 ├── InvoiceScanApplication.java
 ├── controller
 │   └── InvoiceScanController.java
 ├── dto
 │   ├── InvoiceScanRequest.java
 │   └── InvoiceScanResponse.java
 ├── repository
 │   ├── BlacklistRepository.java
 │   └── InMemoryBlacklistRepository.java
 └── service
     ├── InvoiceScanService.java
     ├── InvoiceScanServiceImpl.java
     ├── domain
     │   └── IbanScanner.java
     └── infra
         ├── HttpDownloader.java
         └── PdfTextExtractor.java
```

**Begründung der Schichtung:**
- *controller*: REST-Schnittstelle, keine Geschäftslogik  
- *dto*: JSON-Request/Response-Objekte  
- *repository*: Zugriff auf Blacklist-Daten  
- *service*: Orchestrierung des Anwendungsfalls  
- *domain*: fachliche Logik (IBAN-Erkennung)  
- *infra*: technische Infrastruktur (HTTP, PDF)  

---

## 3. Build- und Laufzeitumgebung
- Programmiersprache: Java 21  
- Framework: Spring Boot 3.5.7  
- Build-Tool: Maven (inkl. Wrapper)  
- Laufzeit: Embedded Apache Tomcat  
- Standard-Port: 8080  
- Build-Artefakt: ausführbares JAR (`invoicescanner.jar`)  

---

## 4. Abhängigkeiten
- spring-boot-starter-web – REST-API & Tomcat  
- spring-boot-starter-validation – Bean Validation  
- Apache PDFBox 3.0.2 – Textextraktion aus PDFs  
- iban4j 3.2.7 – IBAN-Validierung  
- spring-boot-starter-test – Testframework  

---

## 5. Konfiguration
In der aktuellen MVP-Version werden überwiegend Default-Werte verwendet.
Eine spätere Externalisierung in `application.properties` ist vorgesehen, z. B. für:
- HTTP-Timeouts  
- maximale PDF-Größe  
- Blacklist-Quelle  

---

## 6. REST-Controller – InvoiceScanController
Der Controller stellt den Endpunkt **POST `/api/v1/invoice-scan`** bereit.
Er validiert die Eingaben mittels Bean Validation und delegiert die Verarbeitung
an den Service-Layer. Bei erfolgreicher Verarbeitung wird HTTP 200 zurückgegeben.

---

## 7. Service-Layer

### InvoiceScanService
Definiert den Anwendungsfall „Rechnung scannen“.

### InvoiceScanServiceImpl
Orchestriert den Ablauf:
1. Download der PDF-Datei  
2. Textextraktion  
3. IBAN-Erkennung und Validierung  
4. Blacklist-Abgleich  
5. Aufbau des Response-Objekts  

---

## 8. DTOs und Validierung

### InvoiceScanRequest
- Feld: `invoiceUrl`  
- Pflichtfeld (`@NotBlank`)  

### InvoiceScanResponse
- `extractedIbans`  
- `blacklistedIbans`  
- `success`  
- `message`  

---

## 9. PdfTextExtractor
Extrahiert Text aus PDF-Dokumenten mittels Apache PDFBox.
Fehlerhafte oder leere Dokumente führen zu Exceptions.

---

## 10. IbanScanner
Erkennt IBAN-Kandidaten per regulärem Ausdruck, normalisiert diese
und validiert sie mithilfe von iban4j. Ungültige IBANs werden verworfen.

---

## 11. BlacklistRepository
Stellt den Abgleich erkannter IBANs mit einer In-Memory-Blacklist bereit.
Die Implementierung ist bewusst einfach gehalten und leicht austauschbar.

---

## 12. HttpDownloader
Lädt PDF-Dokumente über HTTP/HTTPS mit `java.net.http.HttpClient`.
Es werden Timeouts gesetzt und nur erfolgreiche HTTP-Statuscodes akzeptiert.

---

## 13. Fehlerbehandlung und HTTP-Statuscodes
- **200 OK** – erfolgreicher Scan  
- **400 Bad Request** – ungültige Eingaben  
- **500 Internal Server Error** – unerwartete Fehler  

---

## 14. Logging
- **INFO** – regulärer Ablauf  
- **WARN** – Timeouts oder ungewöhnliche Dokumente  
- **ERROR** – unerwartete Fehler (ohne sensible Daten)  

---

## 15. Sicherheit
Aktuell umgesetzt:
- DTO-Validierung  
- kein Zugriff auf lokale Dateien  

Geplant:
- HTTPS  
- Größenlimits  
- Rate-Limiting  

---

## 16. Performance- und Robustheitsaspekte
- In-Memory-Verarbeitung ausreichend für MVP  
- frühzeitiger Abbruch bei Fehlern  
- performanter Blacklist-Abgleich  

---

## 17. Beispielaufruf (cURL)
```bash
curl -X POST http://localhost:8080/api/v1/invoice-scan   -H "Content-Type: application/json"   -d '{ "invoiceUrl": "http://example.com/invoice.pdf" }'
```

---

## 18. Deployment
**Build:** `mvn clean package`  
**Start:** `java -jar invoicescanner.jar`  

Der Service läuft standalone auf Port 8080.
