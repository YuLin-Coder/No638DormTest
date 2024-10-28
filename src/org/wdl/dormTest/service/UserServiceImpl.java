package org.wdl.dormTest.service;

import java.util.List;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.dao.DormBuildDao;
import org.wdl.dormTest.dao.DormBuildDaoImpl;
import org.wdl.dormTest.dao.UserDao;
import org.wdl.dormTest.dao.UserDaoImpl;
import org.wdl.dormTest.util.ConnectionFactory;
import org.wdl.dormTest.util.PageModel;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl();
	private DormBuildDao  dormBuildDao = new DormBuildDaoImpl();
	@Override
	public User findByStuCodeAndPass(String name, String password, String roleId) {
		return userDao.findByStuCodeAndPass(name,password,roleId);
	}
	@Override
	public void saveManager(User user,String[] dormBuildIds) {
			
		//保存宿舍管理员
		Integer userId = userDao.saveManager(user);
		System.out.println("userId:"+userId);
		
		//保存宿舍管理员和宿舍楼的中间表
		dormBuildDao.saveManagerAndBuild(userId,dormBuildIds);
	}
	
	
	@Override
	public List<User> findManager(String searchType, String keyword) {
		StringBuffer  sql = new StringBuffer("SELECT * FROM tb_manage WHERE role_id=1 ");
		
		if(keyword != null && !keyword.equals("")) {
			//说明用户是点击搜索按钮进行搜索
			if("name".equals(searchType)) {
				sql.append(" and name like '%"+keyword+"%'");
			}else if("sex".equals(searchType)) {
				sql.append(" and sex = '"+keyword.trim()+"'");
			}else if("tel".equals(searchType)) {
				sql.append(" and tel ="+keyword.trim());
			}
		}
		
		System.out.println("sql:"+sql.toString());
		
		//查询宿舍管理员
		List<User> users= userDao.findManager(sql.toString());
		for (User user : users) {
			List<DormBuild>  builds = dormBuildDao.findByUserId(user.getId());
			user.setDormBuilds(builds);
		}
		System.out.println("宿管员users:"+users);
		
		return users;
	}
	@Override
	public User findById(int id) {
		return userDao.findById(id);
	}
	@Override
	public void updateManager(User user) {
		userDao.updateManager(user);
	}
	@Override
	public User findByStuCode(String stuCode) {
		// TODO Auto-generated method stub
		return userDao.findByStuCode(stuCode);
	}
	@Override
	public void saveStudent(User user) {
		userDao.saveStudent(user);
	}
	@Override
	public List<User> findStudent(String dormBuildId, String searchType, String keyword,
			User user, PageModel pageModel) {
		
		StringBuffer  sql = new StringBuffer();
		//不管当前用户角色是怎么样，查询的都是学生，所有role_id=2
		sql.append("SELECT student.*,build.name buildName,build.*  FROM tb_student student " + 
				" LEFT JOIN tb_dormbuild  build ON build.`id` = student.dormBuildId "
				+ " where student.role_id = 2");
		
		
		if(keyword != null && !keyword.equals("") && "name".equals(searchType)) {
			//根据名字查询
			sql.append("  and  student.name like '%"+keyword.trim()+"%'");
			
		}else if(keyword != null && !keyword.equals("") && "stuCode".equals(searchType)) {
			//根据学号查询
			sql.append(" and student.stu_code = '"+keyword.trim()+"'");
			
		}else if(keyword != null && !keyword.equals("") && "dormCode".equals(searchType)) {
			//根据宿舍编号查询
			sql.append(" and student.dorm_code = '"+keyword.trim()+"'");
			
		}else if(keyword != null && !keyword.equals("") && "sex".equals(searchType)) {
			//根据性别查询
			sql.append(" and student.sex = '"+keyword.trim()+"'");
		}
		else if(keyword != null && !keyword.equals("") && "tel".equals(searchType)) {
			//根据电话号码查询
			sql.append(" and student.tel = '"+keyword.trim()+"'");
		}
		
		if(dormBuildId != null && !dormBuildId.equals("")) {
			//根据宿舍楼id查询
			sql.append(" and student.dormBuildId = '"+dormBuildId+"'");
		}
		
		//判断当前用户是否为宿舍管理员，如是则需查询其管理的所有宿舍楼id
		if(user.getRoleId().equals(1)) {
			//获取当前宿舍管理员管理的所有宿舍楼
			List<DormBuild>  builds  = dormBuildDao.findByUserId(user.getId());
			
			sql.append(" and student.dormBuildId in (");
			for (int i = 0; i < builds.size(); i++) {
				sql.append(builds.get(i).getId()+",");
			}
			
			//删除最后一个,
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		
		sql.append(" limit "+pageModel.getStartNum()+" , "+pageModel.getPageSize());
		System.out.println("sql:"+sql);
		
		List<User>  students = userDao.findStudent(sql.toString());
		System.out.println("students:"+students);
		
		return students;
	}
	@Override
	public Integer findTotalNum(String dormBuildId, String searchType, String keyword, User user) {
		StringBuffer  sql = new StringBuffer();
		//不管当前用户角色是怎么样，查询的都是学生，所有role_id=2
		sql.append("SELECT count(*)  FROM tb_student student " + 
				" LEFT JOIN tb_dormbuild  build ON build.`id` = student.dormBuildId "
				+ " where student.role_id = 2");
		
		
		if(keyword != null && !keyword.equals("") && "name".equals(searchType)) {
			//根据名字查询
			sql.append("  and  student.name like '%"+keyword.trim()+"%'");
			
		}else if(keyword != null && !keyword.equals("") && "stuCode".equals(searchType)) {
			//根据学号查询
			sql.append(" and student.stu_code = '"+keyword.trim()+"'");
			
		}else if(keyword != null && !keyword.equals("") && "dormCode".equals(searchType)) {
			//根据宿舍编号查询
			sql.append(" and student.dorm_code = '"+keyword.trim()+"'");
			
		}else if(keyword != null && !keyword.equals("") && "sex".equals(searchType)) {
			//根据性别查询
			sql.append(" and student.sex = '"+keyword.trim()+"'");
		}
		else if(keyword != null && !keyword.equals("") && "tel".equals(searchType)) {
			//根据电话号码查询
			sql.append(" and student.tel = '"+keyword.trim()+"'");
		}
		
		if(dormBuildId != null && !dormBuildId.equals("")) {
			//根据宿舍楼id查询
			sql.append(" and student.dormBuildId = '"+dormBuildId+"'");
		}
		
		/*sql.append(" and user.dormBuildId in (2,3,4)")*/
		//判断当前用户是否为宿舍管理员，如是则需查询其管理的所有宿舍楼id
		if(user.getRoleId().equals(1)) {
			//获取当前宿舍管理员管理的所有宿舍楼
			List<DormBuild>  builds  = dormBuildDao.findByUserId(user.getId());
			
			sql.append(" and student.dormBuildId in (");
			for (int i = 0; i < builds.size(); i++) {
				sql.append(builds.get(i).getId()+",");
			}
			
			//删除最后一个,
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		
		System.out.println("sql:"+sql);
		
		return userDao.findTotalNum(sql.toString());
	}
	@Override
	public void updateStudent(User studentUpdate) {
		userDao.updateStudent(studentUpdate);
	}
	@Override
	public User findByUserIdAndManager(Integer id, User user) {
		StringBuffer  sql = new StringBuffer();
		sql.append("select * from tb_student  user where user.id ="+id);
		
		if(user.getRoleId() != null && user.getRoleId().equals(1)) {
			//表示当前登录的用户角色是宿舍管理员，则要求要修改的学生必须居住在该宿舍管理员管理的宿舍楼中
			List<DormBuild>  builds = dormBuildDao.findByUserId(user.getId());
			
			sql.append(" and user.dormBuildId in (");
			for (int i = 0; i < builds.size(); i++) {
				sql.append(builds.get(i).getId()+",");//(1,2,)
			}
			
			//删除最后一个,
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		
		return userDao.findByUserIdAndManager(sql.toString());
	}
	@Override
	public User findStuCodeAndManager(String stuCode, User userCurr) {
		StringBuffer sql = new StringBuffer("SELECT * FROM tb_student USER WHERE user.role_id =2 and  user.`stu_code`="+stuCode);
		
		//获取当前登录用户的角色
		Integer roleId = userCurr.getRoleId();
		if(roleId != null && roleId.equals(1)) {
			//表示当前用户是宿舍管理员，通过宿舍管理员的id 获取其管理的宿舍楼
			List<DormBuild>  builds = dormBuildDao.findByUserId(userCurr.getId());
			
			sql.append(" and USER.dormBuildId in (");
			for (int i = 0; i < builds.size(); i++) {
				sql.append(builds.get(i).getId()+",");//(1,2,)
			}
			
			//删除最后一个,
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
			
			return userDao.findStuCodeAndManager(sql.toString());
		}
		
		if(roleId != null && roleId.equals(2)) {
			//当前登录用户是学生，没有添加任何学生缺勤记录的权限
			return null;
		}
		
		//表示当前用户是超级管理员，只有用户输入的学号在数据库真实存在，就可以添加任意学生的缺勤记录
		return  userDao.findStuCodeAndManager(sql.toString());
	}
	@Override
	public void updatePassWord(User userCur) {
		userDao.updatePassWord(userCur);
	}
	@Override
	public User findAdmin(String stuCode, String password) {
		return userDao.findAdmin(stuCode,password);
	}
	@Override
	public User findDormManage(String stuCode, String password) {
		return userDao.findDormManage(stuCode,password);
	}
	@Override
	public User findByManageId(int id) {
		return userDao.findByManageId(id);
	}

}
