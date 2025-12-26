
# Invoice Scanner Service  
## Phase 3 – System- und Softwareentwurf

**Autor:** Simone Njike  
**Datum:** 28.11.2025  
**Version:** 1.2  

---

## 1. Ziel und Abgrenzung
Dieses Dokument beschreibt den technischen System- und Softwareentwurf des *Invoice Scanner Service*.
Es konkretisiert die Anforderungen aus dem Lastenheft (Phase 2) und legt fest, wie die funktionalen
und nicht-funktionalen Anforderungen technisch umgesetzt werden.

---

## 2. Systemarchitektur
Der Invoice Scanner Service ist als leichtgewichtiger REST-Microservice umgesetzt.
Die technische Basis bilden **Java 21** und **Spring Boot 3.5.7**.

Die Anwendung folgt einer **mehrschichtigen Architektur**, um eine klare Trennung von
Verantwortlichkeiten sicherzustellen sowie Wartbarkeit und Erweiterbarkeit zu fördern.

### 2.1 Architekturdiagramm
Das Architekturdiagramm zeigt die Schichten **Controller**, **Service**, **Domain** und
**Infrastruktur** sowie deren Interaktion. Externe Bibliotheken sind klar von der
Geschäftslogik getrennt.

### 2.2 Schichten und Verantwortlichkeiten

**Controller-Schicht**
- Entgegennahme von HTTP-Requests
- Validierung der Eingabedaten
- Delegation an die Service-Schicht
- Abbildung von Fehlern auf HTTP-Statuscodes

**DTO-Schicht**
- Transportobjekte für Request und Response
- Bean Validation zur Eingabeprüfung
- Entkopplung von API und interner Logik

**Service-Schicht**
- Zentrale Geschäftslogik
- Orchestrierung des Scan-Ablaufs  
  PDF-Download → Textextraktion → IBAN-Erkennung → Blacklist-Abgleich

**Infra- und Domain-Schicht**
- PDF-Verarbeitung mit Apache PDFBox
- IBAN-Erkennung und -Validierung mit iban4j
- HTTP-Download mit Timeouts und Größenprüfung

### 2.3 Externe Bibliotheken
- **Apache PDFBox 3.0.2** – Textextraktion aus PDFs  
- **iban4j 3.2.7** – IBAN-Erkennung, Normalisierung und Validierung  

---

## 3. Komponentenmodell
Zentrale Komponenten des Systems sind:

- **InvoiceScanController** – REST-Endpunkt `/api/v1/invoice-scan`
- **InvoiceScanService / InvoiceScanServiceImpl** – Ablaufsteuerung
- **PdfTextExtractor** – Textextraktion aus PDFs
- **IbanScanner** – IBAN-Erkennung und Validierung
- **BlacklistRepository / InMemoryBlacklistRepository** – Verwaltung gesperrter IBANs
- **HttpDownloader** – Download der PDF-Dokumente

---

## 4. Schnittstellen (REST-API)

### POST `/api/v1/invoice-scan`

**Request:**  
`application/json` mit URL zur PDF-Datei

**Response:**  
JSON-Objekt mit:
- Liste erkannter IBANs
- Liste erkannter Blacklist-Treffer
- Status- und Hinweismeldungen

### HTTP-Statuscodes
- **200 OK** – erfolgreicher Scan  
- **400 Bad Request** – ungültige Eingabe  
- **404 Not Found** – PDF nicht erreichbar  
- **415 Unsupported Media Type** – Datei ist kein PDF  
- **422 Unprocessable Entity** – kein extrahierbarer Text  
- **500 Internal Server Error** – unerwarteter Fehler  

---

## 5. Datenmodell und DTOs

**InvoiceScanRequest**
- `invoiceUrl` (String, Pflichtfeld)

**InvoiceScanResponse**
- `extractedIbans` (List<String>)
- `blacklistedIbans` (List<String>)
- `success` (boolean)
- `message` (String)

---

## 6. Ablaufbeschreibung (Sequenz – Scan per URL)
1. Controller empfängt und validiert den Request
2. Service lädt das PDF herunter
3. Textextraktion aus dem Dokument
4. IBAN-Erkennung und Validierung
5. Abgleich mit der Blacklist
6. Aufbau des Response-Objekts
7. Rückgabe an den Client

---

## 7. Nicht-funktionale Anforderungen (Design-Umsetzung)

**Performance**
- Ziel: < 3 Sekunden für PDFs mit 1–5 Seiten
- Abbruch bei Timeouts

**Sicherheit**
- Keine persistente Speicherung sensibler Daten
- Validierung aller Eingaben

**Zuverlässigkeit**
- Robuste Verarbeitung fehlerhafter PDFs
- Klare Fehlerklassifizierung

**Wartbarkeit**
- Modulare Struktur
- Klare Verantwortlichkeiten

**Testbarkeit**
- Unit-Tests für Kernkomponenten
- Integrationstests für den Gesamtablauf

---

## 8. Fehlerbehandlung und Logging
Fehler werden eindeutig klassifiziert und auf HTTP-Statuscodes abgebildet.
Logging erfolgt in den Stufen **INFO**, **WARN** und **ERROR** ohne Speicherung sensibler Daten.

---

## 9. Konfiguration
Die Konfiguration erfolgt zentral über `application.properties`, u. a.:
- HTTP-Timeouts
- Maximale PDF-Größe
- Blacklist-Quelle

---

## 10. Qualitätssicherung und Tests
Die Architektur unterstützt automatisierte Unit- und Integrationstests zur Sicherstellung
der funktionalen und technischen Qualität.

---

## 11. Risiken und Maßnahmen
- **PDF ohne Text** → Fehlercode 422  
- **Langsame URLs** → Timeout & Abbruch  
- **Große PDFs** → Größenlimit  

---

## 12. Build, Laufzeit und Abhängigkeiten

**Build**
- Maven (`mvn clean package`)
- Ausführbares Spring-Boot-JAR

**Laufzeit**
- Java 21
- Embedded Tomcat
- Standard-Port 8080

**Abhängigkeiten**
- spring-boot-starter-web
- spring-boot-starter-validation
- apache pdfbox
- iban4j
- spring-boot-starter-test
