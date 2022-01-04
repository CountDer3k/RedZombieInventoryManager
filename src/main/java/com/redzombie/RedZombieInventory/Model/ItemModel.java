package com.redzombie.RedZombieInventory.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemModel {
	private Logger logger = LoggerFactory.getLogger(ItemModel.class);

	private int item_id;
	private String name = "";
	private String sku = "";
	private String barcode = "";
	private int brand_id = 0;
	private String brand = "";
	private int glass_typeID = 0;
	// Standard, Full Coverage, Privacy, Camera, Tablet, 
	private String glass_type = "";
	private boolean isUV = false;
	// type: glass, charger, speaker, etc.
	private int item_type = -1;
	// Final count of inventory in hand for the previous month
	private int previousMonthTotal = 0;
	// This needs to be a string in order to be blank at times
	private String actualTotal = "";

	// Orders made for each week
	private int week1 = 0;
	private int week2 = 0;
	private int week3 = 0;
	private int week4 = 0;
	private int week5 = 0;

	// amount of item ordered from customers in the previous month
	private int orderedLastMonth = 0;
	private int orderedFromManufacturer = 0;

	private int coming = 0;
	private int month = 0;
	private int year = 0;

	// Not in the database -- calculate manually
	private int expectedTotal = 0;
	private int orderCircleTotal = 0;
	private int MIQ = 0;
	private int orderedThisMonth = 0;
	private int offset = 0;

	// Used to create the labels for each brand
	private boolean isBrandOnly = false;


	public ItemModel() {}

	public ItemModel(boolean isBrand, String brand) {
		isBrandOnly = isBrand;
		this.brand = brand;
	}

	public ItemModel(int item_id, String name, String sku, String barcode, String brand, int item_type, int previousMonthTotal, int week1, int week2, int week3, int week4, int week5, int orderedLastMonth, int orderedFromManufacturer) {
		this.item_id = item_id;
		this.name = name;
		this.sku = sku;
		this.barcode = barcode;
		this.brand = brand;
		this.item_type = item_type;
		this.previousMonthTotal = previousMonthTotal;
		this.week1 = week1;
		this.week2 = week2;
		this.week3 = week3;
		this.week4 = week4;
		this.week5 = week5;
		this.orderedLastMonth = orderedLastMonth;
		this.orderedFromManufacturer = orderedFromManufacturer;
	}

	public ItemModel(int item_id, String name, String sku, String barcode, String brand, int item_type, int previousMonthTotal, int currentTotal, int week1, int week2, int week3, int week4, int week5, int orderedLastMonth, int orderedFromManufacturer) {
		this.item_id = item_id;
		this.name = name;
		this.sku = sku;
		this.barcode = barcode;
		this.brand = brand;
		this.item_type = item_type;
		this.previousMonthTotal = previousMonthTotal;
		this.week1 = week1;
		this.week2 = week2;
		this.week3 = week3;
		this.week4 = week4;
		this.week5 = week5;
		this.orderedLastMonth = orderedLastMonth;
		this.orderedFromManufacturer = orderedFromManufacturer;
	}

	public ItemModel(int item_id, String name, String sku, String barcode, String brand, int item_type, int previousMonthTotal, int currentTotal, int week1, int week2, int week3, int week4, int week5, int orderedLastMonth, int orderedFromManufacturer, int coming) {
		this.item_id = item_id;
		this.name = name;
		this.sku = sku;
		this.barcode = barcode;
		this.brand = brand;
		this.item_type = item_type;
		this.previousMonthTotal = previousMonthTotal;
		this.actualTotal = String.valueOf(currentTotal);
		this.week1 = week1;
		this.week2 = week2;
		this.week3 = week3;
		this.week4 = week4;
		this.week5 = week5;
		this.orderedLastMonth = orderedLastMonth;
		this.orderedFromManufacturer = orderedFromManufacturer;
		this.coming = coming;
	}

	public void setCalcuations() {
		try {
			orderedThisMonth = week1 + week2 + week3 + week4 + week5;
			expectedTotal = previousMonthTotal - orderedThisMonth + coming;
			int actual = actualTotal != null && !actualTotal.equals("") ? Integer.parseInt(actualTotal) : expectedTotal;
			orderCircleTotal = actual + coming;
			offset = (actual - expectedTotal);
			MIQ = orderedLastMonth / 2;
		}catch(Exception e) {
			logger.error("ItemModel - setCalculations() " + e.toString());
		}

	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

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

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getGlass_typeID() {
		return glass_typeID;
	}

	public void setGlass_typeID(int glass_typeID) {
		this.glass_typeID = glass_typeID;
	}

	public String getGlass_type() {
		return glass_type;
	}

	public void setGlass_type(String glass_type) {
		this.glass_type = glass_type;
	}

	public boolean isUV() {
		return isUV;
	}

	public void setUV(boolean isUV) {
		this.isUV = isUV;
	}

	public int getItem_type() {
		return item_type;
	}

	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}

	public int getPreviousMonthTotal() {
		return previousMonthTotal;
	}

	public void setPreviousMonthTotal(int previousMonthTotal) {
		this.previousMonthTotal = previousMonthTotal;
	}

	public String getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(String actualTotal) {
		this.actualTotal = actualTotal;
	}

	public int getWeek1() {
		return week1;
	}

	public void setWeek1(int week1) {
		this.week1 = week1;
	}

	public int getWeek2() {
		return week2;
	}

	public void setWeek2(int week2) {
		this.week2 = week2;
	}

	public int getWeek3() {
		return week3;
	}

	public void setWeek3(int week3) {
		this.week3 = week3;
	}

	public int getWeek4() {
		return week4;
	}

	public void setWeek4(int week4) {
		this.week4 = week4;
	}

	public int getWeek5() {
		return week5;
	}

	public void setWeek5(int week5) {
		this.week5 = week5;
	}

	public int getOrderedLastMonth() {
		return orderedLastMonth;
	}

	public void setOrderedLastMonth(int orderedLastMonth) {
		this.orderedLastMonth = orderedLastMonth;
	}

	public int getOrderedFromManufacturer() {
		return orderedFromManufacturer;
	}

	public void setOrderedFromManufacturer(int orderedFromManufacturer) {
		this.orderedFromManufacturer = orderedFromManufacturer;
	}

	public int getComing() {
		return coming;
	}

	public void setComing(int coming) {
		this.coming = coming;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getExpectedTotal() {
		return expectedTotal;
	}

	public void setExpectedTotal(int expectedTotal) {
		this.expectedTotal = expectedTotal;
	}

	public int getOrderCircleTotal() {
		return orderCircleTotal;
	}

	public void setOrderCircleTotal(int orderCircleTotal) {
		this.orderCircleTotal = orderCircleTotal;
	}

	public int getMIQ() {
		return MIQ;
	}

	public void setMIQ(int mIQ) {
		MIQ = mIQ;
	}

	public int getOrderedThisMonth() {
		return orderedThisMonth;
	}

	public void setOrderedThisMonth(int orderedThisMonth) {
		this.orderedThisMonth = orderedThisMonth;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isBrandOnly() {
		return isBrandOnly;
	}

	public void setBrandOnly(boolean isBrandOnly) {
		this.isBrandOnly = isBrandOnly;
	}
	
}
