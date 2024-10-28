package org.wdl.dormTest.service;

import java.util.List;

import org.wdl.dormTest.bean.DormBuild;
import org.wdl.dormTest.bean.Record;
import org.wdl.dormTest.bean.User;
import org.wdl.dormTest.dao.DormBuildDao;
import org.wdl.dormTest.dao.DormBuildDaoImpl;
import org.wdl.dormTest.dao.RecordDao;
import org.wdl.dormTest.dao.RecordDaoImpl;
import org.wdl.dormTest.util.PageModel;

public class RecordServiceImpl implements RecordService {

	private RecordDao recordDao = new RecordDaoImpl();
	private DormBuildDao  dormBuildDao = new DormBuildDaoImpl();
	@Override
	public void save(Record record) {
		recordDao.save(record);
	}
	@Override
	public Integer getToTalNum(String startDate, String endDate, String dormBuildId, 
			String searchType, String keyword,
			User userCurr) {
		StringBuffer sql = new StringBuffer("SELECT count(*) FROM tb_record record" + 
				" LEFT JOIN tb_student USER ON user.id = record.student_id  where 1=1 ");
		
		if(keyword != null &&  !keyword.equals("") && "name".equals(searchType)) {
			sql.append(" and USER.name like '%"+keyword.trim()+"%'");
			
		}else if(keyword != null &&  !keyword.equals("") && "sex".equals(searchType)) {
			sql.append(" and  USER.sex ='"+keyword.trim()+"'");
			
		}else if(keyword != null &&  !keyword.equals("") && "stuCode".equals(searchType)) {
			sql.append(" and  USER.stu_Code ='"+keyword.trim()+"'");
			
		}else if(keyword != null &&  !keyword.equals("") && "dormCode".equals(searchType)) {
			sql.append(" and  USER.dorm_Code ='"+keyword.trim()+"'");
		}
		
		if(dormBuildId != null && !dormBuildId.equals("")) {
			sql.append(" and USER.dormBuildId = "+dormBuildId);
		}
		
		if(startDate != null && !startDate.equals("")) {
			//查询处理的考勤记录时间要大于查询的开始时间
			sql.append(" and record.date >= '"+startDate+"'");
		}
		
		if(endDate != null && !endDate.equals("")) {
			//查询处理的考勤记录时间要小于查询的结束时间
			sql.append(" and record.date <= '"+endDate+"'");
		}
		
		//获取当前登录用户的角色
		Integer roleId = userCurr.getRoleId();
		if(roleId != null && roleId.equals(1)) {
			//当前登录用户是宿舍管理员
			//获取当前宿舍管理员管理的所有宿舍楼
			List<DormBuild>  builds  = dormBuildDao.findByUserId(userCurr.getId());
			
			sql.append(" and USER.dormBuildId in (");
			for (int i = 0; i < builds.size(); i++) {
				sql.append(builds.get(i).getId()+",");
			}
			
			//删除最后一个,
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		
		if(roleId != null && roleId.equals(2)) {
			//当前登录用户未学生
			sql.append(" and USER.ID = "+userCurr.getId());
			
		}
		
		System.out.println("sql:"+sql.toString());
		return recordDao.getTotalNum(sql.toString());
	}
	@Override
	public List<Record> findRecords(String startDate, String endDate, String dormBuildId, String searchType,
			String keyword, User userCurr, PageModel pageModel) {
		StringBuffer sql = new StringBuffer("SELECT record.*,record.id recordId,record.disabled recordDisabled,user.*,bulid.name buildName FROM tb_record record" + 
				" LEFT JOIN tb_student USER ON user.id = record.student_id  " + 
				" LEFT JOIN tb_dormbuild bulid ON bulid.id = User.`dormBuildId`" + 
				" WHERE 1=1 ");
		
		if(keyword != null &&  !keyword.equals("") && "name".equals(searchType)) {
			sql.append(" and USER.name like '%"+keyword.trim()+"%'");
			
		}else if(keyword != null &&  !keyword.equals("") && "sex".equals(searchType)) {
			sql.append(" and  USER.sex ='"+keyword.trim()+"'");
			
		}else if(keyword != null &&  !keyword.equals("") && "stuCode".equals(searchType)) {
			sql.append(" and  USER.stu_Code ='"+keyword.trim()+"'");
			
		}else if(keyword != null &&  !keyword.equals("") && "dormCode".equals(searchType)) {
			sql.append(" and  USER.dorm_Code ='"+keyword.trim()+"'");
		}
		
		if(dormBuildId != null && !dormBuildId.equals("")) {
			sql.append(" and USER.dormBuildId = "+dormBuildId);
		}
		
		if(startDate != null && !startDate.equals("")) {
			//查询处理的考勤记录时间要大于查询的开始时间
			sql.append(" and record.date >= '"+startDate+"'");
		}
		
		if(endDate != null && !endDate.equals("")) {
			//查询处理的考勤记录时间要小于查询的结束时间
			sql.append(" and record.date <= '"+endDate+"'");
		}
		
		//获取当前登录用户的角色
		Integer roleId = userCurr.getRoleId();
		if(roleId != null && roleId.equals(1)) {
			//当前登录用户是宿舍管理员
			//获取当前宿舍管理员管理的所有宿舍楼
			List<DormBuild>  builds  = dormBuildDao.findByUserId(userCurr.getId());
			
			sql.append(" and USER.dormBuildId in (");
			for (int i = 0; i < builds.size(); i++) {
				sql.append(builds.get(i).getId()+",");
			}
			
			//删除最后一个,
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		
		if(roleId != null && roleId.equals(2)) {
			//当前登录用户未学生
			sql.append(" and USER.ID = "+userCurr.getId());
		}
		
		//缺勤日期递减查询
		sql.append(" order by record.date desc");
		
		//分页查询
		sql.append(" limit "+pageModel.getStartNum()+" , "+pageModel.getPageSize());
		System.out.println("sql:"+sql.toString());
		
		return recordDao.find(sql.toString());
	}
	@Override
	public Record findById(int id) {
		return recordDao.findById(id);
	}
	@Override
	public void update(Record record) {
		recordDao.update(record);
	}

}
