package com.redzombie.RedZombieInventory.Item;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

import com.redzombie.RedZombieInventory.Model.BrandModel;
import com.redzombie.RedZombieInventory.Model.GlassTypeModel;
import com.redzombie.RedZombieInventory.Model.ItemModel;
import com.redzombie.RedZombieInventory.Model.ItemTypeModel;
import com.redzombie.RedZombieInventory.aop.Log;

@Controller
public class ItemController {
	ItemService itemService;
	private Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}



	@GetMapping("/items/itemInfo/{itemID}")
	@Log
	public ModelAndView showItemInfo(@PathVariable String itemID) {
		try {
			ModelAndView mav = new ModelAndView("items/itemInfo");

			ItemModel item = itemService.getItemInfo(itemID);

			mav.getModelMap().addAttribute("item", item);

			return mav;
		}catch(Exception e) {
			logger.error(e.toString());
			return new ModelAndView("/error");
		}
	}
	
	
	@GetMapping("/items/addItem")
	@Log
	public ModelAndView showAddItem() {
		ModelAndView mv = new ModelAndView("items/addItem");
		ItemDto itemDto = new ItemDto();
		List<BrandModel> brands = itemService.getAllBrands();
		List<GlassTypeModel> glassTypes = itemService.getAllGlassTypes();
		List<ItemTypeModel> itemTypes= itemService.getAllItemTypes();
		// Used to get the object back on POST
		mv.getModelMap().addAttribute("itemDto", itemDto);
		mv.getModelMap().addAttribute("brands", brands);
		mv.getModelMap().addAttribute("glassTypes", glassTypes);
		mv.getModelMap().addAttribute("itemTypes", itemTypes);
		
		return mv;
	}
	
	@PostMapping("/items/addItem")
	@Log
	public ModelAndView addItem(
			@Validated ItemDto itemDto,
			BindingResult bindResult,
			HttpServletRequest request,
			Errors errors) {
		
		if(bindResult.hasErrors()) {
			return new ModelAndView("error");
		}else {
			ModelAndView mv;
			logger.info("dto information: "+ itemDto.toString());
			itemService.addItemDto(itemDto);
			mv = new ModelAndView("redirect:/");
			return mv;
		}
	}
}
