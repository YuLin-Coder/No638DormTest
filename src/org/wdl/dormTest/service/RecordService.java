package org.wdl.dormTest.service;

import java.util.List;

import org.wdl.dormTest.bean.Record;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.util.PageModel;

public interface RecordService {

	void save(Record record);

	Integer getToTalNum(String startDate, String endDate, String dormBuildId, String searchType, String keyword,
			User userCurr);

	List<Record> findRecords(String startDate, String endDate, String dormBuildId, String searchType, String keyword,
			User userCurr, PageModel pageModel);

	Record findById(int id);

	void update(Record record);


}
