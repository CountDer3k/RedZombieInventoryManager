package com.redzombie.RedZombieInventory.Item;

import java.util.ArrayList;
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
import com.redzombie.RedZombieInventory.Model.monthYearModel;
import com.redzombie.RedZombieInventory.aop.Log;

import dto.ItemDto;
import dto.actualTotalDto;
import dto.comingDto;
import dto.searchDto;

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
		ModelAndView mv = new ModelAndView("redirect:/");
		try {
			int coming = Integer.parseInt(dto.getComingTotal());
			itemService.updateItemComingTotal(itemID, coming);
		} catch (Exception e) {
			logger.error("ItemController - comingTotalOnChange() " + e.toString());
			mv.getModelMap().addAttribute("message",
					"Weird Error Occured. Contact developer. Save this message: " + e.toString());
		}
		return mv;
	}

	@GetMapping("/items/itemInfo/{itemID}")
	@Log
	public ModelAndView showItemInfo(@PathVariable String itemID) {
		try {
			ModelAndView mav = new ModelAndView("items/itemInfo");

			ItemModel item = itemService.getItemInfo(itemID);

			mav.getModelMap().addAttribute("item", item);

			return mav;
		} catch (Exception e) {
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

		if (bindResult.hasErrors()) {
			return getMVForAddItem(itemDto, true);
		}

		if (itemDto.getName() == null || itemDto.getName().equals("") ||
				itemDto.getSku() == null || itemDto.getSku().equals("") ||
				itemDto.getBarcode() == null || itemDto.getBarcode().equals("")) {
			return getMVForAddItem(itemDto, true);
		} else {
			ModelAndView mv = new ModelAndView("redirect:/");
			boolean isSuccessful;
			try {
				isSuccessful = itemService.addItemDto(itemDto);
				if (!isSuccessful) {
					ModelAndView mvsame = new ModelAndView("items/itemInfo");
					mvsame.getModelMap().addAttribute("message", "* Error occured while adding item to database *");
				}
			} catch (Exception e) {
				logger.error("ItemController - addItem() " + e.toString());
				mv.getModelMap().addAttribute("message", "* Error occured while adding item to database *");
			}
			return mv;
		}
	}

	@PostMapping("/search")
	@Log
	public ModelAndView searchItems(
			@Valid @ModelAttribute("searchParms") searchDto searchParameters,
			BindingResult bindResult,
			HttpServletRequest request,
			Errors errors) {
		ModelAndView mv = new ModelAndView("index");
		try {
			String keyWord = searchParameters.getSearchParameter();

			// Fill this with the keyWord 
			List<ItemModel> items = itemService.getAllItems();

			// Search the above items with keyWord
			items = itemService.filterBy(items, keyWord);





			actualTotalDto actualDto = new actualTotalDto();
			comingDto comingDto = new comingDto();
			List<monthYearModel> months = itemService.getAllMonthYears();
			searchDto searchParms = new searchDto();
			mv.getModelMap().addAttribute("items", items);
			mv.getModelMap().addAttribute("months", months);
			// Used to show inputs instead of labels when using the bottom bar
			mv.getModelMap().addAttribute("isNow", true);
			mv.getModelMap().addAttribute("selectedButton", "Now");
			mv.getModelMap().addAttribute("actualInput", actualDto);
			mv.getModelMap().addAttribute("comingInput", comingDto);
			mv.getModelMap().addAttribute("searchParms", searchParms);
			return mv;
		} catch (Exception e) {
			logger.error("HomeController - GridViewInfo() " + e.toString());
			mv.getModelMap().addAttribute("message",
					"Weird Error occured on page load. Contact developer. Save this message: " + e.toString());
			return new ModelAndView("error");
		}
	}


	private ModelAndView getMVForAddItem(ItemDto itemDto, boolean isShowingValidation) {
		ModelAndView mv = new ModelAndView("items/addItem");
		List<BrandModel> brands = itemService.getAllBrands();
		List<GlassTypeModel> glassTypes = itemService.getAllGlassTypes();
		List<ItemTypeModel> itemTypes = itemService.getAllItemTypes();
		// Used to get the object back on POST
		mv.getModelMap().addAttribute("showValidation", isShowingValidation);
		mv.getModelMap().addAttribute("itemDto", itemDto);
		mv.getModelMap().addAttribute("brands", brands);
		mv.getModelMap().addAttribute("glassTypes", glassTypes);

		return mv;
	}
}
