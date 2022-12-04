package com.rosivanyshyn.db.dao.entity;

import lombok.*;

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
    private Long id;
    private String guestsNumber;
    private String roomsNumber;
    private String apartmentClass;

    private Date checkInDate;
    private Date checkOutDate;
    //Foreign keys
    Account account;
    ResponseToOrder responseToOrder = null;
}
