package com.iplay.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "feast_order_contract")
public class OrderContractDO {
	@Id
	private int id;
	@Column(length = 1000)
	private String contract;
	private long lastUpdated;
	private ApprovalStatus approvalStatus;

	public OrderContractDO() {
	}

	public OrderContractDO(int id, String contract, long lastUpdated, ApprovalStatus approvalStatus) {
		super();
		this.id = id;
		this.contract = contract;
		this.lastUpdated = lastUpdated;
		this.approvalStatus = approvalStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
}
