package com.app.controller;

import java.io.IOException;
import java.util.List;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.entity.Attendance;
import com.app.entity.Teacher;
import com.app.service.AttendanceService;
import com.app.service.TeacherService;
import com.app.serviceimpl.SalarySlipService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private SalarySlipService salarySlipService;

	@GetMapping("/showNewTeacherForm")
	public String showNewTeacherForm(Model model) {
		Teacher teacher = new Teacher();
		model.addAttribute("teacher", teacher);
		return "new_teacher";
	}

	@PostMapping("/saveTeacher")
	public String saveTeacher(@ModelAttribute("teacher") Teacher teacher, RedirectAttributes redirectAttributes) 
	{
		
		teacherService.saveTeacher(teacher);
		redirectAttributes.addFlashAttribute("successMessage", "Teacher added successfully!");
		return "redirect:/showTeacherList";
	}

	@GetMapping("/showTeacherList")
	public String showEmployeeList(Model model) {
		model.addAttribute("listTeachers", teacherService.getAllTeachers());
		return "teacherlist";
	}

	@GetMapping("/showFormForUpdateTeacher/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
		Teacher teacher = teacherService.getTeacherById(id);
		model.addAttribute("teacher", teacher);
		return "update_teacher";
	}

	
	
	//update teacher
	@PostMapping("/updateTeacher")
	public String updateTeacher(@ModelAttribute("teacher") Teacher teacher, RedirectAttributes redirectAttributes) 
	{
		teacherService.updateTeacher(teacher);
		redirectAttributes.addFlashAttribute("successMessage", "Teacher added successfully!");
		return "redirect:/showTeacherList";
	}
	
	
	
	@GetMapping("/deleteTeacher/{id}")
	public String deleteEmployee(@PathVariable(value = "id") long id) {
		teacherService.deleteTeacherById(id);
		return "redirect:/showTeacherList";
	}
	
	
	
	

	@GetMapping("/viewTeacher/{id}")
	public String viewEmployee(@PathVariable(value = "id") long id, Model model) {
		Teacher teacher = teacherService.getTeacherById(id);
		model.addAttribute("teacher", teacher);
		return "view_teacher";
	}

	@GetMapping("/showTeacherListforAttendance")
	public String showEmployeeListForAttendance(Model model) {
		model.addAttribute("listTeachers", teacherService.getAllTeachers());
		return "ListTeacher";
	}

	// View attendance records
	@GetMapping("/teacher/{id}")
	public String viewAttendanceByTeacher(@PathVariable Long id, Model model) 
	{
		Teacher teacher = teacherService.getTeacherById(id);

		model.addAttribute("teacher", teacher);

		return "attendancelist";
	}

	@GetMapping("/filterAttendanceByMonth")
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

		return "attendancelist"; // Ensure this is the correct view name
	}

	 @GetMapping("/viewSalarySlip")
	public String viewSalarySlip(@RequestParam Long teacherId, @RequestParam String month, @RequestParam String year,
			Model model) 
	{
		
		
		
		Teacher teacher = teacherService.getTeacherById(teacherId);
		List<Attendance> attendances = attendanceService.getAttendancesByMonth(teacherId, month, year);

		long totalDaysInMonth = salarySlipService.calculateWorkingDaysInMonth(Integer.parseInt(year),
				Integer.parseInt(month));
		long totalPresentDays = salarySlipService.calculateTotalWorkingDays(attendances);

        long absentDays = totalDaysInMonth - totalPresentDays;      // Calculate absent days
        
        
		
		// Salary calculations
		double salaryPerMonth = teacher.getSalaryPerMonth();
		
		double salaryPerDay = salaryPerMonth / totalDaysInMonth;
		
		double totalSalary = salaryPerDay * totalPresentDays;

	    double 	absentDaysSalary = absentDays * salaryPerDay;
	    
		double netSalary = totalSalary - teacher.getDeductions();

		model.addAttribute("teacher", teacher);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("totalDaysInMonth", totalDaysInMonth);
		model.addAttribute("totalPresentDays", totalPresentDays);
		model.addAttribute("absentDays", absentDays);
		model.addAttribute("salaryPerMonth", salaryPerMonth);
		model.addAttribute("salaryPerDay", salaryPerDay);
		model.addAttribute("BasicPay", teacher.getBasicPay());
		model.addAttribute("HRA", teacher.getHra());
		model.addAttribute("conveyanceAllowance", teacher.getConveyanceAllowance());
		model.addAttribute("educationAllowance", teacher.getEducationAllowance());
		model.addAttribute("washingAllowance", teacher.getWashingAllowance());
		model.addAttribute("medicalAllowance", teacher.getMedicalAllowance());
		model.addAttribute("dearnessAllowance", teacher.getDearnessAllowance());
		model.addAttribute("otherAllowances", teacher.getOtherAllowances());
		model.addAttribute("deductions", teacher.getDeductions());
		model.addAttribute("absentDaysSalary", absentDaysSalary);
		model.addAttribute("grossSalary", salaryPerMonth);
		

		model.addAttribute("totalSalary", netSalary);

		return "salarySlipView";
	}	 


	
	@PostMapping("/generateSalarySlip")
	public void generateSalarySlip(@RequestParam Long teacherId, @RequestParam String month, @RequestParam String year,
			HttpServletResponse response) throws IOException
	{
		Teacher teacher = teacherService.getTeacherById(teacherId);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		int monthNumber = Integer.parseInt(month);              // Convert month string to an integer
		Month monthEnum = Month.of(monthNumber);               // Convert the month number to a Month enum
		String monthName = monthEnum.name();                  // Get the month name
		String formattedMonthName = monthName.substring(0, 1) + monthName.substring(1).toLowerCase();   // Convert the month name to a more user-friendly format (capitalize only the first letter)

		response.setHeader("Content-Disposition", "attachment; filename=salary_slip_" + teacher.getFirstName() + "_"
				+ teacher.getLastName() + "_" + formattedMonthName + ".xlsx");

		salarySlipService.generateSalarySlip(teacher, month, year, response.getOutputStream());
	
	}
	
	

	
	
}