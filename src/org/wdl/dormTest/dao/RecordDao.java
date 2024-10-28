package org.wdl.dormTest.dao;

import java.util.List;

import org.wdl.dormTest.bean.Record;

public interface RecordDao {

	void save(Record record);

	Integer getTotalNum(String sql);

	List<Record> find(String sql);

	Record findById(int id);

	void update(Record record);

}
