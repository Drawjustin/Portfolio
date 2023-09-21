package com.ssafy.board.model.service;

import java.util.List;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.dao.BoardDao;
import com.ssafy.board.model.dao.BoardDaoImpl;

public class BoardServiceImpl implements BoardService{
	private static BoardService instance = new BoardServiceImpl();
	private final BoardDao boardDao = BoardDaoImpl.getInstance();
	
	public static BoardService getInstance() {
		return instance;
	}
	
	
	private BoardServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void registerArticle(BoardDto boardDto) {
		boardDao.registerArticle(boardDto);
		
	}

	@Override
	public List<BoardDto> searchListAll() {
		return boardDao.searchListAll();
		
	}

	@Override
	public List<BoardDto> searchListBySubject(String subject) {
		return boardDao.searchListBySubject(subject);
	}

	@Override
	public BoardDto viewArticle(int no) {
		return boardDao.viewArticle(no);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) {
		boardDao.modifyArticle(boardDto);
		
	}

	@Override
	public void deleteArticle(int no) {
		boardDao.deleteArticle(no);
		
	}

}
