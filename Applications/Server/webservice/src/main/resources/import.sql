
INSERT INTO `user` (`idUser`, `forename`, `mail`, `password`, `surname`) VALUES (1, 'Rainer', 'angermeier.rainer@gmail.com', 'test', 'Angermeier');


INSERT INTO `issuestatus` (`idIssueStatus`, `status`) VALUES (2, 'IN_PROGRESS'), (1, 'NEW'), (4, 'OBSOLETE'), (5, 'REJECTED'), (3, 'RESOLVED');


INSERT INTO `issue` (`idIssue`, `blocked`, `description`, `title`, `idCreator`, `idIssueStatus`, `idOwner`) VALUES (1, b'0', 'Issue Description', 'Thats the title', 1, 1, 1), (2, b'1', 'aölskdfjöa', 'asdfasdf', 1, 1, 1), (3, b'0', 'dfghsdh', 'sdgfsdfg', 1, 1, 1);

