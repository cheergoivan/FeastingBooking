package com.iplay.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "feast_order_payment")
public class OrderPaymentDO {
	@Id
	private int id;
	private double amountPaid;
	@Column(length = 1000)
	private String payment;
	private long lastUpdated;
	private ApprovalStatus approvalStatus;

	public OrderPaymentDO() {
	}

	public OrderPaymentDO(int id, double amountPaid, String payment, long lastUpdated, ApprovalStatus approvalStatus) {
		super();
		this.id = id;
		this.amountPaid = amountPaid;
		this.payment = payment;
		this.lastUpdated = lastUpdated;
		this.approvalStatus = approvalStatus;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
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
