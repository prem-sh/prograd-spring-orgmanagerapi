package io.github.premsh.orgmanager.dto.employee;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Employee;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Employees")
public class EmployeesDto {
    @JacksonXmlElementWrapper(localName = "Employee", useWrapping = false)
    private final List<EmployeeDto> employees;
    public EmployeesDto(List<Employee> employees) {
        this.employees = employees.stream().map(EmployeeDto::new).collect(Collectors.toList());
    }
}
