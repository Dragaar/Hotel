package com.rosivanyshyn.db.dao.entity;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"id", "password"})
@EqualsAndHashCode(exclude = {"id", "password"})
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 1299478402992228383L;

    private Long id;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //indicate state of the Account (false - blocked/ true - available)
    private Boolean state = true;

}
