package com.redzombie.RedZombieInventory.Item;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.redzombie.RedZombieInventory.Model.ItemModel;
import com.redzombie.RedZombieInventory.Model.monthYearModel;
import com.redzombie.RedZombieInventory.aop.Log;


@Repository
public class ItemRepository {
	private Logger logger = LoggerFactory.getLogger(ItemRepository.class);
	private NamedParameterJdbcTemplate jdbc;
	private static ItemRepository INSTANCE;


	// SQL Queries
	private final String GET_MONTH_YEAR = "SELECT * From MonthYear WHERE access = :code";

	private final String GET_ALL_ITEMS_OF_THE_MONTH = "SELECT * FROM Item WHERE month = :month AND year = :year ORDER BY brand, glass_type, item_id";

	private final String GET_ITEM_INFO_FROM_ID = "SELECT * FROM Item WHERE item_id = :itemID";

	private final String ADD_ITEM = "INSERT INTO Item "
			+ "(name, sku, barcode, brand, glass_type, item_type, previousMonthTotal, actualTotal,"
			+ "week1, week2, week3, week4, week5, orderedLastMonth, orderedFromManufacturer, coming, month, year)"
			+ "VALUES(:name, :sku, :barcode, :brand, :glass_type, :item_type, :previousMonthTotal, :actualTotal,"
			+ ":week1, :week2, :week3, :week4, :week5, :orderedLastMonth, :orderedFromManufacturer, :coming,"
			+ ":month, :year)";

	private final String CHANGE_MONTH_YEAR = "UPDATE MonthYear SET nowMonth = :month, nowYear = :year WHERE access = :code";





	public ItemRepository() {}

	@Autowired
	public ItemRepository(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	//--------------------
	// GET Methods
	//--------------------

	@Log
	public List<ItemModel> getAllItemsOfTheMonth(){
		try {
			monthYearModel mym = getCurrentMonthYear();
			return getAllItemsOfTheMonth(mym);
		}catch(Exception e) {
			logger.error("ItemRepository - getAllItemsOfTheMonth() " + e.toString());
			return null;
		}
	}

	@Log
	public List<ItemModel> getAllItemsOfTheMonth(monthYearModel mym){
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("month", mym.getMonth())
				.addValue("year", mym.getYear());
		List<ItemModel> items = (List<ItemModel>)jdbc.query(GET_ALL_ITEMS_OF_THE_MONTH, parameters, new ItemRowMapper());
		logger.info("All item of month count: " +items.size());
		return AddBrandItem(items);
	}

	@Log
	// Gets the month and year from the database
	private monthYearModel getCurrentMonthYear() {
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("code", "now");
			monthYearModel mym = (monthYearModel) jdbc.queryForObject(GET_MONTH_YEAR, parameters, new monthYearRowMapper());
			return mym;
		}catch(Exception e) {
			logger.error("ItemRepository - getCurrentMonthYear() " + e.toString());
			return null;
		}
	}

	@Log
	public ItemModel getItemInfo(String itemID) {
		try {
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("itemID", itemID);
			ItemModel item = (ItemModel)jdbc.queryForObject(GET_ITEM_INFO_FROM_ID, parameters, new ItemRowMapper());
			logger.info("Brand: " + item.getBrand());
			item.setCalcuations();
			return item;
		}catch(Exception e) {
			logger.error("ItemRepository - getItemInfo() " + e.toString());
			return null;
		}
	}

	//--------------------
	// POST Methods
	//--------------------

	@Log
	public boolean addItem(ItemModel item) {
		try {
			//?? Maybe change this in the db to instead take a string over an int
			String actual = item.getActualTotal();
			int actualInt = actual != null && !actual.equals("") ? Integer.parseInt(actual) : item.getExpectedTotal();

			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("name", item.getName())
					.addValue("sku", item.getSku())
					.addValue("barcode", item.getBarcode())
					.addValue("brand", item.getBrand())
					.addValue("glass_type", item.getGlass_type())
					.addValue("item_type", item.getItem_type())
					.addValue("previousMonthTotal", item.getPreviousMonthTotal())
					.addValue("actualTotal", actualInt)
					.addValue("week1", item.getWeek1())
					.addValue("week2", item.getWeek2())
					.addValue("week3", item.getWeek3())
					.addValue("week4", item.getWeek4())
					.addValue("week5", item.getWeek5())
					.addValue("orderedLastMonth", item.getOrderedLastMonth())
					.addValue("orderedFromManufacturer", item.getOrderedFromManufacturer())
					.addValue("coming", item.getComing())
					.addValue("month", item.getMonth())
					.addValue("year", item.getYear());
			jdbc.update(ADD_ITEM, parameters, keyHolder);
			return true;
		} catch(Exception e) {
			logger.error("ItemRepository - addItem() "+e.toString());
			return false;
		}
	}


	//--------------------
	// UPDATE Methods
	//--------------------

	@Log
	public boolean updateMonthYear(monthYearModel mym) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("month", mym.getMonth())
					.addValue("year", mym.getYear())
					.addValue("code", "now");
			jdbc.update(CHANGE_MONTH_YEAR, parameters, keyHolder);
			return true;
		} catch(Exception e) {
			logger.error("ItemRepository - updateMonthYear() " +e.toString());
			return false;
		}
	}


	//----------------------
	// DATA MOVEMENT Methods
	//----------------------

	@Log
	/**
	 * This will serve a way to "archive" the current month values
	 * It will copy all the items with a new month/year and values changed in the following order:
	 * 
	 * 	acutalTotal -> previouseMonthTotal
	 * 	//?? Check what else
	 * 
	 * @return		a boolean if it was successful
	 * */
	public boolean archiveMonth() {
		try {
			// Get all items from this month & year
			List<ItemModel> items = removeBrandItem(getAllItemsOfTheMonth());
			// Get Month/Year & increment it 
			monthYearModel mym = getCurrentMonthYear();
			mym = increaseByAMonth(mym);
			logger.info("items Count: "+items.size());
			
			// Iterate through each item
			for(ItemModel item : items) {
				// re-calculate values
				item.setCalcuations();
				// Change values of each item according to what needs to be changed (according to the JavaDoc above)
				//?? THis still needs to be done
				String actual = item.getActualTotal();
				int actualInt = actual != null && !actual.equals("") ? Integer.parseInt(actual) : item.getExpectedTotal();

				item.setPreviousMonthTotal(actualInt);
				// Change item's month/year
				item.setMonth(mym.getMonth());
				item.setYear(mym.getYear());
				// store each item back to the db
				boolean didAdd = addItem(item);
				// Return if any adding failed.
				if(!didAdd) {
					return false;
				}
			}
			// change month/year in database
			updateMonthYear(mym);
			return true;
		} catch(Exception e) {
			logger.error("ItemRepository - archiveMonth() " + e.toString());
			return false;
		}
	}


	//--------------------
	// HELPER Methods
	//--------------------

	/**
	 * The list must already be sorted by brand before being passed in
	 */
	@Log
	private List<ItemModel> AddBrandItem(List<ItemModel> items){
		String currentBrand = "";
		for(int i = 0; i < items.size(); i++) {
			String brand = items.get(i).getBrand();
			if(! brand.equals(currentBrand)) {
				ItemModel brandItem = new ItemModel(true, brand);
				items.add(i, brandItem);
				currentBrand = brand;
			}
		}
		return items;
	}

	/**
	 * Remove the brand itemModel from the list passed in
	 * 
	 */
	@Log
	private List<ItemModel> removeBrandItem(List<ItemModel> items){
		for(int i = 0; i < items.size(); i++) {
			boolean isBrand = items.get(i).isBrandOnly();
			if(isBrand) {
				items.remove(i);
			}
		}
		return items;
	}
	
	
	/**
	 * Increase month by 1, unless at the end of the year, then it increases the year and sets the month to the first
	 * 
	 * @param 	mym 	monthYearModel of current MYM based on db
	 * @return 			returns an incremented MYM 
	 * */
	@Log
	private monthYearModel increaseByAMonth(monthYearModel mym) {
		int month = mym.getMonth();
		int year = mym.getYear();
		
		if(month+1 > 12) {
			month = 1;
			year++;
		}
		else {
			month++;
		}
		mym.setMonth(month);
		mym.setYear(year);
		return mym;
	}

	public static ItemRepository getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ItemRepository();
		return INSTANCE;
	}
}
