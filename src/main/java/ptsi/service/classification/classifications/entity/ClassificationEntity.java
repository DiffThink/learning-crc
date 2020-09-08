package ptsi.service.classification.classifications.entity;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import ptsi.service.centers.entity.TrainingCenterEntity;
import ptsi.service.configurations.PrivateVisibilityStrategy;
import ptsi.service.validations.ValidEntity;

@Entity
@Table(name = "classifications")
@NamedQueries({
	@NamedQuery(name = ClassificationEntity.findByProgramCode, query = "SELECT c FROM ClassificationEntity c WHERE c.center.program_code = :programCode"),
	@NamedQuery(name = ClassificationEntity.findAll, query = "SELECT c FROM ClassificationEntity c")
})
@JsonbVisibility(PrivateVisibilityStrategy.class)
public class ClassificationEntity implements Serializable, ValidEntity{
	
	private static final long serialVersionUID = 5138672261884252346L;
	public static final String PREFIX = "centers.entity.ClassificationEntity.";
	public static final String findByProgramCode = PREFIX + "findByProgramCode";
    public static final String findAll = PREFIX + "findAll";

    @Id
    @Column(name = "classification_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "classification_type", nullable = false)
    private ClassificationType classificationType;
    
    @Column(name = "classification_name", nullable = true, length = 255)
    private String classificationName;
    
    @ManyToOne
    @JoinColumn(name = "program_code", referencedColumnName = "program_code")
    private TrainingCenterEntity center;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClassificationEntity() {
		this.center = new TrainingCenterEntity();
    }

	@Override
	@Transient
	@JsonbTransient
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
