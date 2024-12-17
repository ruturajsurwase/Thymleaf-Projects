package com.app.controller;

import com.app.entity.Attendance;
import com.app.entity.Teacher;
import com.app.service.AttendanceService;
import com.app.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;



@Controller
public class AttendanceController 
{

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private TeacherService teacherService;

    // Show the list of teachers
    @GetMapping("/teacherList")
    public String showTeacherList(Model model) {
        model.addAttribute("listTeachers", teacherService.getAllTeachers());
        return "ListTeacher";
    }

    // Show form to edit teacher
    @GetMapping("/showFormForEditTeacher/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "editTeacher";
    }

    // Save teacher
    @PostMapping("/teachersaved")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher, RedirectAttributes redirectAttributes) {
        teacherService.saveTeacher(teacher);
        redirectAttributes.addFlashAttribute("successMessage", "Teacher added successfully!");
        return "redirect:/teacherList";
    }

    // Delete teacher
    @GetMapping("/teacherDelete/{id}")
    public String deleteTeacher(@PathVariable(value = "id") long id) {
        teacherService.deleteTeacherById(id);
        return "redirect:/teacherList";
    }

    // Show teacher details
    @GetMapping("/teacherShow/{id}")
    public String viewTeacher(@PathVariable(value = "id") long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "showTeacher";
    }

    // Show the attendance page
    @GetMapping("/attendance/{id}")
    public String showAttendancePage(@PathVariable Long id, Model model) 
    {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "attendance";
    }

    
    
 // Check-In button logic
    @PostMapping("/checkin/{id}")
    public String checkIn(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            Attendance attendance = new Attendance();
            attendance.setTeacher(teacher);
            attendance.setCheckIn(LocalDateTime.now());
            attendanceService.checkIn(attendance);
        }
        // Add teacher details to the model and redirect to dashboard
        model.addAttribute("teacher", teacher);
        return "redirect:/teacherdashboard/" + id;
    }

    // Check-Out button logic
    @PostMapping("/checkout/{id}")
    public String checkOut(@PathVariable Long id, Model model) {
        Attendance attendance = attendanceService.getLatestAttendanceByTeacherId(id);
        if (attendance != null && attendance.getCheckOut() == null) {
            attendance.setCheckOut(LocalDateTime.now());
            attendanceService.checkOut(attendance);
        }
        // Add teacher details to the model and redirect to dashboard
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "redirect:/teacherdashboard/" + id;
    }
    
    

    
    // View attendance records
    @GetMapping("/teacher/{id}/attendance")

    public String viewAttendanceByTeacher(@PathVariable Long id, Model model)
    {
    Teacher teacher = teacherService.getTeacherById(id);
       
        model.addAttribute("teacher", teacher);
        
      //model.addAttribute("attendanceList", attendanceList);
    return "viewAttendance";
    
   }
    
    
    @GetMapping("/filterAttendanceByMonthForTeacher")
	public String filterAttendanceByMonth(@RequestParam("month") String month, @RequestParam("year") String year,
			@RequestParam("teacherId") Long teacherId, Model model) 
	{
		// Fetch the teacher entity using the provided teacherId
		Teacher teacher = teacherService.getTeacherById(teacherId);

		// Fetch filtered attendance records
		List<Attendance> filteredAttendances = attendanceService.getAttendancesByMonth(teacherId, month, year);

		// Add attributes to the model for Thymeleaf to use in the view
		model.addAttribute("teacher", teacher);
		model.addAttribute("filteredAttendances", filteredAttendances);

		// Ensure month and year are strings for proper comparison in Thymeleaf
		model.addAttribute("month", String.format("%02d", Integer.parseInt(month)));
		model.addAttribute("year", year);

		return "viewAttendance"; // Ensure this is the correct view name
	}
    

}
