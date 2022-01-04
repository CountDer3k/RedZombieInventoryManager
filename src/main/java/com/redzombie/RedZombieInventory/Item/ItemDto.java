package com.redzombie.RedZombieInventory.Item;

import org.springframework.lang.NonNull;

public class ItemDto {

	@NonNull
	private String name;
	
	@NonNull
	private String sku;
	
	@NonNull
	private String barcode;
	
	@NonNull
	private int brand;
	
	@NonNull
	private int glass_type;
	
	private int item_type = 1;
	
	private int coming;
	
	// this will be put in the database as the 'previousMonthTotal'
	@NonNull
	private int startingAmount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getBrand() {
		return brand;
	}

	public void setBrand(int brand) {
		this.brand = brand;
	}

	public int getGlass_type() {
		return glass_type;
	}

	public void setGlass_type(int glass_type) {
		this.glass_type = glass_type;
	}

	public int getItem_type() {
		return item_type;
	}

	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}

	public int getComing() {
		return coming;
	}

	public void setComing(int coming) {
		this.coming = coming;
	}

	public int getStartingAmount() {
		return startingAmount;
	}

	public void setStartingAmount(int startingAmount) {
		this.startingAmount = startingAmount;
	}

	@Override
	public String toString() {
		return "ItemDto [name=" + name + ", sku=" + sku + ", barcode=" + barcode + ", brand=" + brand + ", glass_type="
				+ glass_type + ", item_type=" + item_type + ", coming=" + coming + ", startingAmount=" + startingAmount
				+ "]";
	}
	
	
}
