package com.rosivanyshyn.dao.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
public class Booking implements Serializable {
    private int id = 0;
    private String guestsNumber;

    private Date checkInDate;
    private Date checkOutDate;
    private Timestamp reservationData;
    private boolean isPaidForReservation = false;
    //зовнішні ключі
    Account account;
    Apartment apartment;


}
