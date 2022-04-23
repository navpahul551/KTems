package com.navpahul.KTems.services;

import java.util.Optional;

import com.navpahul.KTems.entities.Role;
import com.navpahul.KTems.entities.Rolename;
import com.navpahul.KTems.repositories.RoleRepository;

import org.springframework.stereotype.Service;

@Service
public class RoleService {
    
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public void findOrCreateRole(Rolename rolename){
        Optional<Role> optionalRole = roleRepository.findByName(rolename);

        if(optionalRole.isEmpty()){
            roleRepository.save(new Role(rolename));
        }
    }

}
