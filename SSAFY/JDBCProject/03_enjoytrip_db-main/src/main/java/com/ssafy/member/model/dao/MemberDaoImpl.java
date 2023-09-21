package com.ssafy.member.model.dao;


import com.ssafy.member.model.MemberDto;
import com.ssafy.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDaoImpl implements MemberDao{

  private static MemberDao instance = new MemberDaoImpl();
  private final DBUtil dbUtil = DBUtil.getInstance();

  private MemberDaoImpl(){}

  public static MemberDao getInstance() {
    return instance;
  }

  @Override
  public void registerMember(MemberDto memberDto) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    boolean result = false;

    try {
      conn = dbUtil.getConnection();
      String query = "insert into member (user_id, user_pass, user_name) values (?, ?, ?)";
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, memberDto.getUserId());
      pstmt.setString(2, memberDto.getUserPass());
      pstmt.setString(3, memberDto.getUserName());

      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      dbUtil.close(pstmt, conn);
    }
  }

  @Override
  public MemberDto login(String userId, String userPass) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      MemberDto result = new MemberDto();
      conn = dbUtil.getConnection();
      String query = "select * from member where user_id=? and user_pass=?";
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, userId);
      pstmt.setString(2, userPass);

      rs = pstmt.executeQuery();
      if(rs.next()) {
        result.setUid(rs.getInt("uid"));
        result.setUserId(rs.getString("user_id"));
        result.setUserPass(rs.getString("user_pass"));
        result.setUserName(rs.getString("user_name"));
        result.setJoinDate(rs.getString("join_date"));

        return result;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      dbUtil.close(rs, pstmt, conn);
    }

    return null;
  }

  @Override
  public void modifyMember(String userId, String userPass, String userName) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = dbUtil.getConnection();
      String query = "update member set user_pass=?, user_name=? where user_id=?";
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, userPass);
      pstmt.setString(2, userName);
      pstmt.setString(3, userId);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      dbUtil.close(pstmt, conn);
    }
  }

  @Override
  public void deleteMember(String userId) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      conn = dbUtil.getConnection();
      String query = "delete from member where user_id=?";
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, userId);

      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      dbUtil.close(pstmt, conn);
    }
  }

  @Override
  public boolean check(MemberDto user) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean result = false;

    try {
      conn = dbUtil.getConnection();
      String query = "select * from member where user_id=?";
      pstmt = conn.prepareStatement(query);

      pstmt.setString(1, user.getUserId());

      rs = pstmt.executeQuery();
      if(rs.next()) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      dbUtil.close(rs, pstmt, conn);
    }

    return result;
  }
}
