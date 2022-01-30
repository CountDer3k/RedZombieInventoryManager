package com.redzombie.RedZombieInventory.Item;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.redzombie.RedZombieInventory.Model.BrandModel;
import com.redzombie.RedZombieInventory.Model.GlassTypeModel;
import com.redzombie.RedZombieInventory.Model.ItemModel;
import com.redzombie.RedZombieInventory.Model.ItemTypeModel;
import com.redzombie.RedZombieInventory.Model.monthYearModel;
import com.redzombie.RedZombieInventory.aop.Log;

import dto.ItemDto;

@Service
public class ItemService {
	private ItemRepository itemRepo;
	private Logger logger = LoggerFactory.getLogger(ItemService.class);


	public ItemService(ItemRepository itemRepo) {
		this.itemRepo = itemRepo;
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
	public ItemModel getItemInfoFromSKU(String sku) {
		return itemRepo.getItemInfoFromSKU(sku);
	} 

	@Log
	public monthYearModel getMonthYearFromAccessCode(String accessCode) {
		return itemRepo.getMonthYearFromAccessCode(accessCode);
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
	public List<monthYearModel> getAllMonthYears(){
		return itemRepo.getAllMonthYears();
	}

	@Log
	public List<GlassTypeModel> getAllGlassTypes(){
		return itemRepo.GetAllGlassType();
	}

	@Log
	public List<BrandModel> getAllBrands(){
		return itemRepo.GetAllBrands();
	}

	@Log 
	public List<ItemTypeModel> getAllItemTypes(){
		return itemRepo.GetAllItemType();
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
	
	@Log
	public boolean updateItem(ItemModel item) {
		return itemRepo.updateItem(item);
	}

	@Log
	public boolean updateItemActualTotal(String id, String actual) {
		try {
			ItemModel item = itemRepo.getItemInfo(id);
			item.setActualTotal(actual);
			return itemRepo.updateItem(item);
		}catch(Exception e) {
			logger.error("ItemService - updateItemActualTotal() " + e.toString());
			return false;
		}
	}
	
	@Log 
	public boolean updateItemComingTotal(String id, int coming) {
		try {
			ItemModel item = itemRepo.getItemInfo(id);
			item.setComing(coming);
			return itemRepo.updateItem(item);
		}catch(Exception e) {
			logger.error("ItemService - updateItemComingTotal() " + e.toString());
			return false;
		}
	}

	@Log
	public boolean addItemDto(ItemDto dto) {
		try {
			// Convert the dto into ItemModel
			ItemModel item = new ItemModel();
			item.setName(dto.getName());
			item.setSku(dto.getSku());
			item.setBarcode(dto.getBarcode());
			item.setComing(dto.getComing());
			item.setPreviousMonthTotal(dto.getStartingAmount());
			item.setBrand_id(dto.getBrand());
			item.setGlass_typeID(dto.getGlass_type());
			item.setItem_type(dto.getItem_type());

			String glassType = itemRepo.GetGlassTypeNameFromID(dto.getGlass_type());
			item.setUV(glassType.equals("UV") ? true : false);

			monthYearModel mym = itemRepo.getCurrentMonthYear();
			item.setMonth(mym.getMonth());
			item.setYear(mym.getYear());

			addItem(item);
			return true;
		}catch(Exception e) {
			logger.error("ItemService - addItemDto() " + e.toString());
			return false;
		}
	}

	@Log
	public boolean addComingToTotal(){
		boolean isSuccessful = true;

		List<ItemModel> comingItems = itemRepo.getAllComingFromCurrent();

		//iterate through all coming items & change their total include the coming
		for(ItemModel item : comingItems){
			item.setOrderedFromManufacturer(item.getOrderedFromManufacturer() + item.getComing());
			item.setComing(0);
			itemRepo.updateItem(item);
		}

		return isSuccessful;
	}

	@Log
	public List<ItemModel> filterBy(List<ItemModel> items, String filterWord)
	{
		String[] filters = filterWord.split(" ");
		List<ItemModel> filteredItems = new ArrayList<ItemModel>();

		for(ItemModel item : items){
			// item equals any of these add to list
			for(String filter : filters){
				if(item.getName().toLowerCase().contains(filter.toLowerCase()) ||
				 item.getSku().toLowerCase().contains(filter.toLowerCase()) || 
				 item.getBarcode().toLowerCase().contains(filter.toLowerCase()) || 
				 item.getBrand().toLowerCase().contains(filter.toLowerCase()) || 
				 item.getGlass_type().toLowerCase().contains(filter.toLowerCase())){
					filteredItems.add(item);
				}
			}
		}
		return filteredItems;
	}
}
