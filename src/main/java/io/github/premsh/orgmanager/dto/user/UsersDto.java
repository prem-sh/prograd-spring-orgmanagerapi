package io.github.premsh.orgmanager.dto.user;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.User;

import java.util.List;
import java.util.stream.Collectors;

@JacksonXmlRootElement(localName = "Users")
public class UsersDto {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "User")
    private List<UserDto> user;

    public UsersDto(List<User> users) {
        this.user = (List<UserDto>) users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public List<UserDto> getUser() {
        return user;
    }
}
