package com.mimi.spring.cache.Entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userID;

    private String username;

    private String password;

    private Integer age;
}
