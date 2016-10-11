package com.ecmoho.modules.shops;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecmoho.models.BusinessDTO;
import com.ecmoho.service.shops.BusinessService;

@Controller
public class BusinessController {
	@Autowired
	private BusinessService businessService;
	 //查询全部数据
	@RequestMapping(value="/test.json",method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
	public List<BusinessDTO> selectAllBusiness(){
    	return businessService.selectAllBusiness();
    };
    //查询全部数据
   	@RequestMapping(value="/test2.json")
    @ResponseBody
   	public BusinessDTO selectBusinessById(BusinessDTO businessDTO){
       	return businessService.selectBusinessById(businessDTO);
    };
}
