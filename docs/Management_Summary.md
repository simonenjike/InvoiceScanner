
# Invoice Scanner Service  
## Management Summary

**Autor:** Simone Njike  
**Datum:** 19.12.2025  
**Version:** 1.0  

---

## 1. Projektüberblick
Das Projekt **Invoice Scanner Service** hat das Ziel, Rechnungsdokumente im PDF-Format automatisiert
auf gesperrte (blacklisted) IBANs zu prüfen.

Dadurch sollen Risiken wie fehlerhafte Zahlungen, Betrugsversuche oder Compliance-Verstöße
frühzeitig erkannt und vermieden werden.

Der entwickelte Service ist als **REST-basierter Microservice** umgesetzt und kann einfach in
bestehende IT-Systeme integriert werden. Die Lösung ist technologieoffen, wartbar und
zukunftssicher ausgelegt.

---

## 2. Zielsetzung
Ziel des Projekts war die Entwicklung einer Lösung mit folgenden Eigenschaften:

- automatisiert  
- zuverlässig  
- wartbar  
- erweiterbar  

Der Fokus lag auf der sicheren Erkennung und Validierung von IBANs in Rechnungsdokumenten
sowie dem Abgleich mit einer definierten Blacklist.

---

## 3. Umsetzung
Die Umsetzung erfolgte in sechs klar definierten Projektphasen nach einem klassischen Phasenmodell:

1. Voruntersuchung  
2. Initiierung / Lastenheft  
3. System- und Softwareentwurf  
4. Implementierung  
5. Test und Integration  
6. Einführung und Projektabschluss  

Technisch basiert der Service auf **Java 21** und **Spring Boot 3.5.7**.

Zur Minimierung technischer Risiken wurden bewährte Open-Source-Bibliotheken eingesetzt:
- **Apache PDFBox** zur PDF-Verarbeitung  
- **iban4j** zur IBAN-Validierung  

---

## 4. Ergebnisse
Der Invoice Scanner Service erfüllt alle im Lastenheft definierten Anforderungen:

- Download von PDF-Rechnungen über eine URL  
- Textextraktion aus Rechnungsdokumenten  
- Erkennung und Validierung enthaltener IBANs  
- Abgleich der IBANs mit einer Blacklist  
- Rückgabe strukturierter JSON-Antworten über eine REST-Schnittstelle  

Die Funktionalität wurde sowohl durch manuelle Tests (z. B. über cURL und Postman) als auch
durch automatisierte Unit- und Integrationstests erfolgreich verifiziert.

---

## 5. Nutzen und Mehrwert
Der entwickelte Service bietet einen klaren fachlichen und technischen Mehrwert:

- Reduzierung manueller Prüfaufwände  
- Erhöhung der Prüfungssicherheit  
- schnelle Integration in bestehende Systeme  
- solide Basis für zukünftige Erweiterungen  
  (z. B. KI-gestützte Analyse, externe Blacklists, erweiterte Betrugserkennung)

---

## 6. Projektabschluss
Das Projekt wurde termingerecht und erfolgreich abgeschlossen.

Alle definierten Meilensteine wurden erreicht, die Anwendung ist lauffähig, getestet und
vollständig dokumentiert.

Der **Invoice Scanner Service** stellt eine praxisnahe und technisch saubere Lösung dar,
die sowohl fachliche als auch technische Anforderungen erfüllt und sich für einen realen
Produktiveinsatz eignet.
