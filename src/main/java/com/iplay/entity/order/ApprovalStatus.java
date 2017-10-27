package com.iplay.entity.order;

public enum ApprovalStatus {
	PENDING, APPROVED, DECLINED;
	
	public enum ModifiableApprovalStatus {
		APPROVED, DECLINED;

		public ApprovalStatus toApprovalStatus() {
			return ApprovalStatus.valueOf(this.toString());
		}
	}
	
}
