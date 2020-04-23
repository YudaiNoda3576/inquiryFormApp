package com.example.demo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.example.demo.service.InquiryNotFoundException;

@ControllerAdvice
public class WebMvcControllerAdvice {

//	全ての空文字をnullに直すという処理
//	＠NotNullは返還後のnullに反応している
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public String handleException(EmptyResultDataAccessException e,Model model) {
		model.addAttribute("message", e);
		return "error/CustomPage";
	}
	
//	@ExceptionHandler(InquiryNotFoundException.class)
//	public String handleException(InquiryNotFoundException e,Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}
   
}
