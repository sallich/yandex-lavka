package ru.yandex.yandexlavka.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="orders")
public class OrderDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("order_id")
    @NotNull
    private long orderId;
    @NotNull
    private float weight;
    @NotNull
    private int regions;
    @NotNull
    @ElementCollection
    @JsonProperty("delivery_hours")
    private List<@Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]-(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
            String> deliveryHours;
    @NotNull
    private int cost;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("completed_time")
    private Instant completedTime;
    @JsonIgnore
    @JsonProperty("courier_id")
    private long courierId;

}
