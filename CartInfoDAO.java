package com.internousdev.earth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.internousdev.earth.dto.CartInfoDTO;
import com.internousdev.earth.dto.ProductInfoDTO;
import com.internousdev.earth.util.DBConnector;

public class CartInfoDAO {

	// カート情報取得
	public ArrayList<CartInfoDTO> getCartContents(String UserId) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		ArrayList<CartInfoDTO> toTransList = new ArrayList<CartInfoDTO>();

		String sql = "SELECT * FROM cart_info left outer join product_info on cart_info.product_id=product_info.product_id where cart_info.user_id=? order by cart_info.update_date desc ,cart_info.regist_date desc";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, UserId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CartInfoDTO dto = new CartInfoDTO();
				dto.setUserId(rs.getString("cart_info.user_id"));
				dto.setProductId(rs.getInt("cart_info.product_id"));
				dto.setProductCount(rs.getInt("cart_info.product_count"));
				dto.setPrice(rs.getInt("cart_info.price"));
				dto.setRegistDate(rs.getString("cart_info.regist_date"));
				dto.setUpdateDate(rs.getString("cart_info.update_date"));
				dto.setProductName(rs.getString("product_info.product_name"));
				dto.setProductNameKana(rs.getString("product_info.product_name_kana"));
				dto.setImagePath(rs.getString("product_info.image_file_path"));
				dto.setImageName(rs.getString("product_info.image_file_name"));
				dto.setReleaseComp(rs.getString("product_info.release_company"));
				dto.setReleaseDate(rs.getDate("product_info.release_date"));
				int sum = rs.getInt("price") * rs.getInt("cart_info.product_count");
				dto.setSum(sum);
				toTransList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return toTransList;
	}

	// カートに追加-受け取る値は要考慮
	public int add(String UserId, ProductInfoDTO dto) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		int result = 0;
		String sql = "insert into cart_info(user_id,product_id,product_count,price,regist_date,update_date) values(?,?,?,?,now(),now())";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, UserId);
			ps.setInt(2, dto.getProductId());
			ps.setInt(3, dto.getProductCount());
			ps.setInt(4, dto.getPrice());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 重複更新
	public int update(int TotalCount, String UserId, int ProductId) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		int result = 0;

		String sql = "update cart_info set product_count=? ,update_date=now() where user_id=? and product_id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, TotalCount);
			ps.setString(2, UserId);
			ps.setInt(3, ProductId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// カートから個別削除
	public int delete(String UserId, int ProductId) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		int result = 0;
		String sql = "delete FROM cart_info where user_id=? and product_id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, UserId);
			ps.setInt(2, ProductId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 決済時カートから全削除
	public int deleteAll(String UserId) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		int result;
		String sql = "delete FROM cart_info where user_id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, UserId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// カートに情報が存在するか確認
	public boolean isExistsCartInfo(String UserId, int ProductId) {
		boolean result = false;
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		String sql = "select * FROM cart_info where user_id=? and product_id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, UserId);
			ps.setInt(2, ProductId);
			ResultSet preres = ps.executeQuery();
			if (preres.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// ユーザーIDのカートに情報が存在する場合、カウントを更新して登録
	public int updateFromLogin(int TotalCount, String UserId, int ProductId) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		int ResourceQuantity = 0;
		int result = 0;
		ArrayList<CartInfoDTO> list = this.getCartContents(UserId);
		for (CartInfoDTO dto : list) {
			if (ProductId == dto.getProductId()) {
				ResourceQuantity = dto.getProductCount();
				break;
			}
		}

		String sql = "update cart_info set product_count=? , update_date=now() where user_id=? and product_id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, TotalCount + ResourceQuantity);
			ps.setString(2, UserId);
			ps.setInt(3, ProductId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// ユーザーのカートに情報が無い場合、一時ユーザーIDに保存されているカート情報をユーザーIDのカート情報に登録
	public int linkToUserId(String TempUserId, String UserId, int ProductId) {
		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		int result = 0;
		ArrayList<CartInfoDTO> list = this.getCartContents(TempUserId);

		try {
			for (CartInfoDTO dto : list) {
				if (dto.getProductId() == ProductId) {
					String sql = "insert into cart_info(user_id,product_id,product_count,price,regist_date) values(?,?,?,?,now())";
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, UserId);
					ps.setInt(2, dto.getProductId());
					ps.setInt(3, dto.getProductCount());
					ps.setInt(4, dto.getPrice());
					result = ps.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = 0;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
