package io.github.premsh.orgmanager.dto.payroll;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Payroll;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "Payrolls")
@Getter
public class PayrollsDto {
    @JacksonXmlElementWrapper(localName = "Payroll", useWrapping = false)
    private final List<PayrollDto> payrolls;
    public PayrollsDto(List<Payroll> payrolls) {
        this.payrolls = payrolls.stream().map(PayrollDto::new).collect(Collectors.toList());
    }
}
