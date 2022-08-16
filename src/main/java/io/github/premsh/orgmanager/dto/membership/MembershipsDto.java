package io.github.premsh.orgmanager.dto.membership;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Membership;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "Memberships")
public class MembershipsDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Member")
    private List<MembershipDto> members = new ArrayList<>();

    public MembershipsDto(List<Membership> members) {
        this.members = members.stream().map(MembershipDto::new).collect(Collectors.toList());
    }
}
