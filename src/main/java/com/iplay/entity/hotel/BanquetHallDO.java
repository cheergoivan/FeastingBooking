package com.iplay.entity.hotel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iplay.component.util.DelimiterUtils;

@Entity
@Table(name = "banquet_hall")
public class BanquetHallDO {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double area;
	private int minimumTables;
	private int maximumTables;
	private double minimumPrice;
	private double height;
	private String columns;
	private String shape;
	private String actualArea;
	private String colorOfTablecloth;
	private String extraInfo;
	private String pictures = "";
	
	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private HotelDO hotelDO;
	
	public BanquetHallDO(){}

	public BanquetHallDO(int id){
		this.id = id;
	}
	
	public BanquetHallDO(String name, double area, int minimumTables, int maximumTables, double minimumPrice,
			double height, String columns, String shape, String actualArea, String colorOfTablecloth, String extraInfo,
			String pictures) {
		super();
		this.name = name;
		this.area = area;
		this.minimumTables = minimumTables;
		this.maximumTables = maximumTables;
		this.minimumPrice = minimumPrice;
		this.height = height;
		this.columns = columns;
		this.shape = shape;
		this.actualArea = actualArea;
		this.colorOfTablecloth = colorOfTablecloth;
		this.extraInfo = extraInfo;
		this.pictures = pictures;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getMinimumTables() {
		return minimumTables;
	}

	public void setMinimumTables(int minimumTables) {
		this.minimumTables = minimumTables;
	}

	public int getMaximumTables() {
		return maximumTables;
	}

	public void setMaximumTables(int maximumTables) {
		this.maximumTables = maximumTables;
	}

	public double getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	public String[] getPicturesAsArray(){
		if(pictures.equals(""))
			return new String[0];
		return pictures.split(DelimiterUtils.PICTURE_DELIMITER);
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getActualArea() {
		return actualArea;
	}

	public void setActualArea(String actualArea) {
		this.actualArea = actualArea;
	}

	public String getColorOfTablecloth() {
		return colorOfTablecloth;
	}

	public void setColorOfTablecloth(String colorOfTablecloth) {
		this.colorOfTablecloth = colorOfTablecloth;
	}


	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public HotelDO getHotelDO() {
		return hotelDO;
	}

	public void setHotelDO(HotelDO hotelDO) {
		this.hotelDO = hotelDO;
	}
}
