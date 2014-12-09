INSERT INTO AppUser (idUser, forename, mail, password, surname) VALUES (238, 'Vorname', 'v@n.at', '$2a$10$QxGgdfoZ5454LPaDgU873ue2QmaczPzFGGOyZYQ8Na2sPxJfDqsm2', 'Nachname');
INSERT INTO AppUser (idUser, forename, mail, password, surname) VALUES (239, 'Katharina', 'k@k.at', '$2a$10$QxGgdfoZ5454LPaDgU873ue2QmaczPzFGGOyZYQ8Na2sPxJfDqsm2', 'Kaiser');
INSERT INTO AppUser (idUser, forename, mail, password, surname) VALUES (240, 'Manuel', 'm@h.at', '$2a$10$QxGgdfoZ5454LPaDgU873ue2QmaczPzFGGOyZYQ8Na2sPxJfDqsm2', 'Hochreiter');
INSERT INTO AppUser (idUser, forename, mail, password, surname) VALUES (241, 'Michael', 'm@w.at', '$2a$10$QxGgdfoZ5454LPaDgU873ue2QmaczPzFGGOyZYQ8Na2sPxJfDqsm2', 'Weichselbaumer');
INSERT INTO AppUser (idUser, forename, mail, password, surname) VALUES (242, 'David', 'd@e.at', '$2a$10$QxGgdfoZ5454LPaDgU873ue2QmaczPzFGGOyZYQ8Na2sPxJfDqsm2', 'Eigenstuhler');
INSERT INTO AppUser (idUser, forename, mail, password, surname) VALUES (243, 'Rainer', 'r@a.at', '$2a$10$QxGgdfoZ5454LPaDgU873ue2QmaczPzFGGOyZYQ8Na2sPxJfDqsm2', 'Angermeier');

INSERT INTO issuestatus (idIssueStatus, status) VALUES (2, 'IN_PROGRESS'), (1, 'NEW'), (4, 'OBSOLETE'), (5, 'REJECTED'), (3, 'RESOLVED');  

INSERT INTO relationtype (idRelationType, type) VALUES (1, 'DEPENDS'), (3, 'RELATES'), (2, 'RESOLVES');

INSERT INTO designdecisionstatus (idDesignDecisionStatus, status) VALUES (1, 'COLLECTING_ALTERNATIVES'), (2, 'RANK_ALTERNATIVES'), (3, 'SELECTING_ALTERNATIVES'), (4, 'DECIDED'), (5, 'OBSOLETE'), (6, 'INAPPROPRIATE_SOLUTION'), (7, 'BLOCKED');