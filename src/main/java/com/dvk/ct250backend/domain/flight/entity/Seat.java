package com.dvk.ct250backend.domain.flight.entity;

import com.dvk.ct250backend.domain.booking.entity.BookingPassenger;
import com.dvk.ct250backend.domain.flight.enums.TicketClassEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seats")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_id_seq")
    @SequenceGenerator(name = "seat_id_seq", sequenceName = "seat_seq", allocationSize = 1)
    Integer seatId;

    @Enumerated(EnumType.STRING)
    TicketClassEnum ticketClass;
    String seatCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seat", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    List<SeatAvailability> seatAvailability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    Model model;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seat", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    List<BookingPassenger> bookingPassengers;
}
