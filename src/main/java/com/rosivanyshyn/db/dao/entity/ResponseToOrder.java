package com.rosivanyshyn.db.dao.entity;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode()
public class ResponseToOrder implements Serializable {
    private Long id;
    private String description;
}
