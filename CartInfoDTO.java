package com.internousdev.earth.dto;

import java.util.Date;

public class CartInfoDTO {
	//from cart_info table
	private String UserId;
	private String TempId;
	private int ProductId;
    private int ProductCount;
    private int Price;
    private String RegistDate;
    private String UpdateDate;
    //below from product_info table
    private String ProductName;
    private String ProductNameKana;
    private String ImagePath;
    private String ImageName;
    private String ReleaseComp;
    private Date ReleaseDate;
    //other elements
    private int sum;


	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public int getProductCount() {
		return ProductCount;
	}
	public void setProductCount(int productCount) {
		ProductCount = productCount;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public String getRegistDate() {
		return RegistDate;
	}
	public void setRegistDate(String registDate) {
		RegistDate = registDate;
	}
	public String getUpdateDate() {
		return UpdateDate;
	}
	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}
	public String getTempId() {
		return TempId;
	}
	public void setTempId(String tempId) {
		TempId = tempId;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getProductNameKana() {
		return ProductNameKana;
	}
	public void setProductNameKana(String productNameKana) {
		ProductNameKana = productNameKana;
	}
	public String getImagePath() {
		return ImagePath;
	}
	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}
	public String getImageName() {
		return ImageName;
	}
	public void setImageName(String imageName) {
		ImageName = imageName;
	}
	public String getReleaseComp() {
		return ReleaseComp;
	}
	public void setReleaseComp(String releaseComp) {
		ReleaseComp = releaseComp;
	}
	public Date getReleaseDate() {
		return ReleaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		ReleaseDate = releaseDate;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}

}
