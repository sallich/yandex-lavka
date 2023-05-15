package ru.yandex.yandexlavka.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.yandexlavka.presentation.createDto.CreateCourierRequest;
import ru.yandex.yandexlavka.persistance.RepositoryCouriers;
import ru.yandex.yandexlavka.business.entities.CourierDto;

import java.util.List;
import java.util.Optional;

@Service
public class CourierService {
    private final RepositoryCouriers repository;
@Autowired
    public CourierService(RepositoryCouriers repository) {
        this.repository = repository;
    }

    public List<CourierDto> getCouriers(int limit, int offset){
        return repository.findAllWithLimitAndOffset(limit,offset);
    }

    public Optional<CourierDto> getCouriersId(long id){
        return repository.findById(id);
    }

    public List<CourierDto> postCouriers(CreateCourierRequest withoutId){
        return (List<CourierDto>) repository.saveAll(withoutId.getCouriers());
    }
}


