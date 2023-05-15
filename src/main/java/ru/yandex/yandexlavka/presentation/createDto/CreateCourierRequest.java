package ru.yandex.yandexlavka.presentation.createDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.yandexlavka.business.entities.CourierDto;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateCourierRequest {
    @NotEmpty
    private List<CourierDto> couriers;
}
