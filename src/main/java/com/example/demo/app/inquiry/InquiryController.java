package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
// インターフェイスの型名で指定する
 	private final InquiryService inquiryService;

 	@Autowired
// 	DIによって自動的にinquiryServiceImplが引数に代入される
 	public InquiryController(InquiryService inquiryService) {
 		this.inquiryService = inquiryService;
 	}
// 	一覧表示
 	@GetMapping 
 	public String index(Model model) {
 		List<Inquiry> list = inquiryService.getAll();
// 		リストをmodelに設定。キーは任意、listを丸ごと渡す
 		model.addAttribute("inquiryList", list);
// 		タイトルをつけておく
 		model.addAttribute("title", "Inquiry Index");
// 		表示ページ
 		return "inquiry/index";
 	}

//	このURLでアクセスしてきた時の処理を記述↓
	@GetMapping("/form")
//	InquiryForm フォームの入力欄との紐付け
	public String form(InquiryForm inquiryForm, 
			Model model,
//			フラッシュスコープを受け取る。フラッシュスコープのキー"complete"→formHTMLに出力する処理を組む
			@ModelAttribute("complete") String complete) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
	}
	
//	戻るボタン（URLではない）が押された時用
	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
	}
//	データを送信した場合にのみ表示されるページ
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {
//		エラーがあった場合フォームの画面に戻す
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
		}
		model.addAttribute("title", "Confirm Page");
		return "inquiry/confirm";
	}
//	確認ページからデータが飛んでくるから、Post
	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
//			RedirectAttributesでフラッシュスコープ(seccionを使う)を使える
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
//			エラーがあったらFormの画面に遷移する
			model.addAttribute("title", "InquiryForm");
			return "inquiry/form";
		}
//		DBの操作を記述
//		inquiryFormからinquiry(Entityのクラス)にデータを詰め替える（重要）
//		↑自動化させる方法ある
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
//		ここはFormから値がきていない
		inquiry.setCreated(LocalDateTime.now());
//		saveメソッドにinquiryを渡す
		inquiryService.save(inquiry);
//		redirectAttributesはmodel.addAttributeの代わりになる
//		addFlashAttribute　seccionに値が格納される。Registeredが表示されるとsessionのデータが破棄される。＝フラッシュスコープ。
//		これを簡単に実装する仕組みが↓
		redirectAttributes.addFlashAttribute("complete", "Registered");
//		redirect: HTMLファイルではなくURLを指す。クライアントに一度レスポンスを返し、クライアントから再びリクエストを受け取る。→GetMappingに記述
		return "redirect:/inquiry/form";
	
		
	}
}
