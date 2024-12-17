package com.app.service;

import java.util.List;


import com.app.entity.Teacher;

public interface TeacherService  {

	public List<Teacher> getAllTeachers();

	public void saveTeacher(Teacher teacher);

	public Teacher getTeacherById(Long id);

	public void deleteTeacherById(Long id);

	public Teacher findById(Long id);

	public Teacher getByUsername(String username);

	public void updateTeacher(Teacher teacher);
}
