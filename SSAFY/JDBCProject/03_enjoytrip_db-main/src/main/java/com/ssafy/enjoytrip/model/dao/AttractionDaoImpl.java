package com.ssafy.enjoytrip.model.dao;

import com.ssafy.enjoytrip.model.AttractionInfoDto;
import com.ssafy.util.DBUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.enjoytrip.model.AttractionInfoDto;
import com.ssafy.util.DBUtil;

public class AttractionDaoImpl implements AttractionDao {

	private final DBUtil dbUtil = DBUtil.getInstance();
	private static AttractionDao instance = new AttractionDaoImpl();

	private AttractionDaoImpl() {
	}

	public static AttractionDao getInstance() {
		return instance;
	}

	@Override
	public List<AttractionInfoDto> attractionList(AttractionInfoDto attractionInfoDto) {
		List<AttractionInfoDto> result = new ArrayList<>();
		String query = "select * from attraction_info where 1=1";
		boolean sidoFlag = false;
		boolean contentTypeIdFalg = false;
		if (attractionInfoDto.getSidoCode() != 0) {
			query += " and sido_code=?";
			sidoFlag = true;
		}

		if (attractionInfoDto.getContentTypeId() != 0) {
			query += " and content_type_id=?";
			contentTypeIdFalg = true;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			pstmt = conn.prepareStatement(query);

			int index = 1;
			if (sidoFlag) {
				pstmt.setInt(index++, attractionInfoDto.getSidoCode());
			}

			if (contentTypeIdFalg) {
				pstmt.setInt(index++, attractionInfoDto.getContentTypeId());
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				AttractionInfoDto res = new AttractionInfoDto();
				res.setContentId(rs.getInt("content_id"));
				res.setContentTypeId(rs.getInt("content_type_id"));
				res.setTitle(rs.getString("title"));
				res.setAddr1(rs.getString("addr1"));
				res.setAddr2(rs.getString("addr2"));
				res.setZipcode(rs.getString("zipcode"));
				res.setTel(rs.getString("tel"));
				res.setFirstImage(rs.getString("first_image"));
				res.setFirstImage2(rs.getString("first_image2"));
				res.setReadcount(rs.getInt("readcount"));
				res.setSidoCode(rs.getInt("sido_code"));
				res.setGugunCode(rs.getInt("gugun_code"));
				res.setLatitude(rs.getDouble("latitude"));
				res.setLongitude(rs.getDouble("longitude"));
				res.setMlevel(rs.getString("mlevel"));

				result.add(res);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}

		return result;
	}

	@Override
	public List<AttractionInfoDto> searchByTitle(String title, int sidoCode) {
		DBUtil db = DBUtil.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AttractionInfoDto> result = new ArrayList<>();
		try {
			conn = db.getConnection();
			System.out.println("DB연결에 성공했습니다");
			String sql = "select * from attraction_info where title like ? and sido_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");
			pstmt.setInt(2, sidoCode);
			rs = pstmt.executeQuery();
			AttractionInfoDto dto;
			while (rs.next()) {
				AttractionInfoDto res = new AttractionInfoDto();
				res.setContentId(rs.getInt("content_id"));
				res.setContentTypeId(rs.getInt("content_type_id"));
				res.setTitle(rs.getString("title"));
				res.setAddr1(rs.getString("addr1"));
				res.setAddr2(rs.getString("addr2"));
				res.setZipcode(rs.getString("zipcode"));
				res.setTel(rs.getString("tel"));
				res.setFirstImage(rs.getString("first_image"));
				res.setFirstImage2(rs.getString("first_image2"));
				res.setReadcount(rs.getInt("readcount"));
				res.setSidoCode(rs.getInt("sido_code"));
				res.setGugunCode(rs.getInt("gugun_code"));
				res.setLatitude(rs.getDouble("latitude"));
				res.setLongitude(rs.getDouble("longitude"));
				res.setMlevel(rs.getString("mlevel"));
				result.add(res);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(rs, pstmt, conn);
		}
		return result;
	}
}
