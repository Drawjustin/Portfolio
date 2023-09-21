package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.model.dao.AttractionDao;
import com.ssafy.enjoytrip.model.dao.AttractionDaoImpl;
import java.util.List;

public class AttractionServiceImpl implements AttractionService {
	private static AttractionService instance = new AttractionServiceImpl();
	private final AttractionDao attractionDao = AttractionDaoImpl.getInstance();

	private AttractionServiceImpl() {
	}

	public static AttractionService getInstance() {
		return instance;
	}

	@Override
	public List<AttractionInfoDto> attractionList(AttractionInfoDto attractionInfoDto) {
		return attractionDao.attractionList((attractionInfoDto));
	}

	@Override
	public List<AttractionInfoDto> searchByTitle(String title, int sidoCode) {
		return attractionDao.searchByTitle(title, sidoCode);
	}
}
