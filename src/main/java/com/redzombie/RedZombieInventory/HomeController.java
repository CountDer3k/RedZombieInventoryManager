package com.redzombie.RedZombieInventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.redzombie.RedZombieInventory.Item.ItemService;
import com.redzombie.RedZombieInventory.Model.ItemModel;
import com.redzombie.RedZombieInventory.Model.monthYearModel;
import com.redzombie.RedZombieInventory.aop.Log;

import dto.actualTotalDto;
import dto.comingDto;



@Controller
public class HomeController {
	private Logger logger = LoggerFactory.getLogger(HomeController.class);

	ItemService itemService;

	@Autowired
	public HomeController(ItemService itemService) {
		this.itemService = itemService;
	}



	@GetMapping("/")
	@Log
	public ModelAndView home(){
		List<ItemModel> items = itemService.getAllItems();
		return GridViewInfo(items, true, "Now");
	}	
	
	@GetMapping("/previousMonth/{accessCode}")
	@Log
	public ModelAndView showPreviousMonth(@PathVariable String accessCode) {
		try {
			monthYearModel mym = itemService.getMonthYearFromAccessCode(accessCode);
			List<ItemModel> items = itemService.getAllItemForMonth(mym);
			boolean isNow = accessCode.equals("Now") ? true : false;
			return GridViewInfo(items, isNow, accessCode);
		}catch(Exception e) {
			logger.error("HomeController - GridViewInfo() "+ e.toString());
			return null;
		}
	}


	@GetMapping("/archiveMonth")
	@Log
	public ModelAndView archiveMonth() {
		if(!itemService.archiveMonth()) {
			// ?? Pop up should show on front end
			logger.error("Failed to archive the month");
			return null;
		}
		else {
			ModelAndView mv = new ModelAndView("redirect:/");
			return mv;
		}
	}


	private ModelAndView GridViewInfo(List<ItemModel> items, boolean isNow, String accessCode) {
		try {
			ModelAndView mv = new ModelAndView("index");
			actualTotalDto actualDto = new actualTotalDto();
			comingDto comingDto = new comingDto();
			List<monthYearModel> months = itemService.getAllMonthYears();
			mv.getModelMap().addAttribute("items", items);
			mv.getModelMap().addAttribute("months", months);
			// Used to show inputs instead of labels when using the bottom bar
			mv.getModelMap().addAttribute("isNow", isNow);
			mv.getModelMap().addAttribute("selectedButton", accessCode);
			mv.getModelMap().addAttribute("actualInput", actualDto);
			mv.getModelMap().addAttribute("comingInput", comingDto);
			return mv;
		}catch(Exception e) {
			logger.error("HomeController - GridViewInfo() "+ e.toString());
			return null;
		}
	}
	
}
