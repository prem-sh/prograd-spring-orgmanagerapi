package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.attendance.AttendanceDto;
import io.github.premsh.orgmanager.dto.attendance.AttendancesDto;
import io.github.premsh.orgmanager.dto.attendance.CreateAttendanceDto;
import io.github.premsh.orgmanager.dto.attendance.UpdateAttendanceDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import org.springframework.http.ResponseEntity;

public interface AttendanceService {
    ResponseEntity<AttendanceDto> getAttendanceById(Long orgId, Long id);
    ResponseEntity<AttendancesDto> getAllAttendances(Long orgId);
    ResponseEntity<AttendancesDto> getAllAttendanceByEmployee(Long orgId, Long empId);
    ResponseEntity<CreatedDto> createAttendance(Long orgId, CreateAttendanceDto dto);
    ResponseEntity<UpdatedDto> updateAttendance(Long orgId, UpdateAttendanceDto dto, Long id);
    ResponseEntity<DeletedDto> deleteAttendance(Long orgId, Long id);
}
