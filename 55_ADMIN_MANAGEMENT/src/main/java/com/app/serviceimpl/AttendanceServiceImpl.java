package com.app.serviceimpl;

import java.time.LocalDateTime;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.entity.Attendance;
import com.app.repository.AttendanceRepository;
import com.app.service.AttendanceService;

import jakarta.transaction.Transactional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private AttendanceRepository attendanceRepository;

	public List<Attendance> getAttendanceByTeacher(Long teacherId) {
		return attendanceRepository.findByTeacherId(teacherId);
	}

	public Attendance checkIn(Attendance attendance) {
		return attendanceRepository.save(attendance);
	}

	public Attendance checkOut(Long attendanceId, LocalDateTime checkOutTime)
	{
		Attendance attendance = attendanceRepository.findById(attendanceId).orElse(null);
		if (attendance != null) {
			attendance.setCheckOut(checkOutTime);
			return attendanceRepository.save(attendance);
		}
		return null;
	}

	@Override
	public Attendance getLatestAttendanceByTeacherId(Long id) {
		List<Attendance> attendances = attendanceRepository.findLatestAttendanceByTeacherId(id, PageRequest.of(0, 1));
		if (!attendances.isEmpty()) {
			return attendances.get(0); // Return the first (latest) result
		}
		return null; // If no attendance records found
	}

	@Override
	public void checkOut(Attendance attendance) {
		// Set the checkOut time to the current time
		attendance.setCheckOut(LocalDateTime.now());

		// Save the updated attendance record back to the database
		attendanceRepository.save(attendance);

	}

	@Override
    @Transactional
	public List<Attendance> getAttendancesByMonth(Long teacherId, String month, String year)
	{
		return attendanceRepository.findByTeacherIdAndMonthAndYear(teacherId, month, year);
	}



	public List<Attendance> getAllAttendanceByTeacherId(Long teacherId) {

		return attendanceRepository.findByTeacherId(teacherId);
	}


}
