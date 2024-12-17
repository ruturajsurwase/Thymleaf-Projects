package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.app.entity.Teacher;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
	public Teacher  findByEmail(String email);
}
