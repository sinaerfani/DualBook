package com.example.dualbook.controller.api;

import com.example.dualbook.dto.ApiResponse;
import com.example.dualbook.dto.UserDTO;
import com.example.dualbook.dto.UserUpdateDTO;
import com.example.dualbook.entity.User;
import com.example.dualbook.mapper.UserMapper;
import com.example.dualbook.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserApiController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserDTO>> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserUpdateDTO updateDTO) {
        try {
            User updatedUser = userService.updateUser(user.getId(), updateDTO.getFullName());
            return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", userMapper.toDTO(updatedUser)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Update failed: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserDTO>>> searchUsers(@RequestParam String query) {
        try {
            List<User> users = userService.searchUsers(query);
            List<UserDTO> userDTOs = users.stream()
                    .map(userMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success("Users found", userDTOs));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Search failed: " + e.getMessage()));
        }
    }
}