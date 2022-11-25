package com.rosivanyshyn.dao.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
public class Order implements Serializable {
    private int id = 0;
    private String guestsNumber;
    private String roomsNumber;
    private String apartmentClass;

    private Date checkInDate;
    private Date checkOutDate;
    //зовнішні ключі
    Account account;
    ResponseToOrder responseToOrder = null;
}
