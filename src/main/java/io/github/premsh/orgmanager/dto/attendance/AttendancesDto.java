package io.github.premsh.orgmanager.dto.attendance;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Attendance;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Attendances")
public class AttendancesDto {
    @JacksonXmlElementWrapper(localName = "Attendance", useWrapping = false)
    private final List<AttendanceDto> attendances;

    public AttendancesDto(List<Attendance> attendances) {
        this.attendances = attendances.stream().map(AttendanceDto::new).collect(Collectors.toList());
    }
}
