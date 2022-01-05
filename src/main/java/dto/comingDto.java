package dto;

import javax.validation.constraints.NotNull;

public class comingDto {
	
	@NotNull
	private String comingTotal;

	public String getComingTotal() {
		return comingTotal;
	}

	public void setComingTotal(String comingTotal) {
		this.comingTotal = comingTotal;
	}

	
	
	

}
