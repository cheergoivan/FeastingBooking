package com.iplay.entity.order;

import java.util.ArrayList;
import java.util.List;

public enum OrderStatus {
	CANCELED, CONSULTING, RESERVED, FEASTED, CASHBACK, TO_BE_REVIEWD, DONE;

	public enum ModifiableOrderStatus {
		CANCELED;

		public OrderStatus toOrderStatus() {
			return OrderStatus.valueOf(this.toString());
		}
	}

	public static List<OrderStatus> unfinishedStatus() {
		OrderStatus[] status = OrderStatus.values();
		List<OrderStatus> unfinishedStatus = new ArrayList<>();
		for (OrderStatus s : status) {
			if (s != OrderStatus.CANCELED && s != OrderStatus.DONE) {
				unfinishedStatus.add(s);
			}
		}
		return unfinishedStatus;
	}

	public OrderStatus next() {
		OrderStatus[] status = OrderStatus.values();
		if (this.ordinal() == status.length - 1 || this == OrderStatus.CANCELED)
			return null;
		return status[this.ordinal() + 1];
	}
}
