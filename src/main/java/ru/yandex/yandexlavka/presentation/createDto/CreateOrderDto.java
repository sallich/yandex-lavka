package ru.yandex.yandexlavka.presentation.createDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ru.yandex.yandexlavka.business.entities.OrderDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    @NotEmpty
    private List<@Valid OrderDto> orders;

}
