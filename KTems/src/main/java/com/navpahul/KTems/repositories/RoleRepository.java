package com.navpahul.KTems.repositories;

import java.util.Optional;

import com.navpahul.KTems.entities.Role;
import com.navpahul.KTems.entities.Rolename;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    public Optional<Role> findByName(Rolename name);
}
