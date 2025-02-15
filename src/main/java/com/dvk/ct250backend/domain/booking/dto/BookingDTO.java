package com.dvk.ct250backend.domain.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    Integer bookingId;

    String bookingCode;

    @NotBlank(message = "Trip type is required")
    String tripType;

    @NotBlank(message = "Booking flights are required")
    List<BookingFlightDTO> bookingFlights;

    @NotBlank(message = "Total price is required")
    BigDecimal totalPrice;

    @NotBlank(message = "Booking status is required")
    String bookingStatus;

    LocalDateTime paymentDeadline;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    CouponDTO coupon;
}
