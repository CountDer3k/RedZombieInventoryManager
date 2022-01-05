package com.redzombie.RedZombieInventory.Item;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import dto.ItemDto;
import dto.actualTotalDto;
import dto.comingDto;

@Controller
public class ItemController {
	ItemService itemService;
	private Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@PostMapping("/onChange/actual/{itemID}")
	@Log
	public ModelAndView actualTotalOnChange(
			@PathVariable String itemID,
			@Valid actualTotalDto dto,
			BindingResult bindResult,
			HttpServletRequest request,
			Errors errors) {
		
		itemService.updateItemActualTotal(itemID, dto.getActualTotal());
		return new ModelAndView("redirect:/");
	}
	
	@PostMapping("/onChange/coming/{itemID}")
	@Log
	public ModelAndView comingTotalOnChange(
			@PathVariable String itemID,
			@Valid comingDto dto,
			BindingResult bindResult,
			HttpServletRequest request,
			Errors errors) {
		try {
			int coming = Integer.parseInt(dto.getComingTotal());
			itemService.updateItemComingTotal(itemID, coming);
		}catch(Exception e) {
			logger.error("ItemController - comingTotalOnChange() " + e.toString());
		}
		return new ModelAndView("redirect:/");
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
		ItemDto itemDto = new ItemDto();
		return getMVForAddItem(itemDto, false);
	}
	
	@PostMapping("/items/addItem")
	@Log
	public ModelAndView addItem(
			@Valid ItemDto itemDto,
			BindingResult bindResult,
			HttpServletRequest request,
			Errors errors) {
		
		if(bindResult.hasErrors()) {
			return getMVForAddItem(itemDto, true);
		}
		
		if(itemDto.getName() == null || itemDto.getName().equals("") ||
				itemDto.getSku() == null || itemDto.getSku().equals("") ||
				itemDto.getBarcode() == null || itemDto.getBarcode().equals("")) {
			return getMVForAddItem(itemDto,true);
		}
		else {
			ModelAndView mv;
			itemService.addItemDto(itemDto);
			mv = new ModelAndView("redirect:/");
			return mv;
		}
	}
	
	
	
	private ModelAndView getMVForAddItem(ItemDto itemDto, boolean isShowingValidation) {
		ModelAndView mv = new ModelAndView("items/addItem");
		List<BrandModel> brands = itemService.getAllBrands();
		List<GlassTypeModel> glassTypes = itemService.getAllGlassTypes();
		List<ItemTypeModel> itemTypes= itemService.getAllItemTypes();
		// Used to get the object back on POST
		mv.getModelMap().addAttribute("showValidation", isShowingValidation);
		mv.getModelMap().addAttribute("itemDto", itemDto);
		mv.getModelMap().addAttribute("brands", brands);
		mv.getModelMap().addAttribute("glassTypes", glassTypes);
		
		return mv;
	}
}
