package ru.yandex.yandexlavka.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.yandex.yandexlavka.business.entities.CourierDto;

import java.util.List;

@org.springframework.stereotype.Repository
public interface RepositoryCouriers extends CrudRepository<CourierDto,Long>{
    @Query("SELECT o FROM CourierDto o ORDER BY o.courierId OFFSET :offset FETCH NEXT :limit ROWS ONLY")
    List<CourierDto> findAllWithLimitAndOffset(int limit, int offset);
}
