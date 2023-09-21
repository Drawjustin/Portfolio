package com.ssafy;

import com.ssafy.member.model.service.MemberService;
import com.ssafy.member.model.service.MemberServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ssafy.member.model.MemberDto;
import java.time.LocalDateTime;
import java.util.Date;

public class MemberMain {
	private boolean loginStatus = false;
	private String id = null;
	private BufferedReader in;

	private final MemberService memberService = MemberServiceImpl.getInstance();

	public MemberMain() {
		in = new BufferedReader(new InputStreamReader(System.in));
		menu();
	}

	private void menu() {
		while (true) {
			System.out.println("---------- 회원 메뉴 ----------");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 회원정보수정");
			System.out.println("4. 회원탈퇴");
			System.out.println("-------------------------------------");
			System.out.println("0. 프로그램 종료");
			System.out.println("-------------------------------------");
			System.out.print("메뉴 선택 : ");
			try {
				int num = Integer.parseInt(in.readLine());
				switch (num) {
				case 1:
					registerMember();
					break;
				case 2:
					login();
					break;
				case 3:
					modifyMember();
					break;
				case 4:
					deleteMember();
					break;
				default:
					System.out.println("프로그램을 종료합니다!!!");
					System.exit(0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void registerMember() throws IOException {
		MemberDto user = new MemberDto();

		// 계정 정보를 입력 받고 이미 존재하는 id인지 확인
		// 중복된 아이디가 있으면 check(user) = true
		do {
			System.out.println("********** 회원가입 **********");
			System.out.println("id를 입력해 주세요: ");
			user.setUserId(in.readLine());
			System.out.println("password를 입력해 주세요: ");
			user.setUserPass(in.readLine());
			System.out.println("이름을 입력해 주세요: ");
			user.setUserName(in.readLine());
		} while(memberService.check(user));

		memberService.registerMember(user);
		System.out.println("********** 회원가입 완료 **********");
	}

	private void login() throws IOException {

		System.out.println("********** 로그인 **********");
		System.out.println("id를 입력해 주세요: ");
		String userId = in.readLine();
		System.out.println("password를 입력해 주세요: ");
		String userPass = in.readLine();

		MemberDto user = memberService.login(userId, userPass);

		if(user == null) {
			System.out.println("로그인 정보를 다시 확인해 주세요");
		} else {
			System.out.println("로그인 완료");
			System.out.println(user.toString());
			loginStatus = true;
			id = user.getUserId();
		}
	}

	private void modifyMember() throws IOException {
		System.out.println("********** 회원정보 수정 **********");
		if(!loginStatus) {
			System.out.println("먼저 로그인 해주세요");
		} else {
			System.out.println("새로운 비밀번호 입력: ");
			String userPass = in.readLine();
			System.out.println("새로운 이름 입력: ");
			String userName = in.readLine();

			memberService.modifyMember(id, userPass, userName);
			System.out.println("********** 회원정보 수정 완료 **********");
		}
	}

	private void deleteMember() throws IOException {
		System.out.println("********** 회원탈퇴 **********");
		if(!loginStatus) {
			System.out.println("먼저 로그인 해주세요");
		} else {
			memberService.deleteMember(id);
			System.out.println("********** 탈퇴 완료 **********");
		}
	}

	public static void main(String[] args) {
		new MemberMain();
	}
}
