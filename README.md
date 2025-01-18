## Setup

- `docker compose up` <br>
-  Build/Run Application





# CICD Pipeline - Funktionsweise

Die CICD-Pipeline in diesem Projekt ist mit github Actions konfiguriert. Die Pipeline wird ausgelöst, wenn Änderungen in beliebigen Branches, einschliesslich `master`, gepusht werden.
Die Pieline beinhaltet Testen, Bauen der Applikation und Deployment als Docker image in DockerHub.

## Übersicht der Pipeline

### **1. Tests**
Die Tests stellen sicher, dass alle Schnittstellentests (Wiremock) und Unittests erfolgreich ausgeführt werden. <br>
Ist dieser Schritt nicht erfolgreich, wird der Build Prozess nicht angestossen und entsprechende Fehlermeldungen angezeigt.

### **2. Build**
Die Build-Phase erstellt die Anwendung, nachdem die Lint- und Test-Phasen erfolgreich abgeschlossen wurden.
- **Abhängigkeiten:** Test (Unit) und Test (Integration) müssen erfolgreich abgeschlossen sein.

### **3. Deploy**
Der Deploy Job veröffentlicht die Anwendung als Docker-Image auf DockerHub.
- **Abhängigkeiten:** Build muss erfolgreich abgeschlossen sein.


---

## Besonderheiten der Pipeline

- **Caching:** Artifakte/Abhängigkeiten werden zwischen den Builds zwischengespeichert, um die Installationszeit zu reduzieren.
- **Deployment:** Das fertige Docker-Image wird mit Unterstützung für Multi-Architektur-Builds erstellt und automatisch auf DockerHub hochgeladen.
