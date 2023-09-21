package com.ssafy.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.board.model.BoardDto;
import com.ssafy.util.DBUtil;

public class BoardDaoImpl implements BoardDao {
	private static BoardDao instance = new BoardDaoImpl();
	private final DBUtil dbUtil = DBUtil.getInstance();

	public static BoardDao getInstance() {
		return instance;
	}

	@Override
	public void registerArticle(BoardDto boardDto) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbUtil.getConnection();
			String query = "insert into board (userId, subject, content) values (?, ?, ?)";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, boardDto.getUserId());
			pstmt.setString(2, boardDto.getSubject());
			pstmt.setString(3, boardDto.getContent());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public List<BoardDto> searchListAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardDto> list = new ArrayList<>();
		try {
			conn = dbUtil.getConnection();
			String query = "select * from board";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setArticleNo(rs.getInt("articleNo"));
				boardDto.setUserId(rs.getString("userId"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setRegisterTime(rs.getString("registerTime"));
				list.add(boardDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public List<BoardDto> searchListBySubject(String subject) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardDto> list = new ArrayList<>();
		try {
			conn = dbUtil.getConnection();
			String query = "select * from board where subject like ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, "%"+subject+"%");
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setArticleNo(rs.getInt("articleNo"));
				boardDto.setUserId(rs.getString("userId"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setRegisterTime(rs.getString("registerTime"));
				list.add(boardDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public BoardDto viewArticle(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto boardDto = new BoardDto();
		try {
			conn = dbUtil.getConnection();
			String query = "select * from board where articleNo = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				boardDto.setArticleNo(rs.getInt("articleNo"));
				boardDto.setUserId(rs.getString("userId"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setHit(rs.getInt("hit"));
				boardDto.setRegisterTime(rs.getString("registerTime"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return boardDto;
	}

	@Override
	public void modifyArticle(BoardDto boardDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dbUtil.getConnection();
			String query = "update board set content = ? , subject = ? where articleNo = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1,boardDto.getContent());
			pstmt.setString(2,boardDto.getSubject());
			pstmt.setInt(3, boardDto.getArticleNo());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt, conn);
		}

	}

	@Override
	public void deleteArticle(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbUtil.getConnection();
			String query = "delete from board where articleNo=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt, conn);
		}

	}

}
