package RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.redzombie.RedZombieInventory.Model.GlassTypeModel;

public class GlassTypeRowMapper  implements RowMapper{

	private Logger logger = LoggerFactory.getLogger(GlassTypeRowMapper.class);
	@Override
	public GlassTypeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		try {
		GlassTypeModel gtm = new GlassTypeModel();
		
		gtm.setGlass_type_id(rs.getInt("glass_type_id"));
		gtm.setGlass_type_name(rs.getString("glass_type_name"));
		
		return gtm;
		} catch(Exception e) {
			logger.error("GlassTypeModel - mapRow() " + e.toString());
			return null;
		}
	}
}
