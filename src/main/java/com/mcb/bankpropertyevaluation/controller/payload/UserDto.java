package com.mcb.bankpropertyevaluation.controller.payload;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Set<RoleDto> roles;
    private String buisnessUnit;
    private String contactNumber;
}
