package ru.yandex.yandexlavka.presentation.createDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.yandexlavka.business.entities.CompleteOrder;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompleteOrderRequestDto {
    @NotEmpty
    @JsonProperty("complete_info")
    private List<CompleteOrder> orders;
}
