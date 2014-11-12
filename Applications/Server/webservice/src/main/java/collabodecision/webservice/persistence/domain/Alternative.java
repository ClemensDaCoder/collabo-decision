package collabodecision.webservice.persistence.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table
public class Alternative {

	@GeneratedValue
	@Id
	private long idAlternative;
	
	@Column(nullable=false)
	private String description;

	@ManyToOne
	@JoinColumn(name="idDesignDecision", nullable=false)
	private DesignDecision designDecision;
	
	@ManyToOne
	@JoinColumn(name="idCreator", nullable=false)
	private AppUser creator;
	
	@OneToMany(mappedBy="alternative")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<Comment> comments;
	
	@OneToMany(mappedBy="alternative")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<File> files;
	
	@OneToMany(mappedBy="alternativeFrom")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<AlternativeRelation> alternativeFromRelations;
	
	@OneToMany(mappedBy="alternativeTo")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<AlternativeRelation> alternativeToRelations;
	
	@OneToMany(mappedBy="alternative")
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	private Set<AlternativeRanking> alternativeRankings;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DesignDecision getDesignDecision() {
		return designDecision;
	}

	public void setDesignDecision(DesignDecision designDecision) {
		this.designDecision = designDecision;
	}

	public AppUser getCreator() {
		return creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<AlternativeRanking> getAlternativeRankings() {
		return alternativeRankings;
	}

	public void setAlternativeRankings(Set<AlternativeRanking> alternativeRankings) {
		this.alternativeRankings = alternativeRankings;
	}

	public long getIdAlternative() {
		return idAlternative;
	}

	public Set<AlternativeRelation> getAlternativeFromRelations() {
		return alternativeFromRelations;
	}

	public void setAlternativeFromRelations(
			Set<AlternativeRelation> alternativeFromRelations) {
		this.alternativeFromRelations = alternativeFromRelations;
	}

	public Set<AlternativeRelation> getAlternativeToRelations() {
		return alternativeToRelations;
	}

	public void setAlternativeToRelations(
			Set<AlternativeRelation> alternativeToRelations) {
		this.alternativeToRelations = alternativeToRelations;
	}
	
	
}
