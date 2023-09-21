package com.ssafy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.board.model.service.BoardServiceImpl;

public class BoardMain {

	private BufferedReader in;
	private final BoardService boardService = BoardServiceImpl.getInstance();
	public BoardMain() {
		in = new BufferedReader(new InputStreamReader(System.in));
		menu();
	}

	private void menu() {
		while (true) {
			System.out.println("---------- 게시판 메뉴 ----------");
			System.out.println("1. 글등록");
			System.out.println("2. 글목록(전체)");
			System.out.println("3. 글검색(제목)");
			System.out.println("4. 글보기");
			System.out.println("5. 글수정");
			System.out.println("6. 글삭제");
			System.out.println("-------------------------------------");
			System.out.println("0. 프로그램 종료");
			System.out.println("-------------------------------------");
			System.out.print("메뉴 선택 : ");
			try {
				int num = Integer.parseInt(in.readLine());
				switch (num) {
				case 1:
					registerArticle();
					break;
				case 2:
					searchListAll();
					break;
				case 3:
					searchListBySubject();
					break;
				case 4:
					viewArticle();
					break;
				case 5:
					modifyArticle();
					break;
				case 6:
					deleteArticle();
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

	private void registerArticle() throws IOException {
		System.out.println("---------- 아이디를 입력해주세요 ----------");
		String userId = in.readLine();
		System.out.println("---------- 제목을 입력해주세요 ----------");
		String subject = in.readLine();
		System.out.println("---------- 내용을 입력해주세요 ----------");
		StringBuilder stb = new StringBuilder();
		while(true) {
			String content = in.readLine();
			if(content.equals("q"))break;
			stb.append(content);
			stb.append("\n");
		}
		String s = stb.toString();
		BoardDto boardDto = new BoardDto();
		boardDto.setUserId(userId);
		boardDto.setSubject(subject);
		boardDto.setContent(s);
		
		boardService.registerArticle(boardDto);
		System.out.println("---------- 글 작성 완료 ----------");
	}

	private void searchListAll() {
		List<BoardDto> list = boardService.searchListAll();
		for(BoardDto i : list)
			System.out.println(i.toString());
	}

	private void searchListBySubject() throws IOException {
		System.out.println("---------- 보고자 하는 글의 제목을 입력해주세요 ----------");
		String subject = in.readLine();
		List<BoardDto> list = boardService.searchListBySubject(subject);
		for(BoardDto i : list)
			System.out.println(i.toString());
	}

	private void viewArticle() throws IOException {
		System.out.println("---------- 보고자 하는 글의 번호를 입력해주세요 ----------");
		int no = Integer.parseInt(in.readLine());
		BoardDto boardDto = boardService.viewArticle(no);
		System.out.println(boardDto.toString());
	}

	private void modifyArticle() throws NumberFormatException, IOException {
		System.out.println("---------- 변경할 글의 번호를 입력해주세요 ----------");
		BoardDto boardDto =  new BoardDto();
		int no = Integer.parseInt(in.readLine());
		System.out.println("---------- 제목을 입력해주세요 ----------");
		String subject = in.readLine();
		System.out.println("---------- 내용을 입력해주세요 ----------");
		StringBuilder stb = new StringBuilder();
		while(true) {
			String content = in.readLine();
			if(content.equals("q"))break;
			stb.append(content);
			stb.append("\n");
		}
		String s =stb.toString();
		
		boardDto.setArticleNo(no);
		boardDto.setSubject(subject);
		boardDto.setContent(s);
		boardService.modifyArticle(boardDto);
		System.out.println("----------  변경 완료  ----------");
	}

	private void deleteArticle() throws IOException {
		System.out.println("---------- 삭제할 글의 번호를 입력해주세요 ----------");
		int no = Integer.parseInt(in.readLine());
		boardService.deleteArticle(no);
		System.out.println("---------- 삭제 완료 ----------");
	
	}

	public static void main(String[] args) {
		new BoardMain();
	}
}
