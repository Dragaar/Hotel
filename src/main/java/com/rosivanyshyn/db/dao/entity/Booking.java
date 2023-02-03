package com.rosivanyshyn.db.dao.entity;

import lombok.*;

import java.io.Serial;
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

    @Serial
    private static final long serialVersionUID = 385954903468257504L;

    private Long id;
    private String guestsNumber;

    private Date checkInDate;
    private Date checkOutDate;
    //Auto-Generated
    private Timestamp reservationData;
    private Boolean isPaidForReservation = false;
    //Foreign keys
    Account account;
    Apartment apartment;


}
