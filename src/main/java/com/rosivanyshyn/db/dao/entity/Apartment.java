package com.rosivanyshyn.db.dao.entity;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString()
@EqualsAndHashCode()
public class Apartment implements Serializable {

    @Serial
    private static final long serialVersionUID = -2943941589166694458L;

    private Long id;
    private String title;
    private String description;
    private String imageURL;
    private String address;
    private String maxGuestsNumber;
    private String roomsNumber;
    private String apartmentClass;
    private Long price;
    //indicate state of apartment (false - not available/ true - available)
    private Boolean state = true;
}
