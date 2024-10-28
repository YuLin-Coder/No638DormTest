package org.wdl.dormTest.service;

import java.util.List;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.dao.DormBuildDao;
import org.wdl.dormTest.dao.DormBuildDaoImpl;

public class DormBuildServiceImpl implements DormBuildService {
	private DormBuildDao dormBuildDao = new DormBuildDaoImpl();
	@Override
	public DormBuild findByName(String name) {
		return dormBuildDao.findByName(name);
	}
	@Override
	public void save(DormBuild build) {
		dormBuildDao.save(build);
	}
	@Override
	public List<DormBuild> find() {
		return dormBuildDao.find();
	}
	@Override
	public DormBuild findById(Integer id) {
		return dormBuildDao.findById(id);
	}
	@Override
	public void update(DormBuild dormBuild) {
		dormBuildDao.update(dormBuild);
	}
	@Override
	public List<DormBuild> findByUserId(Integer id) {
		return dormBuildDao.findByUserId(id);
	}
	@Override
	public void deleteByUserId(Integer id) {
		dormBuildDao.deleteByUserId(id);
	}
	@Override
	public void saveManagerAndBuild(Integer id, String[] dormBuildIds) {
		dormBuildDao.saveManagerAndBuild(id, dormBuildIds);
	}
	@Override
	public List<DormBuild> findAll() {
		return dormBuildDao.findAll();
	}

}
