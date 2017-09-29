package com.iplay.vo.common;

import org.hibernate.validator.constraints.NotEmpty;

public class FileDeletionVO {
	@NotEmpty(message="Array names can not be empty")
	private String[] names;
	
	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
}
