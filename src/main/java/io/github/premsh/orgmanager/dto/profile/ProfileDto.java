package io.github.premsh.orgmanager.dto.profile;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.memberprofile.MemberProfileDto;
import io.github.premsh.orgmanager.dto.memberprofile.MemberProfilesDto;
import io.github.premsh.orgmanager.dto.user.UserDto;
import io.github.premsh.orgmanager.models.MemberProfile;
import io.github.premsh.orgmanager.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@JacksonXmlRootElement(localName = "Profile")
public class ProfileDto {
    @JacksonXmlProperty(localName = "PersonalDetails")
    private final UserDto personaldetails;
    @JacksonXmlProperty(localName = "Memberships")
    private final MemberProfilesDto memberships;

    public ProfileDto(User personaldetails, List<MemberProfile> memberships) {
        this.personaldetails = new UserDto(personaldetails);
        this.memberships = new MemberProfilesDto(memberships);
    }
}
