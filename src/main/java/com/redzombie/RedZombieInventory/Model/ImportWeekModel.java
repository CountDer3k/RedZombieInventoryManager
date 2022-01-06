package com.redzombie.RedZombieInventory.Model;

import org.springframework.web.multipart.MultipartFile;

public class ImportWeekModel {

	private MultipartFile file;
	private int week = 0;
	
	
	public ImportWeekModel() {}


	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public int getWeek() {
		return week;
	}


	public void setWeek(int week) {
		this.week = week;
	}
	
	
}
