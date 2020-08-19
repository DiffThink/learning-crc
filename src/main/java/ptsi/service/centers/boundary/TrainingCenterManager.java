/*
 * Copyright (c) 2019, electrical training ALLIANCE
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are prohibited:
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package ptsi.service.centers.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import ptsi.service.centers.entity.TrainingCenter;

/**
 *
 * @author Stephen W. Boyd <sboyd@electricaltrainingalliance.org>
 */
@Stateless
public class TrainingCenterManager {

    @PersistenceContext
    EntityManager em;

    public TrainingCenter save(TrainingCenter center) {
        return this.em.merge(center);
    }

    public TrainingCenter findById(long id) {
        return this.em.find(TrainingCenter.class, id);
    }

    public TrainingCenter findByProgramCode(String code) {
        return this.em.createNamedQuery(TrainingCenter.findByProgramCode, TrainingCenter.class)
                .setParameter("code", code).getSingleResult();
    }

    public List<TrainingCenter> all() {
        return this.em.createNamedQuery(TrainingCenter.findAll, TrainingCenter.class).getResultList();
    }

    public void delete(long id) {
        try {
            TrainingCenter reference = this.em.getReference(TrainingCenter.class, id);
            this.em.remove(reference);
        } catch (EntityNotFoundException e) {
            // This is valid becasue we wanted to remove it anyhow.
        }
    }
}
