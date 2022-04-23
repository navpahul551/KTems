package com.navpahul.KTems;

import java.util.EnumSet;

import com.navpahul.KTems.entities.Rolename;
import com.navpahul.KTems.services.RoleService;
import com.navpahul.KTems.services.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements CommandLineRunner{
    
    private RoleService roleService;
    private UserService userService;

    public ApplicationStartupRunner(RoleService roleService, UserService userService){
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        EnumSet.allOf(Rolename.class).forEach(roleName -> roleService.findOrCreateRole(roleName));
        
        userService.createAdminIfNotPresent();
    }
}