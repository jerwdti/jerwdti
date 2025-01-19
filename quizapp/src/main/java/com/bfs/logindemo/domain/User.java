package com.bfs.logindemo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean admin = false;
    private boolean active = true;

}