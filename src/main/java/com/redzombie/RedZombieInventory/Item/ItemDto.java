package com.redzombie.RedZombieInventory.Item;

import org.springframework.lang.NonNull;

public class ItemDto {

	@NonNull
	private String name;
	
	@NonNull
	private String sku;
	
	@NonNull
	private String barcode;

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
	
	
	
	
}
