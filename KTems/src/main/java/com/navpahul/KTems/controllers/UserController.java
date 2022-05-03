package com.navpahul.KTems.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.navpahul.KTems.data.ErrorResponse;
import com.navpahul.KTems.data.LoginDetails;
import com.navpahul.KTems.data.RegistrationDetails;
import com.navpahul.KTems.exceptions.UserNotFoundException;
import com.navpahul.KTems.services.OrderService;
import com.navpahul.KTems.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;
    private OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDetails loginDetails) {
        try {
            return ResponseEntity.ok(userService.loginUser(loginDetails));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDetails registrationDetails) {
        try {
            System.out.println("registration details: " + registrationDetails.getEmail());
            System.out.println("registration details: " + registrationDetails.getFirstName());
            System.out.println("registration details: " + registrationDetails.getLastName());
            System.out.println("registration details: " + registrationDetails.getUsername());
            System.out.println("registration details: " + registrationDetails.getPassword());
            return ResponseEntity.ok(userService.registerUser(registrationDetails));
        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}/orders")
    public ResponseEntity<?> getAllOrders(@PathVariable(name = "id") Long userId) {
        try {
            return ResponseEntity.ok(orderService.getAllOrders(userId));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing your request!!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{usernameOrEmail}")
    public ResponseEntity<?> getUserDetails(@PathVariable(name = "usernameOrEmail") String usernameOrEmail) {
        try {
            return ResponseEntity.ok(userService.getUserDetails(usernameOrEmail));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}/cart")
    public ResponseEntity<?> getUserCart(@PathVariable(name = "id") Long userId) {
        try {
            return ResponseEntity.ok(userService.getUserCart(userId));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = { "/logout" }, method = RequestMethod.POST)
    public ResponseEntity<?> logoutDo(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            SecurityContextHolder.clearContext();
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            return ResponseEntity.ok("logout successful!!!");
        } catch (Exception e) {
            return new ResponseEntity<>("logout failed!!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
