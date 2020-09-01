package ptsi.service.centers.boundary;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import ptsi.service.centers.entity.TrainingCenterEntity;

@Stateless
public class TrainingCenterManager {

    @Inject
    private Logger LOGGER;

    @Inject
    private EntityManager em;

	
	public TrainingCenterEntity save(TrainingCenterEntity center) {
		try {
			return this.em.merge(center);
		} catch (PersistenceException pex) {
            LOGGER.log(Level.FINE, pex.toString(), pex);
            return null;
		}
	}
	
	public TrainingCenterEntity findById(String code) {
		try {
			return this.em.find(TrainingCenterEntity.class, code);
		} catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            return null;
		}
	}
	
	public List<TrainingCenterEntity> findAll() {
		try {
			return this.em.createNamedQuery(TrainingCenterEntity.findAll, TrainingCenterEntity.class).getResultList();
		} catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            return null;
		}
	}
	
	public Boolean delete(String code) {
        try {
        	TrainingCenterEntity reference = this.em.getReference(TrainingCenterEntity.class, code);
            this.em.remove(this.em.merge(reference));
            return true;
        } catch (PersistenceException pex) {
            LOGGER.log(Level.FINE, pex.toString(), pex);
            return false;
        }
	}
}
