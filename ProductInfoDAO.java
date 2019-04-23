package com.internousdev.earth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.internousdev.earth.dto.ProductInfoDTO;
import com.internousdev.earth.util.DBConnector;

public class ProductInfoDAO {
	public ArrayList<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

	// Product_InfoDTOの中身を全て取得
	public ArrayList<ProductInfoDTO> selectAll() {
		DBConnector db = new DBConnector();
		Connection ct = db.getConnection();
		String sql = "SELECT * FROM product_info order by id asc";
		try {
			PreparedStatement preparedStatement = ct.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}

		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();

		} finally {
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	// ProductID(商品ID)に応じてDTOとる
	public ProductInfoDTO selectByProductId(int productId) {
		DBConnector db = new DBConnector();
		Connection ct = db.getConnection();
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
		String sql = "SELECT * FROM product_info WHERE product_id = ?";
		try {
			PreparedStatement ps = ct.prepareStatement(sql);
			ps.setInt(1, productId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_Date"));
				productInfoDTO.setUpDate(resultSet.getDate("update_date"));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTO;
	}

	// カテゴリーのみでの検索。
	public ArrayList<ProductInfoDTO> selectByCategoryId(int categoryId) {
		DBConnector db = new DBConnector();
		Connection ct = db.getConnection();
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
		if (categoryId == 1) {
			productInfoDTOList = this.selectAll();
		} else {
			String sql = "SELECT *  FROM product_info WHERE category_id = ?";
			try {
				PreparedStatement ps = ct.prepareStatement(sql);
				ps.setInt(1, categoryId);
				ResultSet resultSet = ps.executeQuery();
				while (resultSet.next()) {
					productInfoDTO = new ProductInfoDTO();
					productInfoDTO.setId(resultSet.getInt("id"));
					productInfoDTO.setProductId(resultSet.getInt("product_id"));
					productInfoDTO.setProductName(resultSet.getString("product_name"));
					productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
					productInfoDTO.setProductDescription(resultSet.getString("product_description"));
					productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
					productInfoDTO.setPrice(resultSet.getInt("price"));
					productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
					productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
					productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
					productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
					productInfoDTO.setStatus(resultSet.getShort("status"));
					productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
					productInfoDTO.setUpDate(resultSet.getDate("update_date"));
					productInfoDTOList.add(productInfoDTO);
				}

			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				try {
					ct.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return productInfoDTOList;
	}

	// 商品検索。（複数ワードでの検索にも対応）
	public List<ProductInfoDTO> getProductInfoListAll(String[] keywordsList) {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
		String sql = "SELECT * FROM product_info where";
		boolean initializeFlag = true;
		for (String keyword : keywordsList) {
			if (initializeFlag) {
				sql += " (product_name LIKE '%" + keyword + "%' OR product_name_kana LIKE '%" + keyword + "%')";
				initializeFlag = false;
			} else {
				sql += " OR (product_name LIKE '%" + keyword + "%' OR product_name_kana LIKE '%" + keyword + "%')";
			}
		}

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	// カテゴリー及び検索ワードでの商品検索。
	public List<ProductInfoDTO> getProductInfoListByKeywords(String[] keywordsList, String categoryId) {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
		String sql = "SELECT * FROM product_info where";
		boolean initializeFlag = true;
		for (String keyword : keywordsList) {
			if (initializeFlag) {
				sql += " category_id=" + categoryId + " AND ((product_name like '%" + keyword
						+ "%' or product_name_kana like '%" + keyword + "%')";
				initializeFlag = false;
			} else {
				sql += " OR (product_name like '%" + keyword + "%' OR product_name_kana like '%" + keyword + "%')";
			}
		}
		sql += ")";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	public ArrayList<ProductInfoDTO> selectRelative(int ProductId, int categoryId) {
		DBConnector db = new DBConnector();
		Connection ct = db.getConnection();
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
		ArrayList<ProductInfoDTO> sortedList = new ArrayList<ProductInfoDTO>();
		String sql = "SELECT *  FROM product_info WHERE category_id = ?";
		try {
			PreparedStatement ps = ct.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getShort("status"));
				productInfoDTO.setRegistDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpDate(resultSet.getDate("update_date"));
				productInfoDTOList.add(productInfoDTO);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		int count = 0;
		for (ProductInfoDTO dto : productInfoDTOList) {
			if (dto.getProductId() == ProductId) {
				productInfoDTOList.remove(count);
				break;
			}
			count++;
		}
		Collections.shuffle(productInfoDTOList);
		productInfoDTOList.stream().limit(3).forEach(s -> sortedList.add(s));

		return sortedList;
	}
}
