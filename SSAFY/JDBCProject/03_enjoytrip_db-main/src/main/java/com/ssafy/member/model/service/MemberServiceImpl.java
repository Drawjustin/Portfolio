package com.ssafy.member.model.service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.dao.MemberDao;
import com.ssafy.member.model.dao.MemberDaoImpl;

public class MemberServiceImpl implements MemberService {

  private static MemberService instance = new MemberServiceImpl();
  private final MemberDao memberDao = MemberDaoImpl.getInstance();

  private MemberServiceImpl() {
  }

  public static MemberService getInstance() {
    return instance;
  }

  @Override
  public void registerMember(MemberDto memberDto) {
    memberDao.registerMember(memberDto);
  }

  @Override
  public MemberDto login(String userId, String userPass) {
    return memberDao.login(userId, userPass);
  }

  @Override
  public void modifyMember(String userId, String userPass, String userName) {
    memberDao.modifyMember(userId, userPass, userName);
  }

  @Override
  public void deleteMember(String userId) {
    memberDao.deleteMember(userId);
  }

  @Override
  public boolean check(MemberDto user) {
    return memberDao.check(user);
  }
}
