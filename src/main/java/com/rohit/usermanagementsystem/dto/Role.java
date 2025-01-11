package com.rohit.usermanagementsystem.dto;

import com.rohit.usermanagementsystem.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString(exclude = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long id;
    private String name;
    private List<User> users;

}
