package org.theonlydodo.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phone;

    private String title;

    private String bio;

    private String imageUrl;

    private boolean enabled;

    private boolean isNotLocked;

    private boolean isUsingMFA;

    private LocalDateTime createdAt;

}
