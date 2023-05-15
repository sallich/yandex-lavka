package ru.yandex.yandexlavka.presentation.createDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.yandexlavka.business.entities.CourierType;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetCourierMetaInfoResponse {
    @JsonProperty("courier_id")
    @NotNull
    private long courierId;
    @NotNull
    @JsonProperty("courier_type")
    private CourierType courierType;
    @NotNull
    private List<Integer> regions;
    @JsonProperty("working_hours")
    @NotNull
    private List<String> workingHours;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int rating;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int earnings;

}
