package io.github.premsh.orgmanager.dto.user;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.models.Role;
import io.github.premsh.orgmanager.models.User;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@JacksonXmlRootElement(localName = "User")
public class UserDto {
    @Getter
    private static class Metadata {
        private final boolean isEnabled;
        private final Date createdAt;
        private final Date updatedAt;

        public Metadata(boolean isEnabled, Date createdAt, Date updatedAt) {
            this.isEnabled = isEnabled;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
    @JacksonXmlProperty(localName = "userid", isAttribute = true)
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phone;
    private final String email;
    private final Metadata metadata;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.metadata = new Metadata(user.isEnabled(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
