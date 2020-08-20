/*
 * Copyright (c) 2020, ProTech Skills Institute, All rights reserved.
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

import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ptsi.service.configurations.PrivateVisibilityStrategy;

/**
 *
 * @author Stephen W. Boyd <sboyd@protechskillsinstitute.org>
 */
@Entity
@Table(name = "training_centers")
@NamedQuery(name = TrainingCenter.findAll, query = "SELECT c FROM TrainingCenter c")
@JsonbVisibility(PrivateVisibilityStrategy.class)
public class TrainingCenter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9197911968578298904L;
	public static final String PREFIX = "centers.entity.TrainingCenter.";
    public static final String findAll = PREFIX + "findAll";
	

	@Id
	@Column(name = "program_code")
	private String programCode;

	@Column(name = "program_name", length = 255, nullable = false)
	private String programName;

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	
	

}
