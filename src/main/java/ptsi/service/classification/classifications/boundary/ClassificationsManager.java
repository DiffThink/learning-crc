package ptsi.service.classification.classifications.boundary;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import ptsi.service.classification.classifications.entity.ClassificationEntity;

@Stateless
public class ClassificationsManager {

    @Inject
    private Logger LOGGER;

    @Inject
    private EntityManager em;
	
	public ClassificationEntity save(ClassificationEntity classification) {
		try {
			return this.em.merge(classification);
		} catch (PersistenceException pex) {
            LOGGER.log(Level.FINE, pex.toString(), pex);
            return null;
		}
	}
	
	public ClassificationEntity findById(Long id) {
		try {
			return this.em.find(ClassificationEntity.class, id);
		} catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            return null;
		}
	}
	
	public List<ClassificationEntity> findByProgram(String code) {
		try {
			
			return this.em.createNamedQuery(ClassificationEntity.findByProgramCode, ClassificationEntity.class)
					.setParameter("programCode", code)
					.getResultList();
		} catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            return null;
		}
	}
	
	public List<ClassificationEntity> findAll() {
		try {
			return this.em.createNamedQuery(ClassificationEntity.findAll, ClassificationEntity.class).getResultList();
		} catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            return null;
		}
	}
	
	public Boolean delete(Long id) {
        try {
        	ClassificationEntity reference = this.em.getReference(ClassificationEntity.class, id);
            this.em.remove(this.em.merge(reference));
            return true;
        } catch (PersistenceException pex) {
            LOGGER.log(Level.FINE, pex.toString(), pex);
            return false;
        }
	}
}
