package org.wdl.dormTest.service;

import java.util.List;

import org.wdl.dormTest.bean.DormBuild;

public interface DormBuildService {

	DormBuild findByName(String name);

	void save(DormBuild build);

	List<DormBuild> find();

	DormBuild findById(Integer id);

	void update(DormBuild dormBuild);

	List<DormBuild> findByUserId(Integer id);

	void deleteByUserId(Integer id);

	void saveManagerAndBuild(Integer id, String[] dormBuildIds);

	List<DormBuild> findAll();

}
