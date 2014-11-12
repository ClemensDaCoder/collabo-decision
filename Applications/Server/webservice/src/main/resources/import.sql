INSERT INTO AppUser (idUser, forename, mail, password, surname) VALUES (238, 'Vorname', 'v@n.at', 'password', 'Nachname'),  (239, 'Rainer', 'angermeier.rainer@gmail.com', 'password', 'Angermeier');

INSERT INTO issuestatus (idIssueStatus, status) VALUES (2, 'IN_PROGRESS'), (1, 'NEW'), (4, 'OBSOLETE'), (5, 'REJECTED'), (3, 'RESOLVED');  

INSERT INTO relationtype (idRelationType, type) VALUES (1, 'DEPENDS'), (3, 'RELATES'), (2, 'RESOLVES');

INSERT INTO Tag (idTag, name) VALUES (9, 'Java'), (4, 'Database'), (1, 'Organizational'), (2, 'Enterprise');  