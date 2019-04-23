package com.internousdev.earth.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.earth.dao.ProductInfoDAO;
import com.internousdev.earth.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport implements SessionAware {
	private int productId;
	private Map<String, Object> session;
	private List<ProductInfoDTO> selectRelativeList;
	ProductInfoDTO productInfoDTO = new ProductInfoDTO();
	private String message;

	public String execute() {
		if (session.isEmpty()) {
			return "sessionTimeout";
		}

		// 商品情報取得
		// DAO,DTOの実体化
		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		// selectByProductIdメソッド呼び出し、引数int productId
		// 戻り値をproductInfoDTOに格納
		productInfoDTO = productInfoDAO.selectByProductId(productId);
		session.put("additem",productInfoDTO);

		// 関連情報の取得、引数にint categoryIdを渡す
		// 戻り値をselectRelativeListに格納→jspでiterator
		selectRelativeList = productInfoDAO.selectRelative(productInfoDTO.getProductId(),productInfoDTO.getCategoryId());

		return SUCCESS;
	}



	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<ProductInfoDTO> getSelectRelativeList() {
		return selectRelativeList;
	}

	public void setSelectRelativeList(List<ProductInfoDTO> selectRelativeList) {
		this.selectRelativeList = selectRelativeList;
	}

	public ProductInfoDTO getProductInfoDTO() {
		return productInfoDTO;
	}

	public void setProductInfoDTO(ProductInfoDTO productInfoDTO) {
		this.productInfoDTO = productInfoDTO;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
