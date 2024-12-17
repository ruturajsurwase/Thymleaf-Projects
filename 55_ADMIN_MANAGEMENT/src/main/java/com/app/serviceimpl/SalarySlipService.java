package com.app.serviceimpl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.app.entity.Attendance;
import com.app.entity.Teacher;
import com.app.service.AttendanceService;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.Color;

@Service
public class SalarySlipService {

    private final AttendanceService attendanceService;

    public SalarySlipService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void generateSalarySlip(Teacher teacher, String month, String year, OutputStream outputStream)
            throws IOException {

        int monthNumber = Integer.parseInt(month);
        int yearNumber = Integer.parseInt(year);

        Month monthEnum = Month.of(monthNumber);
        String monthName = monthEnum.name();
        String formattedMonthName = monthName.substring(0, 1) + monthName.substring(1).toLowerCase();

        long totalDaysInMonth = calculateWorkingDaysInMonth(yearNumber, monthNumber);

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Salary Slip_" + formattedMonthName);

        // Create styles
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        XSSFCellStyle dataStyle = workbook.createCellStyle();

        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);

        titleStyle.setFillForegroundColor(new XSSFColor(new Color(169, 208, 142), null));
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        headerStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 0), null));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        dataStyle.setFillForegroundColor(new XSSFColor(new Color(220, 230, 241), null));
        dataStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Add Company Name row
        Row companyRow = sheet.createRow(0);
        Cell companyCell = companyRow.createCell(0);
        companyCell.setCellValue("SWAPRA Technologies");
        companyCell.setCellStyle(titleStyle);

        // Add Company Address row below the Company Name
        Row companyAddressRow = sheet.createRow(1);
        Cell companyAddressCell = companyAddressRow.createCell(0);
        companyAddressCell.setCellValue("Neeta Tower Kasarwadi Pune- 411012");
        companyAddressCell.setCellStyle(titleStyle);

        // Title row
        Row titleRow = sheet.createRow(2);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Salary Slip for " + teacher.getFirstName() + " " + teacher.getLastName());
        titleCell.setCellStyle(titleStyle);

        // Teacher details row
        Row teacherDetailsRow = sheet.createRow(3);
        teacherDetailsRow.createCell(0).setCellValue("Teacher ID: " + teacher.getId());
        teacherDetailsRow.createCell(1).setCellValue("Designation: " + teacher.getRole());
        teacherDetailsRow.createCell(2).setCellValue("Bank A/C No: ");
        teacherDetailsRow.createCell(3).setCellValue("IFSC Code:  ");
        teacherDetailsRow.createCell(4).setCellValue("Month:  "+formattedMonthName);

        // Fetch attendance for the given month and year
        List<Attendance> attendances = attendanceService.getAttendancesByMonth(teacher.getId(), month, year);
        long totalPresentDays = calculateTotalWorkingDays(attendances);
        long absentDays = totalDaysInMonth - totalPresentDays;                          // Calculate absent days

        // Salary calculation logic
        double salaryPerMonth = teacher.getSalaryPerMonth();
        double dailyRate = salaryPerMonth / totalDaysInMonth;  
        double totalSalary = dailyRate * totalPresentDays;

        // Add "Total Working Days in Month", "Total Present Days", and "Absent Days" row
        Row attendanceDetailsRow = sheet.createRow(4);
        attendanceDetailsRow.createCell(0).setCellValue("Total Working Days in Month:");
        attendanceDetailsRow.createCell(1).setCellValue(totalDaysInMonth);
        attendanceDetailsRow.createCell(2).setCellValue("Total Present Days:");
        attendanceDetailsRow.createCell(3).setCellValue(totalPresentDays);
        attendanceDetailsRow.createCell(4).setCellValue("Absent Days:");
        attendanceDetailsRow.createCell(5).setCellValue(absentDays);
        attendanceDetailsRow.createCell(6).setCellValue("Salary Per Day:");
        attendanceDetailsRow.createCell(7).setCellValue(dailyRate);

        // Salary components
        double basicPay = teacher.getBasicPay();
        double hra = teacher.getHra();
        double conveyance = teacher.getConveyanceAllowance();
        double educationAllowance = teacher.getEducationAllowance();
        double washingAllowance = teacher.getWashingAllowance();
        double medicalAllowance = teacher.getMedicalAllowance();
        double dearnessAllowance = teacher.getDearnessAllowance();
        double otherAllowances = teacher.getOtherAllowances();

        double monthlyGross = basicPay + hra + conveyance + educationAllowance + washingAllowance + medicalAllowance
                             + dearnessAllowance + otherAllowances;

        // Deductions
        double deductions = teacher.getDeductions();  							//  Professional Tax
        double netSalary = totalSalary - deductions;                            // Adjusted net salary based on attendance

        
        
        // Salary details row
        Row salaryDetailsRow = sheet.createRow(5);
        salaryDetailsRow.createCell(0).setCellValue("Basic Pay:");
        salaryDetailsRow.createCell(1).setCellValue(basicPay);

        // HRA row
        Row hraRow = sheet.createRow(6);
        hraRow.createCell(0).setCellValue("HRA:");
        hraRow.createCell(1).setCellValue(hra);

        // Conveyance row
        Row conveyanceRow = sheet.createRow(7);
        conveyanceRow.createCell(0).setCellValue("Conveyance:");
        conveyanceRow.createCell(1).setCellValue(conveyance);

        // Education Allowance row
        Row educationAllowancerow = sheet.createRow(8);
        educationAllowancerow.createCell(0).setCellValue("Education Allowance:");
        educationAllowancerow.createCell(1).setCellValue(educationAllowance);

        // Washing Allowance row
        Row washingAllowancerow = sheet.createRow(9);
        washingAllowancerow.createCell(0).setCellValue("Washing Allowance:");
        washingAllowancerow.createCell(1).setCellValue(washingAllowance);

        // Medical Allowance row
        Row medicalAllowancerow = sheet.createRow(10);
        medicalAllowancerow.createCell(0).setCellValue("Medical Allowance:");
        medicalAllowancerow.createCell(1).setCellValue(medicalAllowance);

        // Dearness Allowance row
        Row dearnessAllowancerow = sheet.createRow(11);
        dearnessAllowancerow.createCell(0).setCellValue("Dearness Allowance:");
        dearnessAllowancerow.createCell(1).setCellValue(dearnessAllowance);

        // Other Allowances row
        Row otherAllowancesRow = sheet.createRow(12);
        otherAllowancesRow.createCell(0).setCellValue("Other Allowances:");
        otherAllowancesRow.createCell(1).setCellValue(otherAllowances);

        // Monthly Gross Salary row
        Row monthlyGrossrow = sheet.createRow(13);
        monthlyGrossrow.createCell(0).setCellValue("Monthly Gross Salary:");
        monthlyGrossrow.createCell(1).setCellValue(monthlyGross);

        // Deductions row
        Row deductionDetailsRow = sheet.createRow(14);
        deductionDetailsRow.createCell(0).setCellValue("Deductions");

        // Professional Tax row
        Row professionalTaxRow = sheet.createRow(15);
        professionalTaxRow.createCell(0).setCellValue("Professional Tax:");
        professionalTaxRow.createCell(1).setCellValue(deductions);
        
        //AbsentDaysSalary
        Row AbsentDaysSalary = sheet.createRow(16);
        AbsentDaysSalary.createCell(0).setCellValue("Absent Days Salary:");
        AbsentDaysSalary.createCell(1).setCellValue(absentDays * dailyRate);
        
        
        

        // Net Salary row
        Row netSalaryRow = sheet.createRow(17);
        netSalaryRow.createCell(0).setCellValue("Net Salary:");
        netSalaryRow.createCell(1).setCellValue(netSalary);

        // Adjust column sizes
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }

    // Helper methods to calculate working days and total present days
    public long calculateWorkingDaysInMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return IntStream.rangeClosed(1, yearMonth.lengthOfMonth()).mapToObj(day -> LocalDate.of(year, month, day))
                .filter(date -> !isWeekend(date)).count();
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public long calculateTotalWorkingDays(List<Attendance> attendances) {
        return attendances.size();
    }
} 

