package org.arpit.java2blog.controller;

import java.util.List;

import org.arpit.java2blog.dao.StudentDao;
import org.arpit.java2blog.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {
	
	
	@Autowired
	StudentDao studentDao;
	
	@RequestMapping(value = "/stu", method = RequestMethod.GET, headers = "Accept=application/json")
	private String showStudentPage(Model model) {
		
		model.addAttribute("stubean",new Student());
		 List<Student> list = studentDao.getAllStudent();
		 model.addAttribute("list",list);
		 
		 System.out.println(studentDao.getCourseMap());
		 model.addAttribute("roles", studentDao.getCourseMap());
		   
		   
		return "Student";
		
		
		
	}
	
	/*@RequestMapping(value="/stu" , method=RequestMethod.POST,headers = "Accept=application/json")
	public String saveStudent(@ModelAttribute Student student)
	{
		
		
		System.out.println(student);
		
		studentDao.addStudent(student);
		
		return "redirect:stu";
		if we use duplicate checking avoid this save code
		
		
	}*/
	
	@RequestMapping(value = "/deleteStudent", method = RequestMethod.GET, headers = "Accept=application/json")
	public String deleteStudent(@RequestParam("id") String id,RedirectAttributes redir)
	{
		studentDao.deleteStudent(Integer.parseInt(id));
		redir.addFlashAttribute("msg", "Record deleted successfully");
		 redir.addFlashAttribute("cssMsg", "danger");
		
		return "redirect:stu";
		
	
	}
	@RequestMapping(value = "/editStudent", method = RequestMethod.GET, headers = "Accept=application/json")
	public String editStudent(@RequestParam("id") String id,Model model,RedirectAttributes redir)
	{
		Student student =studentDao.getStudentById(Integer.parseInt(id));
		System.out.println(student);
		model.addAttribute("stubean" ,student);
		
		List<Student> list=studentDao.getAllStudent();
		
		model.addAttribute("roles", studentDao.getCourseMap());
		model.addAttribute("list",list);
		return "Student";
		
	
	}
	
	@RequestMapping(value = "/stu", method = RequestMethod.POST, headers = "Accept=application/json")
	public String stuDuplicatesChecking(@ModelAttribute Student student,RedirectAttributes redir)
	{
		
		if(student.getId() ==0)
		{
		System.out.println(student);
		boolean result=studentDao.checkUserExistsOrNot(student);
		if(result==false) 
		{
		
			studentDao.addStudent(student);
		//System.out.println("Record Inserted Successfully"); it use when without using redirectattributes like css messages
		redir.addFlashAttribute("msg", "Record Inserted Successfully");
		redir.addFlashAttribute("cssMsg", "success");
		}
		else
		{
			//System.out.println("record alreday exists");
			redir.addFlashAttribute("msg", "Record Already Exists");
			redir.addFlashAttribute("cssMsg", "warning");

		}
		
		}
		
		else  //Edit recored comes here
		{
			 redir.addFlashAttribute("msg", "Record updated successfully");
			 redir.addFlashAttribute("cssMsg", "primary");

			studentDao.addStudent(student);
			
			
			
			
			
			
		}
		
		return "redirect:stu";	
		
		
		
		//**This is for duplicate checking**
	
	}
	
    
	

}
