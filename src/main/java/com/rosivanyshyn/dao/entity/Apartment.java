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
public class Apartment implements Serializable {
    private int id = 0;
    private String maxGuestsNumber;
    private String roomsNumber;
    private String apartmentClass;
    private Long price;
    //відображає статус апартаментів (false - недоступні/ true - доступні)
    private boolean state = true;
}
