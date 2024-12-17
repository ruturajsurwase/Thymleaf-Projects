package com.app.service;

import java.time.LocalDateTime;

import java.util.List;


import com.app.entity.Attendance;

public interface AttendanceService {

	 public List<Attendance> getAttendanceByTeacher(Long teacherId);
	
	 public Attendance checkOut(Long attendanceId, LocalDateTime checkOutTime);
	 public Attendance checkIn(Attendance attendance);

	public Attendance getLatestAttendanceByTeacherId(Long id);

	public void checkOut(Attendance attendance);
	
	
	 public List<Attendance> getAttendancesByMonth(Long teacherId, String month, String year);
	 
	 
	// public List<Attendance> getAttendanceByMonthAndYear(Long teacherId, YearMonth yearMonth); 
	 
	 public List<Attendance> getAllAttendanceByTeacherId(Long teacherId);

	//List<Attendance> getAttendanceByMonthAndYear(Long teacherId, YearMonth yearMonth);
}