package com.internousdev.earth.action;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.earth.dao.CartInfoDAO;
import com.internousdev.earth.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class CartAction extends ActionSupport implements SessionAware {
	private ArrayList<CartInfoDTO> cartlist;
	private Map<String, Object> session;
	private String message;
	private int totalprice;


	public String execute(){
		if(session.isEmpty()) {
			return "sessionTimeout";
		}


		CartInfoDAO dao = new CartInfoDAO();
		if(session.containsKey("loginuserid")){
		cartlist = dao.getCartContents(session.get("loginuserid").toString());
		}else{
			cartlist = dao.getCartContents(session.get("tempuserid").toString());
		}
		if (cartlist.size()!=0) {  //カートが空ではない場合読み込み
			session.put("cartinfo", cartlist);  //To Settlement
			for(CartInfoDTO dto:cartlist){ //合計値の更新
				totalprice+=dto.getSum();
			}
		}else{ //カートが空である場合
			message="カート情報がありません。";
		}
		return SUCCESS;

	}
	// getter setter

	public ArrayList<CartInfoDTO> getCartlist() {
		return cartlist;
	}

	public void setCartlist(ArrayList<CartInfoDTO> cartlist) {
		this.cartlist = cartlist;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

}
