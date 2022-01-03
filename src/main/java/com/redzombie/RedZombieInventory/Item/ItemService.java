package com.redzombie.RedZombieInventory.Item;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.redzombie.RedZombieInventory.Model.ItemModel;
import com.redzombie.RedZombieInventory.Model.monthYearModel;
import com.redzombie.RedZombieInventory.aop.Log;

@Service
public class ItemService {
	private ItemRepository itemRepo;
	private Logger logger = LoggerFactory.getLogger(ItemService.class);
	
	
	public ItemService(ItemRepository itemRepo) {
		this.itemRepo = itemRepo;
	}
	
	@Log
	/** 
	 * Returns a list of ItemModels for the current month and year
	 * as specified by the database
	 * 
	 * @return		List of ItemModel
	 * 
	 * */
	public List<ItemModel> getAllItems(){
		List<ItemModel> items = itemRepo.getAllItemsOfTheMonth();
		return items;
	}
	
	@Log
	/** 
	 * Returns a list of ItemModels for the current month and year
	 * as specified by the parameter passed in.
	 * 
	 * @param	mym	a monthYearModel object containing the month and year for items wanted
	 * @return		List of ItemModel
	 * 
	 * */
	public List<ItemModel> getAllItemForMonth(monthYearModel mym){
		List<ItemModel> items = itemRepo.getAllItemsOfTheMonth(mym);
		return items;
	}
	
	@Log
	/** 
	 * Returns an ItemModel based on the id passed in
	 * 
	 * @param	itemID	
	 * @return			A single ItemModel
	 * 
	 * */
	public ItemModel getItemInfo(String itemID) {
		ItemModel item = itemRepo.getItemInfo(itemID);
		return item;
	}
	
	@Log
	public List<monthYearModel> getAllMonthYears(){
		return itemRepo.getAllMonthYears();
	}
	
	@Log
	public boolean addItem(ItemModel item) {
		return itemRepo.addItem(item);
	}
	
	@Log
	/**
	 * 
	 * 
	 * */
	public boolean archiveMonth() {
		boolean success = itemRepo.archiveMonth();
		return success;
	}
}
