package com.iplay.vo.common;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class EntityDeletionVO {
	@NotEmpty(message="Array ids can not be empty")
	private List<Integer> ids;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}
