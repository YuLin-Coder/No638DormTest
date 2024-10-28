package org.wdl.dormTest.service;


import java.util.List;

import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.util.PageModel;

public interface UserService {

	User findByStuCodeAndPass(String name, String password, String roleId);

	void saveManager(User user, String[] dormBuildIds);

	List<User> findManager(String searchType, String keyword);

	User findById(int id);

	void updateManager(User user);

	User findByStuCode(String stuCode);

	void saveStudent(User user);

	List<User> findStudent(String dormBuildId, String searchType, String keyword, User user, PageModel pageModel);

	Integer findTotalNum(String dormBuildId, String searchType, String keyword, User user);

	void updateStudent(User studentUpdate);

	User findByUserIdAndManager(Integer id, User user);

	User findStuCodeAndManager(String stuCode, User userCurr);

	void updatePassWord(User userCur);

	User findAdmin(String stuCode, String password);

	User findDormManage(String stuCode, String password);

	User findByManageId(int id);




}
