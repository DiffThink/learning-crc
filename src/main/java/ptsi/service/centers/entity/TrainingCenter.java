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
package ptsi.service.centers.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

/**
 *
 * @author Stephen W. Boyd <sboyd@electricaltrainingalliance.org>
 */
@Entity
@NamedQueries({
        @NamedQuery(name = TrainingCenter.findByProgramCode, query = "SELECT t FROM TrainingCenter t WHERE t.programCode = :code"),
        @NamedQuery(name = TrainingCenter.findAll, query = "SELECT t FROM TrainingCenter t") })
public class TrainingCenter implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PREFIX = "centers.entity.TrainingCenter.";
    public static final String findByProgramCode = PREFIX + "findByProgramCode";
    public static final String findAll = PREFIX + "findAll";

    /**
     * Optimistic Lock Field for Version Control
     */
    @Version
    @Column(name = "OptLock", columnDefinition = "bigint DEFAULT 0", nullable = false)
    private long version = 0L;

    /**
     * PK: Id of the Object
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String programCode;
    private String officialName;
    private String friendlyName;
    private boolean isVisible;
    private boolean isActive;

    public TrainingCenter(String programCode, String officialName, String friendlyName, boolean isVisible,
            boolean isActive) {
        this.programCode = programCode;
        this.officialName = officialName;
        this.friendlyName = friendlyName;
        this.isVisible = isVisible;
        this.isActive = isActive;
    }

    public TrainingCenter() {
        this.isActive = false;
        this.isVisible = false;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
