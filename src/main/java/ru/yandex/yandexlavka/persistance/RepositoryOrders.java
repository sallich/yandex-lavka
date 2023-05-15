package ru.yandex.yandexlavka.persistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.yandex.yandexlavka.business.entities.OrderDto;

import java.time.Instant;
import java.util.List;

@org.springframework.stereotype.Repository
public interface RepositoryOrders extends CrudRepository<OrderDto, Long> {
    @Query("SELECT o FROM OrderDto o ORDER BY o.orderId OFFSET :offset FETCH NEXT :limit ROWS ONLY")
    List<OrderDto> findAllWithLimitAndOffset(int limit, int offset);
    @Query("SELECT sum(o.cost) FROM OrderDto o WHERE o.courierId = :courierId AND o.completedTime >= :startData AND o.completedTime < :endData")
    Integer sumByCourierWithStartAndEnd(long courierId, Instant startData, Instant endData);

    @Query("SELECT count(o) FROM OrderDto o WHERE o.courierId = :courierId AND o.completedTime >= :startData AND o.completedTime < :endData")
    Integer countByCourierWithStartAndEnd(long courierId, Instant startData, Instant endData);
}

