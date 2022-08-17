package io.github.premsh.orgmanager.dto.attendance;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@JacksonXmlRootElement(localName = "Attendance")
public class UpdateAttendanceDto {
    private Date loginTime;
    private Date logoutTime;
}