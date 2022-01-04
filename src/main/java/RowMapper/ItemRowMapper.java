package RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.redzombie.RedZombieInventory.Model.ItemModel;

public class ItemRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemModel item = new ItemModel();
		item.setItem_id(rs.getInt("item_id"));
		item.setName(rs.getString("name"));
		item.setSku(rs.getString("sku"));
		item.setBarcode(rs.getString("barcode"));
		item.setBrand_id(rs.getInt("b.brand_id"));
		item.setBrand(rs.getString("b.brand_name"));
		item.setGlass_typeID(rs.getInt("gt.glass_type_id"));
		item.setGlass_type(rs.getString("gt.glass_type_name"));
		
		item.setItem_type(rs.getInt("item_type"));
		item.setPreviousMonthTotal(rs.getInt("previousMonthTotal"));
		item.setActualTotal(rs.getString("actualTotal"));
		item.setOrderedLastMonth(rs.getInt("orderedLastMonth"));
		item.setOrderedFromManufacturer(rs.getInt("orderedFromManufacturer"));
		item.setComing(rs.getInt("coming"));
		
		item.setWeek1(rs.getInt("week1"));
		item.setWeek2(rs.getInt("week2"));
		item.setWeek3(rs.getInt("week3"));
		item.setWeek4(rs.getInt("week4"));
		item.setWeek5(rs.getInt("week5"));
		
		item.setMonth(rs.getInt("month"));
		item.setYear(rs.getInt("year"));
		// convert from string to boolean
		boolean isUV = (rs.getString("isUV")).equals("False") ? false : true;
		item.setUV(isUV);
		
		item.setCalcuations();
		return item;
	}

}
