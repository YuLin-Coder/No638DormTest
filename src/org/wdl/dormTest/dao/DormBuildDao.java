package org.wdl.dormTest.dao;

import java.sql.Connection;
import java.util.List;

import org.wdl.dormTest.bean.DormBuild;

public interface DormBuildDao {

	DormBuild findByName(String name);

	void save(DormBuild build);

	List<DormBuild> find();

	DormBuild findById(Integer id);

	void update(DormBuild dormBuild);

	void saveManagerAndBuild(Integer userId, String[] dormBuildIds);

	List<DormBuild> findByUserId(Integer id);

	void deleteByUserId(Integer id);

	List<DormBuild> findAll();

}
