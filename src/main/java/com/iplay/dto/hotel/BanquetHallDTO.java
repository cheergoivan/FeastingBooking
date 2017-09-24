package com.iplay.dto.hotel;

public class BanquetHallDTO {
	private int id;
	private String name;
	private double area;
	private int[] tableRange;
	private double minimumPrice;
	private double height;
	private String columns;
	private String shape;
	private String actualArea;
	private String colorOfTablecloth;
	private String[] extraInfos;
	private String[] pictureUrls;
	
	public BanquetHallDTO(int id, String name, double area, int[] tableRange, double minimumPrice, double height,
			String columns, String shape, String actualArea, String colorOfTablecloth, String[] extraInfos,
			String[] pictureUrls) {
		super();
		this.id = id;
		this.name = name;
		this.area = area;
		this.tableRange = tableRange;
		this.minimumPrice = minimumPrice;
		this.height = height;
		this.columns = columns;
		this.shape = shape;
		this.actualArea = actualArea;
		this.colorOfTablecloth = colorOfTablecloth;
		this.extraInfos = extraInfos;
		this.pictureUrls = pictureUrls;
	}
	
	public BanquetHallDTO() {}

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
	public int[] getTableRange() {
		return tableRange;
	}
	public void setTableRange(int[] tableRange) {
		this.tableRange = tableRange;
	}
	public double getMinimumPrice() {
		return minimumPrice;
	}
	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
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
	public String[] getExtraInfos() {
		return extraInfos;
	}
	public void setExtraInfos(String[] extraInfos) {
		this.extraInfos = extraInfos;
	}
	public String[] getPictureUrls() {
		return pictureUrls;
	}
	public void setPictureUrls(String[] pictureUrls) {
		this.pictureUrls = pictureUrls;
	}
}
