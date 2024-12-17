package com.app.repository;

import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Attendance;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByTeacherId(Long teacherId);
	
	@Query("SELECT a FROM Attendance a WHERE a.teacher.id = :teacherId ORDER BY a.checkIn DESC")
    List<Attendance> findLatestAttendanceByTeacherId(@Param("teacherId") Long teacherId, PageRequest pageRequest);

	
	

    @Query("SELECT a FROM Attendance a WHERE a.teacher.id = :teacherId AND FUNCTION('MONTH', a.checkIn) = :month AND FUNCTION('YEAR', a.checkIn) = :year")
    List<Attendance> findByTeacherIdAndMonthAndYear(@Param("teacherId") Long teacherId, 
                                                    @Param("month") String month, 
                                                    @Param("year") String year);
}