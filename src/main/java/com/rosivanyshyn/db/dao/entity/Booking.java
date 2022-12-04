package com.rosivanyshyn.db.dao.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString()
@EqualsAndHashCode(exclude = "reservationData")
public class Booking implements Serializable {
    private Long id;
    private String guestsNumber;

    private Date checkInDate;
    private Date checkOutDate;
    private Timestamp reservationData;
    private Boolean isPaidForReservation = false;
    //Foreign keys
    Account account;
    Apartment apartment;


}
