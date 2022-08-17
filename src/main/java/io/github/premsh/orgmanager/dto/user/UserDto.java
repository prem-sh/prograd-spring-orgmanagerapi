package io.github.premsh.orgmanager.dto.user;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.premsh.orgmanager.dto.metadata.Metadata;
import io.github.premsh.orgmanager.models.User;
import lombok.Getter;



@Getter
@JacksonXmlRootElement(localName = "User")
public class UserDto {

    @JacksonXmlProperty(localName = "userid", isAttribute = true)
    private final long id;
    @JacksonXmlProperty(localName = "enabled", isAttribute = true)
    private final Boolean enabled;
    private final Boolean deleted;
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
        this.enabled = user.getIsEnabled();
        this.deleted = user.getIsDeleted();
        this.metadata = new Metadata(
                user.getCreatedBy(),
                user.getCreatedAt(),
                user.getUpdatedBy(),
                user.getUpdatedAt(),
                user.getDeletedBy(),
                user.getDeletedAt()
        );
    }
}
