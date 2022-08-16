package io.github.premsh.orgmanager.dto.department;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Department;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "Departments")
@Getter
public class DepartmentsDto {
    @JacksonXmlElementWrapper(localName = "Department", useWrapping = false)
    private final List<DepartmentDto> department;
    public DepartmentsDto(List<Department> departments) {
        this.department = departments.stream().map(DepartmentDto::new).collect(Collectors.toList());
    }
}
