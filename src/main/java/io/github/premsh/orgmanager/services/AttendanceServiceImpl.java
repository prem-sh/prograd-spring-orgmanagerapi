package io.github.premsh.orgmanager.services;

import io.github.premsh.orgmanager.dto.attendance.AttendanceDto;
import io.github.premsh.orgmanager.dto.attendance.AttendancesDto;
import io.github.premsh.orgmanager.dto.attendance.CreateAttendanceDto;
import io.github.premsh.orgmanager.dto.attendance.UpdateAttendanceDto;
import io.github.premsh.orgmanager.dto.response.CreatedDto;
import io.github.premsh.orgmanager.dto.response.DeletedDto;
import io.github.premsh.orgmanager.dto.response.UpdatedDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import io.github.premsh.orgmanager.models.Attendance;
import io.github.premsh.orgmanager.repository.AttendanceRepo;
import io.github.premsh.orgmanager.repository.EmployeeRepo;
import io.github.premsh.orgmanager.repository.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AttendanceServiceImpl implements AttendanceService{
    @Autowired private AttendanceRepo attendanceRepo;
    @Autowired private EmployeeRepo employeeRepo;
    @Autowired private OrganizationRepo organizationRepo;
    @Autowired private PrincipalService principalService;

    @Override
    public ResponseEntity<AttendanceDto> getAttendanceById(Long orgId, Long id) {
        return new ResponseEntity<>(
                new AttendanceDto(attendanceRepo.findById(orgId, id).orElseThrow(()-> new EntityNotFoundException("Attendance not found"))), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<AttendancesDto> getAllAttendances(Long orgId) {
        return new ResponseEntity<>(
                new AttendancesDto(attendanceRepo.findAll(orgId)), HttpStatus.OK
        );
    }


    public ResponseEntity<AttendancesDto> getAllAttendanceByEmployee(Long orgId, Long empId) {
        return new ResponseEntity<>(
                new AttendancesDto(attendanceRepo.findByEmployeeId(orgId, empId)), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CreatedDto> createAttendance(Long orgId, CreateAttendanceDto dto) {
        Attendance attendance = new Attendance();
        attendance.setCreatedBy(principalService.getUser());
        attendance.setUpdatedBy(principalService.getUser());
        attendance.setEmployee(employeeRepo.findById(orgId, dto.getEmpId()).orElseThrow(()->new EntityNotFoundException("Employee not found")));
        attendance.setLoginTime(dto.getLoginTime());
        attendance.setLogoutTime(dto.getLogoutTime());
        Attendance a = attendanceRepo.save(attendance);
        return new ResponseEntity<>(new CreatedDto("Attendance registered successfully",String.valueOf(a.getId())), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UpdatedDto> updateAttendance(Long orgId, UpdateAttendanceDto dto, Long id) {
        Attendance attendance = attendanceRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Attendance Not found"));
        attendance.setUpdatedBy(principalService.getUser());
        if(dto.getLoginTime() != null) attendance.setLoginTime(dto.getLoginTime());
        if(dto.getLogoutTime() != null) attendance.setLogoutTime(dto.getLogoutTime());
        attendanceRepo.save(attendance);
        return new ResponseEntity<>(new UpdatedDto("Attendance updated successfully",String.valueOf(attendance.getId())), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<DeletedDto> deleteAttendance(Long orgId, Long id) {
        if(!attendanceRepo.existsById(orgId, id)) throw new EntityNotFoundException("Attendance not found");
        Attendance attendance = attendanceRepo.findById(orgId, id).orElseThrow(()->new EntityNotFoundException("Attendance Not found"));
        attendance.setDeletedBy(principalService.getUser());
        attendance.setDeletedAt(new Date());
        attendance.setIsDeleted(true);
        attendanceRepo.save(attendance);
        return new ResponseEntity<>(new DeletedDto("Attendance deleted successfully",String.valueOf(attendance.getId())), HttpStatus.ACCEPTED);
    }
}
