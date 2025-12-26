
# Invoice Scanner Service  
## Phase 1 – Voruntersuchung / Durchführbarkeitsuntersuchung

**Autor:** Simone Njike  
**Datum:** 12.11.2025  
**Version:** 1.0  

---

## 1. Ziel der Voruntersuchung
Ziel dieser Phase ist es, die fachliche, technische und organisatorische Durchführbarkeit des Projekts *Invoice Scanner Service* zu bewerten. Die Voruntersuchung bildet die Grundlage für die Entscheidung, ob das Projekt initiiert und umgesetzt werden soll.

---

## 2. Prüfung der Kompetenz

### 2.1 Fachliche Kompetenz
Ich verfüge über fundierte Kenntnisse in der Java-Entwicklung, insbesondere im Umgang mit Spring Boot, Maven und der Entwicklung REST-basierter Services.

Die fachliche Aufgabenstellung – das Erkennen gesperrter (blacklisted) IBANs in Rechnungs-PDFs – ist klar definiert, praxisnah und entspricht realen Anforderungen aus dem Finanz- und Compliance-Umfeld.

### 2.2 Technische Kompetenz
Die geplante Systemumgebung basiert auf Java 21 und Spring Boot 3.5.7.

Die eingesetzten Bibliotheken **Apache PDFBox** zur Textextraktion sowie **iban4j** zur IBAN-Validierung sind bewährte und stabile Open-Source-Komponenten.

Die Leistungsfähigkeit des Systems hängt von der Größe der PDF-Dateien und der Anzahl gleichzeitiger Anfragen ab. Diese Aspekte können bei Bedarf durch geeignete Maßnahmen wie Timeouts, Größenlimits und Parallelisierung optimiert werden.

### 2.3 Personelle Kompetenz
Das Projekt kann von mir als Entwicklerin eigenständig umgesetzt werden.

Die notwendigen fachlichen und technischen Kenntnisse sind vorhanden, zusätzliche personelle Ressourcen sind nicht notwendig.

---

## 3. Auswahl des Produktes
Entwickelt wird ein *Invoice Scanner Service*, der eingereichte PDF-Rechnungen automatisiert auf gesperrte IBANs prüft, um potenzielle Risiken wie Geldwäsche frühzeitig zu erkennen.

Der Service soll als eigenständiger REST-Service betrieben werden und sich leicht in bestehende Systeme integrieren lassen.

---

## 4. Voruntersuchung des Produktes

### 4.1 Ist-Zustand
Derzeit erfolgt die Prüfung von Rechnungsdokumenten auf gesperrte IBANs manuell oder gar nicht. Dieser Prozess ist zeitaufwändig, fehleranfällig und nicht skalierbar.

### 4.2 Soll-Zustand
Ein automatisierter Dienst soll:
- PDF-Rechnungen herunterladen  
- den Textinhalt extrahieren  
- enthaltene IBANs erkennen und validieren  
- diese mit einer hinterlegten Blacklist abgleichen  

Bei einem Blacklist-Treffer wird ein entsprechender Hinweis oder Fehler zurückgegeben.

---

## 5. Festlegen der Hauptanforderungen

### 5.1 Hauptfunktionen
- Übergabe einer PDF-URL an den Service  
- Download und Analyse der PDF-Datei  
- Erkennung und Validierung von IBANs  
- Abgleich mit einer Blacklist  
- Rückgabe eines strukturierten Analyseergebnisses  

Eine spätere Erweiterung (z. B. zusätzliche Betrugsprüfungen) soll möglich sein.

### 5.2 Hauptdaten
**Eingabedaten:**  
- URL zu einer PDF-Datei  

**Ausgabedaten:**  
- Liste erkannter IBANs  
- Liste erkannter Blacklist-Treffer  
- Status- und Hinweismeldungen  

### 5.3 Hauptleistungen
- Verarbeitung mehrseitiger PDF-Dokumente  
- Erkennung mehrerer IBANs pro Dokument  
- Ziel-Antwortzeit: unter 3 Sekunden bei Standarddokumenten  

### 5.4 Hauptaspekte der Benutzungsschnittstelle
- REST-konformes API-Design  
- Kommunikation über HTTP POST  
- JSON als Austauschformat  
- Nutzung gängiger HTTP-Statuscodes  

### 5.5 Haupt-Qualitätsmerkmale
- **Zuverlässigkeit:** korrekte Erkennung und Validierung von IBANs  
- **Wartbarkeit:** modularer Aufbau (Controller, Services, DTOs)  
- **Erweiterbarkeit:** einfache Integration zusätzlicher Prüfungen  
- **Benutzerfreundlichkeit:** klare API-Dokumentation und verständliche Fehlermeldungen  
- **Testbarkeit:** gezielte Unit- und Integrationstests  

---

## 6. Entscheidung

### 6.1 Bewertung
Die technische und fachliche Umsetzung des Projekts ist vollständig durchführbar.

### 6.2 Ergebnis
**TOP – Die Projektinitiierung kann beginnen.**
