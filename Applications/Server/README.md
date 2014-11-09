# Schritte für Applikation - Deployment:
  
  
## Anforderungen: Java 7 - MySQL  
  
  
### 1. MySQL Datenbank starten (am einfachsten mit XAMPP https://www.apachefriends.org/de/index.html)
	Server: localhost
	Port: 3306
  
### 2. Leere Datenbank erstellen
	DB Name: collabo_decision (Achtung! Auf Unterstrich geändert)
  	
	Username: root
	Kein Passwort!
	(ist bereits XAMPP default)
  
### 3. Applikation Starten (In Eclipse)
	RUN - collabodecision.webservice.Application
  	
### 4. Test mit Postman:   
  
Postman Erweiterung für Chrome herunterladen (https://chrome.google.com/webstore/detail/postman-rest-client/fdmmgilgnpjigdojojpjoooidkmcomcm?hl=de)  
Postman Collection importieren ("Postman_Collection_Collabo_Decision.json" in "/src/main/resources/")
   
## Weitere Informationen:
  
- Konfiguration der DB Connection in der Datei: /src/main/resources/spring/data-context.xml  
  
- Wenn das Property <prop key="hibernate.hbm2ddl.auto">create</prop> eingestellt ist, dann wird das DB Schema jedes mal wieder neu generiert, wenn die Applikation neu gestartet wird!   
	(Wenn nicht erwünscht einfach auf "update" stellen, oder auskommentieren)  

# Fehler  
  
### Unendliche Rekursion  
Wenn "withRelations=true" requested wird, entsteht eine infinite recursion (Problem derzeit nur auskommentiert)  

### Post Issue
>Column 'idCreator' cannot be null

### Post DesignDecision
>Column 'assumption' cannot be null

### Post Comment
>Column 'idCreator' cannot be null

### Delete Issue
>Cannot delete or update a parent row: a foreign key constraint fails (`collabo_decision`.`DesignDecision`, CONSTRAINT `FK_5le2xhi6pisuyqf0oe3g6divc` FOREIGN KEY (`idIssue`) REFERENCES `Issue` (`idIssue`))

### Delete DesignDecision
>Cannot delete or update a parent row: a foreign key constraint fails (`collabo_decision`.`Comment`, CONSTRAINT `FK_98lligxdu23y51or7oct9y3rw` FOREIGN KEY (`idDesignDecision`) REFERENCES `DesignDecision` (`idDesignDecision`))

