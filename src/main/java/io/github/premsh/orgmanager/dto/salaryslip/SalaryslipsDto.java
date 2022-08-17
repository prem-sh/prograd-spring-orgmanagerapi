package io.github.premsh.orgmanager.dto.salaryslip;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.role.RoleDto;
import io.github.premsh.orgmanager.models.Role;
import io.github.premsh.orgmanager.models.Salaryslip;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Salaryslips")
public class SalaryslipsDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Salaryslip")
    private final List<SalaryslipDto> salaryslips;

    public SalaryslipsDto(List<Salaryslip> roles) {
        this.salaryslips = roles.stream().map(SalaryslipDto::new).collect(Collectors.toList());
    }
}
