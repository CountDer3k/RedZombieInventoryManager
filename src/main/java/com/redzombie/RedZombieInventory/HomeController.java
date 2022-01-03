package com.redzombie.RedZombieInventory;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.redzombie.RedZombieInventory.Item.ItemService;
import com.redzombie.RedZombieInventory.Model.ItemModel;
import com.redzombie.RedZombieInventory.Model.monthYearModel;
import com.redzombie.RedZombieInventory.aop.Log;



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
		return GridViewInfo();
	}	


	@GetMapping("/archiveMonth")
	@Log
	public ModelAndView archiveMonth() {
		if(!itemService.archiveMonth()) {
			// ?? Pop up should show on front end
			logger.error("Failed to archive the month");
		}
		return GridViewInfo();
	}


	private ModelAndView GridViewInfo() {
		try {
			ModelAndView mv = new ModelAndView("index");
			List<ItemModel> items = itemService.getAllItems();
			List<monthYearModel> months = itemService.getAllMonthYears();
			mv.getModelMap().addAttribute("items", items);
			mv.getModelMap().addAttribute("months", months);
			return mv;
		}catch(Exception e) {
			logger.error("HomeController - GridViewInfo() "+ e.toString());
			return null;
		}
	}
}
