package com.example.demo.app.inquiry;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//　データの受け渡しを担当する
public class InquiryForm{
//	これらのアノテーションも適宜確認
	@Size(min = 1, max = 20, message = "Please input 20characters or less")
	private String name;
//	@NotNullがポイント→WebMvcCotrollerへ
	@NotNull
	@Email(message = "Invalid E-mail Format")
	private String email;
	@NotNull
	private String contents;
//	コンストラクタはなぜ必要？
//	入力値を一括で受け取る為のクラス。htmlのnameに対応しているか確認。
	public InquiryForm() {
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}


}