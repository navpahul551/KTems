package com.navpahul.KTems.services;

import java.util.Arrays;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;

import com.navpahul.KTems.data.CustomUserDetails;
import com.navpahul.KTems.data.JwtTokenDetails;
import com.navpahul.KTems.data.LoginDetails;
import com.navpahul.KTems.data.RegistrationDetails;
import com.navpahul.KTems.entities.Cart;
import com.navpahul.KTems.entities.Role;
import com.navpahul.KTems.entities.Rolename;
import com.navpahul.KTems.entities.User;
import com.navpahul.KTems.exceptions.CartNotFoundException;
import com.navpahul.KTems.exceptions.UserNotFoundException;
import com.navpahul.KTems.repositories.CartRepository;
import com.navpahul.KTems.repositories.RoleRepository;
import com.navpahul.KTems.repositories.UserRepository;
import com.navpahul.KTems.security.JwtTokenProvider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private CartRepository cartRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, CartRepository cartRepository,
            BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleRepository = roleRepository;
    }

    public JwtTokenDetails registerUser(RegistrationDetails registrationDetails) throws Exception {
        var encryptedPassword = passwordEncoder.encode(registrationDetails.getPassword());

        Role userRole = roleRepository.findByName(Rolename.ROLE_USER).orElseThrow(RoleNotFoundException::new);

        User newUser = new User(registrationDetails.getFirstName(),
                registrationDetails.getLastName(),
                registrationDetails.getUsername(),
                registrationDetails.getEmail(),
                encryptedPassword, Arrays.asList(userRole));

        newUser = userRepository.save(newUser);

        Cart newCart = new Cart(newUser);
        cartRepository.save(newCart);

        System.out.println("User registered successfully...");

        return jwtTokenProvider.generateJwtToken(authenticateUser(new CustomUserDetails(newUser)));
    }

    public JwtTokenDetails loginUser(LoginDetails loginDetails) throws Exception {
        User user = userRepository
                .findByUsernameOrEmail(loginDetails.getUsernameOrEmail(), loginDetails.getUsernameOrEmail())
                .orElseThrow(UserNotFoundException::new);

        // validate password
        if (!passwordEncoder.matches(loginDetails.getPassword(), user.getPassword())) {
            System.out.println("Invalid password. password: " + loginDetails.getPassword());
            throw new Exception("Invalid password!!!");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);

        if (!userDetails.isEnabled()) {
            throw new Exception("User account is disabled!");
        }

        // return jwt token
        return jwtTokenProvider.generateJwtToken(authenticateUser(userDetails));
    }

    public User getUserDetails(String usernameOrEmail) throws UserNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(UserNotFoundException::new);

        return user;
    }

    public Authentication authenticateUser(CustomUserDetails userDetails) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    @Transactional
    public User getUserById(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        var roles = user.getRoles();

        System.out.println("User Role: " + roles.get(0));

        user.setRoles(roles);

        return user;
    }

    @Transactional
    public void createAdminIfNotPresent() throws RoleNotFoundException {
        Role adminRole = roleRepository.findByName(Rolename.ROLE_ADMIN).orElseThrow(RoleNotFoundException::new);
        Iterable<User> allUsers = userRepository.findAll();

        boolean isAdminPresent = false;
        for (var user : allUsers) {
            if (user.getRoles().get(0).getName() == Rolename.ROLE_ADMIN) {
                isAdminPresent = true;
            }
        }

        if (!isAdminPresent) {
            System.out.println("Creating new admin...");
            User admin = new User("navpreet", "kataria", "admin", "navpahul552@gmail.com",
                    passwordEncoder.encode("11111111"), Arrays.asList(adminRole));
            userRepository.save(admin);
            System.out.println("Admin created successfully");
        }
    }

    public Cart getUserCart(Long userId) throws CartNotFoundException{
        return cartRepository.findByUserId(userId).orElseThrow(CartNotFoundException::new);
    }
}
