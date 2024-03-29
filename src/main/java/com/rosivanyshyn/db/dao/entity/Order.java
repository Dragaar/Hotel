package com.rosivanyshyn.db.dao.entity;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString()
@EqualsAndHashCode()
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1444499296234806694L;

    private Long id;
    private Integer guestsNumber;
    private String roomsNumber;
    private String apartmentClass;
    private Long price;
    private String description;

    private Date checkInDate;
    private Date checkOutDate;
    //Foreign keys
    Account account;
    ResponseToOrder responseToOrder = null;
}
