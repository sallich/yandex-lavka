package ru.yandex.yandexlavka.business.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompleteOrder {
    @JsonProperty("courier_id")
    @NotNull
    private long courierId;
    @JsonProperty("order_id")
    @NotNull
    private long orderId;
    @NotNull
    @JsonProperty("complete_time")
    private Instant completeTime;
}
