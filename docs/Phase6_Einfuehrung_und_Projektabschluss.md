
# Invoice Scanner Service  
## Phase 6 – Einführung und Projektabschluss

**Autor:** Simone Njike  
**Datum:** 22.12.2025  
**Version:** 1.1  

---

## 1. Ziel der Phase
Ziel dieser Phase ist die Einführung des *Invoice Scanner Service* in eine lauffähige Umgebung
sowie der formale Abschluss des Projekts. Es wird beschrieben, wie die Anwendung bereitgestellt,
gestartet und überprüft wird. Abschließend erfolgt eine Gesamtbewertung des Projekts.

---

## 2. Bereitstellung des Systems
Der Invoice Scanner Service wird als **ausführbares Spring-Boot-JAR** bereitgestellt.

Das JAR-Artefakt wird mithilfe von Maven erzeugt und enthält alle notwendigen Abhängigkeiten.

**Build-Befehl:**
```bash
mvn clean package
```

Das erzeugte Artefakt befindet sich im Verzeichnis `target/` und kann direkt für das Deployment
verwendet werden.

---

## 3. Start des Services
Der Service wird über die Java Runtime gestartet:

```bash
java -jar invoicescanner.jar
```

Nach dem Start läuft die Anwendung standalone mit einem eingebetteten **Apache Tomcat**
auf dem Standard-Port **8080**.

---

## 4. Verifikation der Erreichbarkeit
Nach dem Start des Services wird die Erreichbarkeit des REST-Endpunkts überprüft.

**Beispiel:**
```text
POST http://localhost:8080/api/v1/invoice-scan
```

Eine erfolgreiche Antwort (HTTP 200) bestätigt die korrekte Inbetriebnahme des Systems.

---

## 5. Smoke-Test (Kurz-Funktionstest)
Zur grundlegenden Funktionsprüfung wird ein Smoke-Test durchgeführt.
Dabei wird eine bekannte Test-PDF-Datei über den REST-Endpunkt verarbeitet.

**Vorgehen:**
- Service starten  
- REST-Aufruf mit einer Test-PDF  
- Erwartung: HTTP 200 und gültige JSON-Antwort  

Der Smoke-Test bestätigt die grundsätzliche Funktionsfähigkeit nach dem Deployment.

---

## 6. Betrieb und Wartung
Der Service ist als **stateless Microservice** konzipiert und benötigt keine persistente
Datenhaltung.

Wartungsmaßnahmen beschränken sich auf:
- Aktualisierung der Blacklist  
- Updates der verwendeten Bibliotheken  
- Überwachung von Logs und Laufzeitverhalten  

---

## 7. Projektergebnis
Alle definierten funktionalen und nicht-funktionalen Anforderungen wurden erfolgreich umgesetzt.
Der Invoice Scanner Service ist stabil, testbar und modular aufgebaut.

---

## 8. Projektrückblick
Das Projekt zeigt eine vollständige Umsetzung eines REST-basierten Java-Services von der
Anforderungsanalyse bis zur Einführung. Die gewählte Architektur ermöglicht eine einfache
Erweiterung und Wartung.

---

## 9. Ausblick
Mögliche zukünftige Erweiterungen sind:
- Anbindung externer Blacklist-Dienste  
- Erweiterte Betrugserkennung  
- Authentifizierung und Autorisierung  
- Containerisierung (Docker)  

---

## 10. Abschlussbewertung
Das Projektziel wurde vollständig erreicht. Der Invoice Scanner Service stellt eine
praxisnahe und saubere Lösung zur automatisierten Prüfung von Rechnungsdokumenten dar.
