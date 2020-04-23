package com.example.demo.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleController {
	
 	private final JdbcTemplate jdbcTemplate;

 	@Autowired
 	public SampleController(JdbcTemplate jdbcTemplate) {
 		this.jdbcTemplate = jdbcTemplate;
 	}
	
//	GETはURLで、POSTはヘッダー情報としてデータを渡す
	@GetMapping("/test")
//	String は　HTMLのファイル名を返す
	public String test(Model model) {
//		DB取得用
		String sql = "SELECT id, name, email"
				+ " FROM inquiry WHERE id = 1";
//		戻り値の型がMap,Stringがカラム名,Objectはカラムの型に応じて変わる
		 Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		 model.addAttribute("title", "InquiryForm");
		 model.addAttribute("name", map.get("name"));
		 model.addAttribute("email", map.get("email"));
		return "test";
	}

}
