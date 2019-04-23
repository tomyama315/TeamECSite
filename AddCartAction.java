package com.internousdev.earth.action;

import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.earth.dao.CartInfoDAO;
import com.internousdev.earth.dto.CartInfoDTO;
import com.internousdev.earth.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware {
	private ArrayList<CartInfoDTO> cartlist;
	private Map<String, Object> session;
	private int IdentifyKey;
	private String message;
	private int totalprice;
	private int count;//valuestackからの受け取り値
	private ProductInfoDTO dto;



	public String execute(){
		if(session.isEmpty()) {
			return "sessionTimeout";
		}


		dto=(ProductInfoDTO)session.get("additem"); //受け取ったDTOにカウントを追加
		dto.setProductCount(count);

		ProductInfoDTO productdto = (ProductInfoDTO) session.get("additem");
		CartInfoDAO dao = new CartInfoDAO();
		if(count < 1 || count > 5) {
			return  ERROR;
		}
		if(session.containsKey("loginuserid")){
			cartlist = dao.getCartContents(session.get("loginuserid").toString());
		}else{
			cartlist = dao.getCartContents(session.get("tempuserid").toString());
		}
		int result = 0;
		if (!cartlist.isEmpty()) { //カートが空ではない場合
			for (CartInfoDTO dto : cartlist) {
				if (productdto.getProductId() == dto.getProductId()) { //詳細画面からの商品IDとカートの商品IDを走査
					CartInfoDAO updatedao = new CartInfoDAO();
					int totalcount = dto.getProductCount() + productdto.getProductCount();
					if(session.containsKey("loginuserid")){
						result = updatedao.update(totalcount, session.get("loginuserid").toString(), dto.getProductId());
					}else{
						result = updatedao.update(totalcount, session.get("tempuserid").toString(), dto.getProductId());
					}
					IdentifyKey = 1;  //走査の結果同一値が存在した場合更新（既にカートに存在する商品が追加された場合）
					break;
				}
			}
		}
		if (IdentifyKey == 0) {  //走査の結果IdentifyKeyが更新されなかった場合（同じものをカートに追加していない）
			CartInfoDAO adddao = new CartInfoDAO();
			if(session.containsKey("loginuserid")){
				result = adddao.add(session.get("loginuserid").toString(), productdto);
			}else{
				result = adddao.add(session.get("tempuserid").toString(), productdto);
			}
		}
		if (result == 0) {  //executeUpdate()の結果による分岐
		} else {
			CartInfoDAO initializedao = new CartInfoDAO();
			if(session.containsKey("loginuserid")){
				cartlist = initializedao.getCartContents(session.get("loginuserid").toString()); //表示するカート内容の更新
				session.put("cartinfo", cartlist);  //To Settlement
			}else{
				cartlist = initializedao.getCartContents(session.get("tempuserid").toString()); //表示するカート内容の更新
				session.put("cartinfo", cartlist);  //To Settlement
			}
			for (CartInfoDTO dto : cartlist) {  //合計金額の更新
				totalprice += dto.getSum();
			}
		}
		return result == 0 ? ERROR : SUCCESS;  //resultが0ならERROR、それ以外ならSUCCESS

	}

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

	public int getIdentifyKey() {
		return IdentifyKey;
	}

	public void setIdentifyKey(int identifyKey) {
		IdentifyKey = identifyKey;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ProductInfoDTO getDto() {
		return dto;
	}

	public void setDto(ProductInfoDTO dto) {
		this.dto = dto;
	}


}
