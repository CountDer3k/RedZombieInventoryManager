package com.redzombie.RedZombieInventory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.redzombie.RedZombieInventory.Item.ItemService;
import com.redzombie.RedZombieInventory.Model.ItemModel;
import com.redzombie.RedZombieInventory.Model.OrderCircleWeekModel;
import com.redzombie.RedZombieInventory.Model.monthYearModel;
import com.redzombie.RedZombieInventory.aop.Log;

import dto.actualTotalDto;
import dto.comingDto;



@Controller
public class HomeController {
	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	private String fileLocation;

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
			return new ModelAndView("error");
		}
	}
	
	@GetMapping("/importWeek")
	@Log
	public ModelAndView showImportScreenModel () {
		ModelAndView mv = new ModelAndView("menu/ImportItems");
		return mv;
	}

	@PostMapping("/importWeek/")
	@Log
	public ModelAndView importExcelFile (Model model, MultipartFile file) throws IOException {	    
		// validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of OrderCircleWeekModel objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<OrderCircleWeekModel> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(OrderCircleWeekModel.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of OrderCircleWeekModel
                List<OrderCircleWeekModel> weekItems = csvToBean.parse();

                // TODO: save into DB?
                for(OrderCircleWeekModel wItem: weekItems) {
                	String sku = wItem.getSku();
                	int weekCount = wItem.getCount();
                	ItemModel item = itemService.getItemInfoFromSKU(sku);
                	
                	
                	//itemService.updateItem(item);
                }
                

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }	    
		return new ModelAndView("menu/ImportItems");
	}
	

	@GetMapping("/archiveMonth")
	@Log
	public ModelAndView archiveMonth() {
		if(!itemService.archiveMonth()) {
			// ?? Pop up should show on front end
			logger.error("Failed to archive the month");
			return new ModelAndView("error");
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
			return new ModelAndView("error");
		}
	}
	
}
