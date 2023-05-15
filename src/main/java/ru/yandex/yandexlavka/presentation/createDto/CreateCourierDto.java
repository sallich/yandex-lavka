package ru.yandex.yandexlavka.presentation.createDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.yandex.yandexlavka.business.entities.CourierDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourierDto {
    @NotEmpty
    private List<@Valid CourierDto> couriers;

}
