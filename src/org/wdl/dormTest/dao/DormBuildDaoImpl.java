package org.wdl.dormTest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.util.ConnectionFactory;

public class DormBuildDaoImpl implements DormBuildDao {

	@Override
	public DormBuild findByName(String name) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_dormbuild where name = ? ";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setString(1, name);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				DormBuild  build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setDisabled(rs.getInt("disabled"));
				build.setRemark(rs.getString("remark"));
				
				return build;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public void save(DormBuild build) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement =null;
		try {
			//② 准备SQL语句
			String sql = "INSERT INTO tb_dormbuild(NAME,remark,disabled) VALUES(?,?,?) ";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setString(1, build.getName());
			preparedStatement.setString(2, build.getRemark());
			preparedStatement.setInt(3, build.getDisabled());
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			 preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	@Override
	public List<DormBuild> find() {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_dormbuild where disabled=0";
			//③ 获取集装箱或者说是车
			preparedStatement = connection.prepareStatement(sql);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			List<DormBuild> builds = new ArrayList<DormBuild>();
			while (rs.next()) {
				DormBuild  build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setDisabled(rs.getInt("disabled"));
				build.setRemark(rs.getString("remark"));
				
				builds.add(build);
			}
			return builds;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public DormBuild findById(Integer id) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet  rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_dormbuild where id = ? ";
			//③ 获取集装箱或者说是车
			preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setInt(1, id);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			while (rs.next()) {
				DormBuild  build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setDisabled(rs.getInt("disabled"));
				build.setRemark(rs.getString("remark"));
				
				return build;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public void update(DormBuild build) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement =null;
		try {
			//② 准备SQL语句
			String sql = "UPDATE tb_dormbuild SET NAME = ?,remark =?,disabled = ? WHERE id = ?";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setString(1, build.getName());
			preparedStatement.setString(2, build.getRemark());
			preparedStatement.setInt(3, build.getDisabled());
			preparedStatement.setInt(4, build.getId());
			
			//④执行SQL,更新
			 preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	@Override
	public void saveManagerAndBuild(Integer userId, String[] dormBuildIds) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			//② 准备SQL语句
			String sql = "INSERT INTO tb_manage_dormbuild(USER_ID,DormBuild_id) VALUE(?,?)";
			//③ 获取集装箱或者说是车
			preparedStatement = connection.prepareStatement(sql);
			
			for (String dormBuildId : dormBuildIds) {
				//索引从1开始
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, Integer.parseInt(dormBuildId));
				
				//将sql语句添加到批处理
				preparedStatement.addBatch();
			}
			//执行批处理
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
		
	}

	//根据用户id，查询其管理的所有宿舍楼
	@Override
	public List<DormBuild> findByUserId(Integer id) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//② 准备SQL语句
			String sql = "SELECT tb_dormbuild.* FROM tb_manage_dormbuild " + 
					" LEFT JOIN tb_dormbuild   ON tb_dormbuild.`id` = tb_manage_dormbuild.`dormBuild_id` " + 
					" WHERE  tb_manage_dormbuild.user_id = ?";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setInt(1, id);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			  List<DormBuild>  builds = new ArrayList<>();
			while (rs.next()) {
				DormBuild  build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setDisabled(rs.getInt("disabled"));
				build.setRemark(rs.getString("remark"));
				
				builds.add(build);
			}
			return builds;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}

	@Override
	public void deleteByUserId(Integer id) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement =null;
		try {
			//② 准备SQL语句
			String sql = "DELETE FROM tb_manage_dormbuild WHERE user_id =?";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setInt(1, id);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			 preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	@Override
	public List<DormBuild> findAll() {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//② 准备SQL语句
			String sql = "select * from tb_dormbuild where disabled=0";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			//④执行SQL,获取执行后的结果,查询的结果封装在ResultSet
			  rs = preparedStatement.executeQuery();
			
			//因为查询出来的结果包括表头信息，所以要指针下移一行，看是否有查询出来的数据
			//如有数据，就进入循环体，封装该行数据
			  List<DormBuild>  builds = new ArrayList<>();
			while (rs.next()) {
				DormBuild  build = new DormBuild();
				build.setId(rs.getInt("id"));
				build.setName(rs.getString("name"));
				build.setDisabled(rs.getInt("disabled"));
				build.setRemark(rs.getString("remark"));
				
				builds.add(build);
			}
			return builds;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, rs);
		}
		return null;
	}
}
