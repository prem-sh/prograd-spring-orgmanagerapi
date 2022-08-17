package io.github.premsh.orgmanager.controllers;

import io.github.premsh.orgmanager.dto.attendance.AttendanceDto;
import io.github.premsh.orgmanager.dto.attendance.AttendancesDto;
import io.github.premsh.orgmanager.dto.attendance.CreateAttendanceDto;
import io.github.premsh.orgmanager.dto.attendance.UpdateAttendanceDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("org/{orgId}/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDto> getAttendanceById(@PathVariable Long orgId, @PathVariable Long id) {
        return attendanceService.getAttendanceById(orgId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<AttendancesDto> getAllAttendances(@PathVariable Long orgId) {
        return attendanceService.getAllAttendances(orgId);
    }

    @GetMapping("/filter/{empId}")
    public ResponseEntity<AttendancesDto> getAllAttendanceByEmployee(@PathVariable Long orgId, @PathVariable Long empId) {
        return attendanceService.getAllAttendanceByEmployee(orgId, empId);
    }

    @PostMapping
    public ResponseEntity<CreatedDto> createAttendance(@PathVariable Long orgId, @Valid @RequestBody CreateAttendanceDto dto) {
        return attendanceService.createAttendance(orgId, dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedDto> updateAttendance(@PathVariable Long orgId, @Valid @RequestBody UpdateAttendanceDto dto, @PathVariable Long id) {
        return attendanceService.updateAttendance(orgId, dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedDto> deleteAttendance(@PathVariable Long orgId, @PathVariable Long id) {
        return attendanceService.deleteAttendance(orgId, id);
    }
}
