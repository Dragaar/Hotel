package com.rosivanyshyn.dao.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class ResponseToOrder implements Serializable {
    private int id = 0;
    private String description;
}
