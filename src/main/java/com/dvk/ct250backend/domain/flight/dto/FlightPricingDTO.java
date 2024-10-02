package com.dvk.ct250backend.domain.flight.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightPricingDTO {

    Integer flightPricingId;

    @NotBlank(message = "Ticket price is required")
    Double ticketPrice;

    @NotBlank(message = "Seat class name is required")
    String seatClassName;

    @NotBlank(message = "Valid from is required")
    LocalDate validFrom;

    @NotBlank(message = "Valid to is required")
    LocalDate validTo;
}
