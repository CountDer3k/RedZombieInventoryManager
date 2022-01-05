package dto;

import javax.validation.constraints.NotNull;

public class actualTotalDto {
	
	@NotNull
	private String actualTotal;

	public String getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(String actualTotal) {
		this.actualTotal = actualTotal;
	}
	
	

}
