package org.wdl.dormTest.dao;


import java.util.List;

import org.wdl.dormTest.bean.User;

public interface UserDao {

	User findByStuCodeAndPass(String name, String password, String roleId);

	String findManagerStuCodeMax();

	Integer saveManager(User user);

	List<User> findManager(String sql);

	User findById(int id);

	void updateManager(User user);

	User findByStuCode(String stuCode);

	void saveStudent(User user);

	List<User> findStudent(String sql);

	Integer findTotalNum(String sql);

	void updateStudent(User studentUpdate);

	User findByUserIdAndManager(String sql);

	User findStuCodeAndManager(String sql);

	void updatePassWord(User userCur);

	User findAdmin(String stuCode, String password);

	User findDormManage(String stuCode, String password);

	User findByManageId(int id);



}
