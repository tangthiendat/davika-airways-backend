package com.dvk.ct250backend.domain.booking.entity;

import com.dvk.ct250backend.domain.common.entity.BaseEntity;
import com.dvk.ct250backend.domain.flight.entity.Flight;
import com.dvk.ct250backend.domain.flight.entity.TicketClass;
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
@Table(name = "booking_flight")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingFlight extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_flight_id_seq")
    @SequenceGenerator(name = "booking_flight_id_seq", sequenceName = "booking_flights_seq", allocationSize = 1)
    Integer bookingFlightId;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    Flight flight;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    Booking booking;

    @ManyToOne
    @JoinColumn(name = "ticket_class_id")
    TicketClass ticketClass;

    @OneToMany(mappedBy = "bookingFlight", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    List<BookingPassenger> bookingPassengers;

    @Version
    Integer version;
}
