package com.rosivanyshyn.dao.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
public class Account implements Serializable {
    private int id = 0;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    //відображає статус аккаунту (false - заблокований/ true - активний)
    private boolean state = true;

}
