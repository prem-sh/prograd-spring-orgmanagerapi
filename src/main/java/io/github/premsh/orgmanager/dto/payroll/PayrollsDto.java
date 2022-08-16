package io.github.premsh.orgmanager.dto.payroll;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Department;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "Departments")
@Getter
public class PayrollsDto {
    @JacksonXmlElementWrapper(localName = "Department", useWrapping = false)
    private final List<PayrollDto> department;
    public PayrollsDto(List<Department> departments) {
        this.department = departments.stream().map(PayrollDto::new).collect(Collectors.toList());
    }
}
