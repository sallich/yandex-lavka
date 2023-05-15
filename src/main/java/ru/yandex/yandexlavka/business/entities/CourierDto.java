package ru.yandex.yandexlavka.business.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "couriers")
public class CourierDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("courier_id")
    @NotNull
    private long courierId;
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @JsonProperty("courier_type")
    private CourierType courierType;
    @NotNull
    @ElementCollection
    @JsonProperty("working_hours")
    private List<@Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]-(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
            String> workingHours;
    @NotNull
    @ElementCollection
    private List<Integer> regions;
}
