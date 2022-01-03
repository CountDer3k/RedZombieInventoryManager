package RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.redzombie.RedZombieInventory.Model.monthYearModel;

public class monthYearRowMapper implements RowMapper{

	@Override
	public monthYearModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		monthYearModel mym = new monthYearModel();
		
		mym.setMonth(rs.getInt("nowMonth"));
		mym.setYear(rs.getInt("nowYear"));
		mym.setAccess(rs.getString("access"));
		
		return mym;
	}

}
