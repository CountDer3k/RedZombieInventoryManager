package RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.redzombie.RedZombieInventory.Model.ItemTypeModel;

public class ItemTypeRowMapper  implements RowMapper{

	private Logger logger = LoggerFactory.getLogger(ItemTypeRowMapper.class);
	@Override
	public ItemTypeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		try {
		ItemTypeModel itm = new ItemTypeModel();
		
		itm.setItem_type_id(rs.getInt("item_type_id"));
		itm.setItem_type_name(rs.getString("item_type_name"));
		
		return itm;
		} catch(Exception e) {
			logger.error("ItemTypeModel - mapRow() " + e.toString());
			return null;
		}
	}
}
