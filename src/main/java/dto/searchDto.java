package dto;

import javax.validation.constraints.NotNull;

public class searchDto {

    @NotNull
    private String searchParameter;

    public String getSearchParameter(){
        return searchParameter;
    }

    public void setSearchParameter(String search){
        this.searchParameter = search;
    }
}
