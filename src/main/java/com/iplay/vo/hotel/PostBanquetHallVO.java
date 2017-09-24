package com.iplay.vo.hotel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class PostBanquetHallVO {
	private int id = -1;
	@NotBlank(message="The name of the banquet hall can't be blank")
	private String name;
	@NotNull(message="The area of the banquet hall can't be blank")
	private double area;
	@NotNull(message="The minimumTables of the banquet hall can't be blank")
	private int minimumTables;
	@NotNull(message="The maximumTables of the banquet hall can't be blank")
	private int maximumTables;
	@NotNull(message="The minimumPrice of the banquet hall can't be blank")
	private double minimumPrice;
	@NotNull(message="The height of the banquet hall can't be blank")
	private double height;
	@NotBlank(message="The columns of the banquet hall can't be blank")
	private String columns;
	@NotBlank(message="The shape of the banquet hall can't be blank")
	private String shape;
	@NotBlank(message="The actualArea of the banquet hall can't be blank")
	private String actualArea;
	@NotBlank(message="The colorOfTablecloth of the banquet hall can't be blank")
	private String colorOfTablecloth;

	private String extraInfo;
	
	//@NotEmpty(message="Pictures can not be empty")
	//@Size(min=1,message="The quantity of pictures must be at least 1")
	private MultipartFile[] files = new MultipartFile[0];
	
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
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
