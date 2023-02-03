package com.rosivanyshyn.db.dao.entity;

import lombok.*;

import java.io.Serial;
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

    @Serial
    private static final long serialVersionUID = 1327050385146086057L;

    private Long id;
    private String description;
}
