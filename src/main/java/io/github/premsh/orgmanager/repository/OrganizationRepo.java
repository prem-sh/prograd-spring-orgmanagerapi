package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {

}
