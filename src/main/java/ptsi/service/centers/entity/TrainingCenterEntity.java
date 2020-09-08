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

import javax.json.Json;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import ptsi.service.configurations.PrivateVisibilityStrategy;
import ptsi.service.validations.ValidEntity;

/**
 * Training Centers are the Registered Programs using the
 * TMS, LMS, and TAS.
 * 
 * @author Stephen W. Boyd <sboyd@protechskillsinstitute.org>
 */

@Entity
@Table(name = "training_centers")
@NamedQuery(name = TrainingCenterEntity.findAll, query = "SELECT c FROM TrainingCenterEntity c")
@JsonbVisibility(PrivateVisibilityStrategy.class)
public class TrainingCenterEntity implements Serializable, ValidEntity {

	private static final long serialVersionUID = 9197911968578298904L;
	public static final String PREFIX = "centers.entity.TrainingCenter.";
    public static final String findAll = PREFIX + "findAll";

	@Id
	@Column(name = "program_code")
	private String program_code;

	@Column(name = "program_name", length = 255, nullable = false)
	private String program_name;

	public TrainingCenterEntity(String code, String name) {
		this.program_code = code;
		this.program_name = name;
	}
	
	public TrainingCenterEntity() {
	}

	public String getProgramCode() {
		return program_code;
	}

	public void setProgramCode(String program_code) {
		this.program_code = program_code;
	}

	@Override
	@Transient
	public String toString() {
		String center = Json.createObjectBuilder()
				.add("program_code", this.program_code)
				.add("program_name", this.program_name)
				.build()
				.toString();
		return center;
	}
	
	@Override
	@Transient
	@JsonbTransient
	public boolean isValid() {
		return ((this.program_code.length() > 1) && (this.program_name.length() > 1));
	}
}
