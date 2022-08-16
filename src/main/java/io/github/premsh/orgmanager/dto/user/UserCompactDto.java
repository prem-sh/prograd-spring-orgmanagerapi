package io.github.premsh.orgmanager.dto.user;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.User;

@JacksonXmlRootElement(localName = "User")
public class UserCompactDto {
    @JacksonXmlProperty(isAttribute = true)
    private final Long id;
    @JacksonXmlProperty(isAttribute = true)
    private final String name;
    @JacksonXmlProperty(isAttribute = true)
    private final String email;
    @JacksonXmlProperty(isAttribute = true)
    private final String phone;

    public UserCompactDto(User user) {
        id = user.getId();
        name = user.getFirstName()+" "+user.getLastName();
        email = user.getEmail();
        phone = user.getPhone();
    }
}
