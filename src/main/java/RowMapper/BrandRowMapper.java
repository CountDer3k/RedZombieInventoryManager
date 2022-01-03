package RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.redzombie.RedZombieInventory.Model.BrandModel;

public class BrandRowMapper implements RowMapper{

	@Override
	public BrandModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		BrandModel bm = new BrandModel();
		
		bm.setBrand_id(rs.getInt("brand_id"));
		bm.setBrand_name(rs.getString("brand_name"));
		
		return bm;
	}
}
