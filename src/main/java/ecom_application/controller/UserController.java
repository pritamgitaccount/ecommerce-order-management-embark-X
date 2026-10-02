package ecom_application.controller;

import ecom_application.dto.UserRequest;
import ecom_application.dto.UserResponse;
import ecom_application.entity.User;
import ecom_application.role.UserRole;
import ecom_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.fetchUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
//        if (userRequest.getRole() == null) {
//            userRequest.setRole(UserRole.CUSTOMER);
//        }
//        System.out.println("Role before save: " + userRequest.getRole());
        userService.createUser(userRequest);
        return new ResponseEntity<>(
                "User created successfully",
                HttpStatus.CREATED);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody UserRequest updatedUserRequest) {
        boolean isUpdated = userService.updateUser(id, updatedUserRequest);
        if (isUpdated) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
