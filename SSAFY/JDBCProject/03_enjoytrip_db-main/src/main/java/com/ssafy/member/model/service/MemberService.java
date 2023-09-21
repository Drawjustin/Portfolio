package com.ssafy.member.model.service;

import com.ssafy.member.model.MemberDto;

public interface MemberService {

	void registerMember(MemberDto memberDto);

	MemberDto login(String userId, String userPass);

	void modifyMember(String userId, String userPass, String userName);

	void deleteMember(String userId);

	boolean check(MemberDto user);

}
