package com.ecommerce.ecommerce_backend.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DailyRegistrationDTO {
    private LocalDateTime date;
    private Long count;

    public DailyRegistrationDTO(LocalDateTime dateTime, Long count) {
        this.date = dateTime;
        this.count = count;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getCount() {
        return count;
    }
}
