package com.redzombie.RedZombieInventory.Model;

import com.opencsv.bean.CsvBindByName;

public class OrderCircleWeekModel {
	
	@CsvBindByName
	private String sku;
	
	@CsvBindByName
	private String name;
	
	@CsvBindByName(column = "Total Qty Ordered")
	private int count;
	
	@CsvBindByName(column = "Total Custom")
	private int totalCustom;
	
	
	public OrderCircleWeekModel() {}
	
	public OrderCircleWeekModel(String sku, String name, int count, int totalCustom) {
		this.sku = sku;
		this.name = name;
		this.count = count;
		this.totalCustom = totalCustom;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotalCustom() {
		return totalCustom;
	}

	public void setTotalCustom(int totalCustom) {
		this.totalCustom = totalCustom;
	}
	
}
