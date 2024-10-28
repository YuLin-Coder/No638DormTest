package org.wdl.dormTest.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.Record;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.util.ConnectionFactory;

public class RecordDaoImpl implements RecordDao {

	@Override
	public void save(Record record) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement =null;
		try {
			//② 准备SQL语句
			String sql = "INSERT INTO tb_record(student_id,DATE,remark,disabled) VALUES(?,?,?,?)";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setInt(1,record.getStudentId());
			preparedStatement.setDate(2, new Date(record.getDate().getTime()));
			preparedStatement.setString(3, record.getRemark());
			preparedStatement.setInt(4, record.getDisabled());
			
			//④执行SQL,更新
			 preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

	@Override
	public Integer getTotalNum(String sql) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement =null;
		try {
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			//④执行SQL,更新
			ResultSet rs =  preparedStatement.executeQuery();
			 rs.next();
			Integer  count =  rs.getInt(1);
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
		return null;
	}

	@Override
	public List<Record> find(String sql) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement =null;
		try {
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			
			//④执行SQL,更新
			ResultSet rs =  preparedStatement.executeQuery();
			List<Record> records = new ArrayList<Record>();
			
			while (rs.next()) {
				Record record = new Record();
				record.setId(rs.getInt("recordId"));
				record.setDate(rs.getTimestamp("date"));
				record.setRemark(rs.getString("remark"));
				record.setDisabled(rs.getInt("recordDisabled"));
				
				User user = new User();
				user.setId(rs.getInt("student_id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
			
				DormBuild dormBuild = new DormBuild();
				dormBuild.setName(rs.getString("buildName"));
				
				user.setDormBuild(dormBuild);
				record.setUser(user);
				
				records.add(record);
				
			}
			return records;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
		return null;
	}

	@Override
	public Record findById(int id) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement =null;
		try {
			StringBuffer sql = new StringBuffer("SELECT record.*,record.id recordId,record.disabled recordDisabled,student.* FROM tb_record record" + 
					" LEFT JOIN tb_student student ON student.id = record.student_id  " + 
					" WHERE record.id=?");
			
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql.toString());
			 preparedStatement.setInt(1, id);
			
			//④执行SQL,更新
			ResultSet rs =  preparedStatement.executeQuery();
			while (rs.next()) {
				Record record = new Record();
				record.setId(rs.getInt("recordId"));
				record.setStudentId(rs.getInt("student_id"));
				record.setDate(rs.getTimestamp("date"));
				record.setRemark(rs.getString("remark"));
				record.setDisabled(rs.getInt("recordDisabled"));
				
				User user = new User();
				user.setId(rs.getInt("student_id"));
				user.setCreateUserId(rs.getInt("create_user_id"));
				user.setDormBuildId(rs.getInt("dormBuildId"));
				user.setDormCode(rs.getString("dorm_Code"));
				user.setName(rs.getString("name"));
				user.setPassWord(rs.getString("passWord"));
				user.setRoleId(rs.getInt("role_id"));
				user.setSex(rs.getString("sex"));
				user.setStuCode(rs.getString("stu_code"));
				user.setTel(rs.getString("tel"));
				
				record.setUser(user);
				return record;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
		return null;
	}

	@Override
	public void update(Record record) {
		//① 获取连接（数据库地址  用户名 密码）
		Connection  connection = 	ConnectionFactory.getConnection();
		PreparedStatement preparedStatement =null;
		try {
			//② 准备SQL语句
			String sql = "update tb_record set student_id = ?,DATE =? ,remark=?,disabled=? where id=?";
			//③ 获取集装箱或者说是车
			 preparedStatement = connection.prepareStatement(sql);
			//索引从1开始
			preparedStatement.setInt(1,record.getStudentId());
			preparedStatement.setDate(2, new Date(record.getDate().getTime()));
			preparedStatement.setString(3, record.getRemark());
			preparedStatement.setInt(4, record.getDisabled());
			preparedStatement.setInt(5, record.getId());
			
			//④执行SQL,更新
			 preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(connection, preparedStatement, null);
		}
	}

}
