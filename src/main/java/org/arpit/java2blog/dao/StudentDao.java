package org.arpit.java2blog.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arpit.java2blog.model.Course;
import org.arpit.java2blog.model.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional

public class StudentDao {
	
	@Autowired
	SessionFactory sessionfactory;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionfactory = sf;
	}
	 
	 
	 public void addStudent(Student student) {
		 
		 
		 Session session=this.sessionfactory.getCurrentSession();
		 
		 session.saveOrUpdate(student);
		 
		 
		 
	 }


	public List<Student> getAllStudent() {
	
		String hql ="select s.id,s.name,s.marks, s.address,s.dob,s.phno,c.coursename as course,s.course as courseid from Student s,Course c where s.course=c.id";
		
		 /*Session session=this.sessionfactory.getCurrentSession();
		 
		 Query query=session.createQuery(hql);//here persistent class name is Emp  
		 List<Student> list=query.list();
		 
		 System.out.println(list);*/
		 RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		      return  this.jdbcTemplate.query(hql, rowMapper);
		
		
		
	}

	public void deleteStudent(int id) {
		 

		String hql="delete from Student where id="+id;
		
		System.out.println(hql);
		 Session session=this.sessionfactory.getCurrentSession();
		Query query=session.createQuery(hql);
		
		//query.setParameter("id", String.valueOf(id));
		int status=query.executeUpdate();
		if(status==1)
			System.out.println("record deleted");
		else
			System.out.println("record not deleted");						
		
	


}
	
	public Student getStudentById(int parseInt) {
		// TODO Auto-generated method stub
		
		String hql="from Student where id="+parseInt ;
		Session session=this.sessionfactory.getCurrentSession();
		Query query=session.createQuery(hql);
		
		List<Student> list= query.list();
		
		if(list.isEmpty())
		{
			
			return null;
		}
		else
		{
		return list.get(0);
		}
	}
public boolean checkUserExistsOrNot(Student student)
	
	{
		String hql="from Student where phno="+student.getPhno();
		Session session=this.sessionfactory.getCurrentSession();
		Query query=session.createQuery(hql);
		
		List<Student> list= query.list();
		if(list.size()>0)
		{
			return true;
		}
			
		else
		{
			return false;
		}
		
	}

	

public List<Course> getAllCourse(){
		
		String hql="from Course";
		Session session=this.sessionfactory.getCurrentSession();
		Query query=session.createQuery(hql);
		List<Course> list=query.list();
		
		System.out.println(list);
		
		return list;
		
		}
	
	 public Map<Integer,String> getCourseMap(){
		 
		 List<Course> list=getAllCourse();
		 Map<Integer,String> map=new HashMap<Integer,String>();
		 for(Course  entry:list) {
			 
			 map.put(entry.getId(),entry.getCoursename());
			 
			 
			 
		 }
		return map;
		 
}
	
}
