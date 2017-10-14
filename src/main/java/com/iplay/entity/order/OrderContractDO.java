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
	@Column(length=1000)
	private String contract;
	private long updatedAt;
	private ApprovalStatus approvalStatus;
	
	public OrderContractDO(){}
	
	public OrderContractDO(int id, String contract, long updatedAt, ApprovalStatus approvalStatus) {
		super();
		this.id = id;
		this.contract = contract;
		this.updatedAt = updatedAt;
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
	public long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(ApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
}
