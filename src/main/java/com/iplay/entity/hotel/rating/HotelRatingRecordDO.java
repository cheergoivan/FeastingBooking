package com.iplay.entity.hotel.rating;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "hotel_rating_record",indexes={@Index(name="IDX_USER_HOTEL",columnList="userId, hotelId")})
public class HotelRatingRecordDO {
	@Id
	@GeneratedValue
	private int id;
	private int userId;
	private int hotelId;
	
	public HotelRatingRecordDO(){}

	public HotelRatingRecordDO(int userId, int hotelId) {
		super();
		this.userId = userId;
		this.hotelId = hotelId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
}
