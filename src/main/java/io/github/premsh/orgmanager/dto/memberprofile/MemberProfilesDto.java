package io.github.premsh.orgmanager.dto.memberprofile;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.MemberProfile;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JacksonXmlRootElement(localName = "MemberProfiles")
public class MemberProfilesDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MemberProfile")
    private List<MemberProfileDto> members = new ArrayList<>();

    public MemberProfilesDto(List<MemberProfile> members) {
        this.members = members.stream().map(MemberProfileDto::new).collect(Collectors.toList());
    }
}
