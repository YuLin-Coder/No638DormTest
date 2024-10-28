package org.wdl.dormTest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.util.ConnectionFactory;

public class UserDaoImpl implements UserDao {

	@Override
	public User findByStuCodeAndPass(String stuCode, String password, String roleId) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			String sql = "";
			
			if(roleId.equals("0")) {
				//超级管理员
				sql = "select * from tb_admin where stu_code = ? and password=?  and disabled = 0 and role_id = 0";
			}else if(roleId.equals("1")) {
				//宿舍管理员
				sql = "select * from tb_manage where stu_code = ? and password=?  and disabled = 0 and role_id = 1";
				
			}else if(roleId.equals("2")) {
				//学生
				sql = "select * from tb_student where stu_code = ? and password=?  and disabled = 0 and role_id = 2";
			}
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setString(1, stuCode);
			preparedStatement.setString(2, password);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setStuCode(rs.getString("stu_code"));
				
				return user;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		
		return null;
	}

	@Override
	public String findManagerStuCodeMax() {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			//IFNULL(参数1，参数2)函数，用于判断第一个表达式是否为NULL，如果为NULL，则返回第二个参数的值。如果不为NULL，就返回第一个参数的值
			String sql = "select IFNULL( Max(stu_code),20210621)+1 from tb_manage where role_id =1";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			rs.next();
			return rs.getString(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public Integer saveManager(User user) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  resultSet = null;
		try {
			//② 准备SQL语句
			//IFNULL(参数1，参数2)函数，用于判断第一个表达式是否为NULL，如果为NULL，则返回第二个参数的值。如果不为NULL，就返回第一个参数的值
			String sql = "INSERT INTO tb_manage(NAME,PASSWORD,stu_code,sex,tel,role_id,disabled) VALUE(?,?,?,?,?,?,?)";
			
			//③ 获取集装箱或者说是车  Statement.RETURN_GENERATED_KEYS指定返回生成的注解
			 preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getStuCode());
			preparedStatement.setString(4, user.getSex());
			preparedStatement.setString(5, user.getTel());
			preparedStatement.setInt(6, user.getRoleId());
			preparedStatement.setInt(7, user.getDisabled());
			
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			 preparedStatement.executeUpdate();
			
			 resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			
			return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
		return null;
	}

	@Override
	public List<User> findManager(String sql) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			  List<User> users = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				users.add(user);
			}
			
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public User findById(int id) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_student where id = ?";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setInt(1, id);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public void updateManager(User user) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  resultSet = null;
		try {
			//② 准备SQL语句
			//IFNULL(参数1，参数2)函数，用于判断第一个表达式是否为NULL，如果为NULL，则返回第二个参数的值。如果不为NULL，就返回第一个参数的值
			String sql = "UPDATE tb_manage SET NAME= ? ,PASSWORD=?,sex=?,tel=?,disabled= ? ,stu_code=? WHERE id = ?";
			
			//③ 获取集装箱或者说是车  Statement.RETURN_GENERATED_KEYS指定返回生成的注解
			 preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getSex());
			preparedStatement.setString(4, user.getTel());
			preparedStatement.setInt(5, user.getDisabled());
			preparedStatement.setString(6, user.getStuCode());
			preparedStatement.setInt(7, user.getId());
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			 preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User findByStuCode(String stuCode) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_student where stu_code = ? ";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setString(1, stuCode);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				return user;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
				
		return null;
	}

	@Override
	public void saveStudent(User user) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  resultSet = null;
		try {
			//② 准备SQL语句
			//IFNULL(参数1，参数2)函数，用于判断第一个表达式是否为NULL，如果为NULL，则返回第二个参数的值。如果不为NULL，就返回第一个参数的值
			String sql = "INSERT INTO tb_student(NAME,PASSWORD,stu_code,dorm_code,sex,tel,dormBuildId,role_id,create_user_id) "
					+ "VALUE(?,?,?,?,?,?,?,?,?)";
			
			//③ 获取集装箱或者说是车  Statement.RETURN_GENERATED_KEYS指定返回生成的注解
			 preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getStuCode());
			preparedStatement.setString(4, user.getDormCode());
			preparedStatement.setString(5, user.getSex());
			preparedStatement.setString(6, user.getTel());
			preparedStatement.setInt(7, user.getDormBuildId());
			preparedStatement.setInt(8, user.getRoleId());
			preparedStatement.setInt(9, user.getCreateUserId());
			
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			 preparedStatement.executeUpdate();
			
			 /*resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			
			Integer id = resultSet.getInt(1);*/
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public List<User> findStudent(String sql) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			  List<User> users = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				user.setDormCode(rs.getString("dorm_code"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				
				DormBuild build = new DormBuild();
				build.setId(rs.getInt("dormBuildId"));
				build.setName(rs.getString("buildName"));
				build.setRemark(rs.getString("remark"));
				user.setDormBuild(build);
				
				users.add(user);
			}
			
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public Integer findTotalNum(String sql) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			rs.next();
			return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public void updateStudent(User user) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  resultSet = null;
		try {
			//② 准备SQL语句
			//IFNULL(参数1，参数2)函数，用于判断第一个表达式是否为NULL，如果为NULL，则返回第二个参数的值。如果不为NULL，就返回第一个参数的值
			String sql = "UPDATE tb_student SET NAME= ? ,PASSWORD=?,sex=?,tel=?,disabled= ?,stu_code=?,dorm_Code=?,dormBuildId=? WHERE id = ?";
			
			//③ 获取集装箱或者说是车  Statement.RETURN_GENERATED_KEYS指定返回生成的注解
			 preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getSex());
			preparedStatement.setString(4, user.getTel());
			preparedStatement.setInt(5, user.getDisabled());
			preparedStatement.setString(6, user.getStuCode());
			preparedStatement.setString(7, user.getDormCode());
			preparedStatement.setInt(8, user.getDormBuildId());
			preparedStatement.setInt(9, user.getId());
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			 preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User findByUserIdAndManager(String sql) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				return user;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public User findStuCodeAndManager(String sql) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				return user;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public void updatePassWord(User userCur) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  resultSet = null;
		try {
			//② 准备SQL语句
			String sql = "";
			if(userCur.getRoleId().equals(0)) {
				//超级管理员
				sql = "UPDATE tb_admin SET PASSWORD=? WHERE id = ?";
			}else if(userCur.getRoleId().equals(1)) {
				//宿舍管理员
				sql = "UPDATE tb_manage SET PASSWORD=? WHERE id = ?";
			}else if(userCur.getRoleId().equals(2)) {
				//学生
				sql = "UPDATE tb_student SET PASSWORD=? WHERE id = ?";
			}
			
			//③ 获取集装箱或者说是车  Statement.RETURN_GENERATED_KEYS指定返回生成的注解
			 preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userCur.getPassWord());
			preparedStatement.setInt(2, userCur.getId());
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			 preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public User findAdmin(String stuCode, String password) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_admin where stu_code = ? and password=?  and disabled = 0 and role_id=0";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setString(1, stuCode);
			preparedStatement.setString(2, password);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setStuCode(rs.getString("stu_code"));
				
				return user;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
				
		return null;
	}

	@Override
	public User findDormManage(String stuCode, String password) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_manage where stu_code = ? and password=?  and disabled = 0 and role_id=1";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setString(1, stuCode);
			preparedStatement.setString(2, password);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				return user;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
				
		return null;
	}

	@Override
	public User findByManageId(int id) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_manage where id = ? ";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setInt(1, id);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				User user = new User();
				//每一行的数据封装在一个实体bean中，根据字段名获取字段值，注意该字段是什么类型，就get什么类型
				user.setId(rs.getInt("id"));
				user.setDisabled(rs.getInt("disabled"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				return user;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	
}
