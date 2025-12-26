
# Invoice Scanner Service  
## Phase 2 – Initiierung / Lastenheft

**Autor:** Simone Njike  
**Datum:** 14.11.2025  
**Version:** 1.0  

---

## 1. Ziel der Phase
Ziel dieser Phase ist die vollständige Beschreibung der fachlichen und technischen Anforderungen
an den *Invoice Scanner Service*. Das Lastenheft bildet die verbindliche Grundlage für den
Systementwurf (Phase 3), die Implementierung (Phase 4) sowie die Testphase (Phase 5).

---

## 2. Projektbeschreibung
Der Invoice Scanner Service ist ein automatisierter REST-Dienst zur Analyse von
Rechnungsdokumenten im PDF-Format. Der Service erkennt und validiert enthaltene IBANs
und gleicht diese mit einer hinterlegten Blacklist ab, um potenzielle Risiken wie
Geldwäsche oder Betrug frühzeitig zu identifizieren.

---

## 3. Zielsetzung
Entwicklung einer automatisierten, zuverlässigen, leicht integrierbaren und
erweiterbaren Lösung zur Prüfung von Rechnungs-PDFs auf gesperrte Bankverbindungen.

---

## 4. Funktionale Anforderungen
- Download und Analyse von PDF-Dokumenten  
- Erkennung und Validierung von IBANs (ISO 13616)  
- Abgleich erkannter IBANs mit einer Blacklist  
- Bereitstellung eines REST-Endpunkts: **POST /api/v1/invoice-scan**  
- Rückgabe strukturierter JSON-Antworten  
- Robuste Fehlerbehandlung bei ungültigen Eingaben oder Dokumenten  

---

## 5. Nicht-funktionale Anforderungen
- **Performance:** Verarbeitung von Standard-PDFs (< 5 Seiten) in unter 3 Sekunden  
- **Sicherheit:** Keine persistente Speicherung sensibler Daten  
- **Wartbarkeit:** Klare Schichtenarchitektur (Controller, Service, DTO)  
- **Testbarkeit:** Unterstützung von Unit- und Integrationstests  

---

## 6. Liefergegenstände
- Vollständiger Quellcode des Invoice Scanner Service  
- Maven-Projekt (Java 21, Spring Boot)  
- Beispiel-Testdaten (PDFs)  
- Projektdokumentation  

---

## 7. Abnahmekriterien
- IBANs werden korrekt erkannt  
- Blacklist-Treffer werden eindeutig ausgewiesen  
- Anwendung ist lokal stabil ausführbar  

---

## 8. Systemumgebung
- Java 21  
- Spring Boot  
- Maven  
- Apache PDFBox  
- iban4j  

---

## 9. Ausblick
Optionale Erweiterungen umfassen KI-gestützte Dokumentenanalyse sowie zusätzliche
Prüfmechanismen.
