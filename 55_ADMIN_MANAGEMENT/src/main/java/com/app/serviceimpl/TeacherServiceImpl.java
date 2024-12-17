package com.app.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.app.entity.Teacher;
import com.app.repository.TeacherRepository;
import com.app.service.TeacherService;


@Service
public class TeacherServiceImpl implements TeacherService {

	
	@Autowired
	TeacherRepository teacherRepository;
	
	
	@Override
	public List<Teacher> getAllTeachers()
	{
		return teacherRepository.findAll();
	}

	@Override
	public void saveTeacher(Teacher teacher)
	{
		teacherRepository.save(teacher);
		
	}

	@Override
	public Teacher getTeacherById(Long id)
	{
		 Optional<Teacher> optional = teacherRepository.findById(id);
		 Teacher teacher = null;
	        if (optional.isPresent()) 
	        {
	        	teacher = optional.get();
	        } else
	        {
	            throw new RuntimeException("Teacher not found for id :: " + id);
	        }
	        return teacher;
	}

	@Override
	public void deleteTeacherById(Long id) {
		
		teacherRepository.deleteById(id);
		
	}

	@Override
	public Teacher findById(Long id) 
	{
		Optional<Teacher> optional = teacherRepository.findById(id);
		return null;
	}

	@Override
	public Teacher getByUsername(String username) {
		
		return teacherRepository.findByEmail(username);
	}

	@Override
	public void updateTeacher(Teacher teacher)
	{
		
		double salaryPerMonth = teacher.getSalaryPerMonth();
		double basicpay	=salaryPerMonth * 40/100;
		double hra	=salaryPerMonth * 10/100;
		double convenience	=salaryPerMonth * 15/100;
		double education	=salaryPerMonth * 10/100;
		double washing	=salaryPerMonth * 5/100;
		double medical	=salaryPerMonth * 10/100;
		double dearness	=salaryPerMonth * 10/100;
		
		
		double professionalTax = 200;

		teacher.setBasicPay(basicpay);
		teacher.setHra(hra);
		teacher.setConveyanceAllowance(convenience);
		teacher.setEducationAllowance(education);
		teacher.setWashingAllowance(washing);
		teacher.setMedicalAllowance(medical);
		teacher.setDearnessAllowance(dearness);
		teacher.setDearnessAllowance(dearness);
		teacher.setDeductions(professionalTax);
		
		teacherRepository.save(teacher);
		
	}
	
	 
	
	

}
