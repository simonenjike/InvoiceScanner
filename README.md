
# Invoice Scanner Service

Ein **REST-basierter Spring-Boot-Microservice**, der Rechnungen im PDF-Format automatisiert analysiert,
enthaltene **IBANs erkennt**, validiert und mit einer **Blacklist** abgleicht.

Ziel: **Risiken wie Fehlüberweisungen, Betrug oder Compliance-Verstöße frühzeitig erkennen.**

---

## Features
- Download von Rechnungs-PDFs über HTTP/HTTPS  
- Textextraktion aus PDFs (Apache PDFBox)  
- IBAN-Erkennung & -Validierung (iban4j)  
- Blacklist-Abgleich  
- REST-API mit JSON-Response  
- Unit-, Integrations- & Smoke-Tests  
- Bereitstellung als ausführbares Spring-Boot-JAR  

---

## Architektur (Kurzüberblick)
Der Service folgt einer **klaren Schichtenarchitektur**:

```
Controller → Service → Domain → Repository / Infrastructure
```

- **Controller**: REST-Endpunkte  
- **Service**: Orchestrierung der Geschäftslogik  
- **Domain**: Fachliche Logik (IBAN-Erkennung)  
- **Infrastructure**: PDF- & HTTP-Verarbeitung  
- **Repository**: Blacklist-Zugriff  

Details siehe:  
            [`docs/Technisches_Referenzdokument.md`](docs/Technisches_Referenzdokument.md)

---

## Technologiestack
- **Java 21**
- **Spring Boot 3.5.7**
- **Maven**
- **Apache PDFBox**
- **iban4j**
- **JUnit 5**

---

## Build & Start

### Build
```bash
mvn clean package
```

### Start
```bash
java -jar target/invoicescanner.jar
```

Service läuft auf **http://localhost:8080**

---

## REST API

### Endpoint
```http
POST /api/v1/invoice-scan
```

### Request
```json
{
  "invoiceUrl": "http://example.com/invoice.pdf"
}
```

### Response (Beispiel)
```json
{
  "extractedIbans": ["DE89370400440532013000"],
  "blacklistedIbans": ["DE89370400440532013000"],
  "success": true,
  "message": "Blacklist IBAN detected"
}
```

---

## Projektdokumentation

Die vollständige Projektdokumentation befindet sich im Ordner [`docs/`](docs):

- **Management Summary**  
  → [`docs/Management_Summary.md`](docs/Management_Summary.md)

- **Projektstatusübersicht**  
  → [`docs/Projektstatusuebersicht.md`](docs/Projektstatusuebersicht.md)

- **Technisches Referenzdokument**  
  → [`docs/Technisches_Referenzdokument.md`](docs/Technisches_Referenzdokument.md)

- **Projektphasen**
  - Phase 1 – Voruntersuchung  
  - Phase 2 – Lastenheft  
  - Phase 3 – System- & Softwareentwurf  
  - Phase 4 – Implementierung  
  - Phase 5 – Test & Integration  
  - Phase 6 – Einführung & Projektabschluss  

---

## Tests
- **Unit-Tests** (fachliche Logik)  
- **Integrationstests** (REST-Endpunkt)  
- **Smoke-Test** nach Deployment  

Alle Tests werden automatisiert über Maven ausgeführt.

---

## Sicherheit & Qualität
- Validierung aller Eingaben  
- Keine persistente Speicherung sensibler Daten  
- Klare HTTP-Statuscodes  
- Strukturierte Fehlerbehandlung  

---

## Ausblick
Mögliche Erweiterungen:
- Anbindung externer Blacklist-Dienste  
- Unterstützung weiterer Dokumenttypen  
- Authentifizierung & Autorisierung  
- Docker-Containerisierung  
- Monitoring & Dashboard  

---

## Autor
**Simone Njike**  
Invoice Scanner Service – Referenzprojekt  

---

## Lizenz
Dieses Projekt dient zu **Demonstrations- und Lernzwecken**.
