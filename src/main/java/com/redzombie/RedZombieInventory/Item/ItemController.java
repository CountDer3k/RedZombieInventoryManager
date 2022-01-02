package com.redzombie.RedZombieInventory.Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import com.redzombie.RedZombieInventory.Model.ItemModel;
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
}
