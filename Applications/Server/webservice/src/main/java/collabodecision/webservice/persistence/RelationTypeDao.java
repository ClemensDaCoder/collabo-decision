package collabodecision.webservice.persistence;

import collabodecision.webservice.persistence.domain.RelationType;

public interface RelationTypeDao {
	RelationType getRelationTypeByType(String type);
}
