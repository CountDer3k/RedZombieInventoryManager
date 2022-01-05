package com.redzombie.RedZombieInventory.Model;

import java.io.Serializable;

public class monthYearModel implements Serializable{

	private int month;
	private int year;
	private String access;
	
	public monthYearModel() {}
	
	public monthYearModel(int month, int year, String access) {
		this.month = month;
		this.year = year;
		this.access = access;
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
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}

	@Override
	public String toString() {
		return "monthYearModel [month=" + month + ", year=" + year + ", access=" + access + "]";
	}
	
	
}
