package ptsi.service.centers.boundary;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import ptsi.service.centers.entity.TrainingCenter;

@Stateless
public class CenterManager {

	@PersistenceContext(name = "serviceDS")
	EntityManager em;
	
	private static final Logger LOGGER = Logger.getLogger(CenterManager.class.getName());
	
	public TrainingCenter save(TrainingCenter center) {
		try {
			return this.em.merge(center);
		} catch (PersistenceException pex) {
            LOGGER.log(Level.FINE, pex.toString(), pex);
            return null;
		}
	}
	
	public TrainingCenter findById(String code) {
		try {
			return this.em.find(TrainingCenter.class, code);
		} catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            return null;
		}
	}
	
	public List<TrainingCenter> findAll() {
		try {
			return this.em.createNamedQuery(TrainingCenter.findAll, TrainingCenter.class).getResultList();
		} catch (Exception e) {
            LOGGER.log(Level.FINE, e.toString(), e);
            return null;
		}
	}
	
	public void delete(String code) {
        try {
        	TrainingCenter reference = this.em.getReference(TrainingCenter.class, code);
            this.em.remove(this.em.merge(reference));
        } catch (PersistenceException pex) {
            LOGGER.log(Level.FINE, pex.toString(), pex);
        }
	}
}
