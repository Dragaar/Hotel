package com.rosivanyshyn.db.dao.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString()
@EqualsAndHashCode()
public class Apartment implements Serializable {
    private Long id;
    private String maxGuestsNumber;
    private String roomsNumber;
    private String apartmentClass;
    private Long price;
    //indicate state of apartment (false - not available/ true - available)
    private Boolean state = true;
}
