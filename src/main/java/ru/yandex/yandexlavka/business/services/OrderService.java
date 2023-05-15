package ru.yandex.yandexlavka.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.yandexlavka.presentation.createDto.CompleteOrderRequestDto;
import ru.yandex.yandexlavka.business.entities.CompleteOrder;
import ru.yandex.yandexlavka.presentation.createDto.GetCourierMetaInfoResponse;
import ru.yandex.yandexlavka.persistance.RepositoryCouriers;
import ru.yandex.yandexlavka.presentation.createDto.CreateOrderDto;
import ru.yandex.yandexlavka.persistance.RepositoryOrders;
import ru.yandex.yandexlavka.business.entities.CourierDto;
import ru.yandex.yandexlavka.business.entities.CourierType;
import ru.yandex.yandexlavka.business.entities.OrderDto;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final RepositoryOrders repositoryOrders;
    private final RepositoryCouriers repositoryCouriers;

    @Autowired
    OrderService(RepositoryOrders repository, RepositoryCouriers repositoryCouriers){
        this.repositoryOrders = repository;
        this.repositoryCouriers = repositoryCouriers;
    }
    public List<OrderDto> getOrders(int limit, int offset) {

        return repositoryOrders.findAllWithLimitAndOffset(limit,offset);
    }
    public Optional<OrderDto> getOrdersId(long id) {
        return repositoryOrders.findById(id);
    }
    public List<OrderDto> postOrders(CreateOrderDto withoutId) {
        return (List<OrderDto>) repositoryOrders.saveAll(withoutId.getOrders());
    }
    public List<OrderDto> postOrdersComplete(CompleteOrderRequestDto completeOrderRequestDto) {
        List<CompleteOrder> order = completeOrderRequestDto.getOrders();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (CompleteOrder completeOrder : order) {
            var orderOptional = repositoryOrders.findById(completeOrder.getOrderId());
            var courierOptional=repositoryCouriers.findById(completeOrder.getCourierId());
            if (orderOptional.isEmpty()||courierOptional.isEmpty()) {
                return null;
            }
            else {
                var orderDto = orderOptional.get();
                orderDto.setCompletedTime(completeOrder.getCompleteTime());
                orderDto.setCourierId(completeOrder.getCourierId());
                orderDtos.add(orderDto);
            }
        }
        return (List<OrderDto>) repositoryOrders.saveAll(orderDtos);
    }
    public GetCourierMetaInfoResponse getCouriersMeta(long courierId, LocalDate startData, LocalDate endData){
        Optional<CourierDto> courierDto = repositoryCouriers.findById(courierId);
        if (courierDto.isEmpty()){
            return null;
        }
        Instant changeStartData = startData.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant changeEndData = endData.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Integer sum = repositoryOrders.sumByCourierWithStartAndEnd(courierId,changeStartData,changeEndData);

        Integer count= repositoryOrders.countByCourierWithStartAndEnd(courierId,changeStartData, changeEndData);

        CourierDto courier = courierDto.get();

        if (count==0){
            return new GetCourierMetaInfoResponse(courierId, courier.getCourierType(), courier.getRegions(), courier.getWorkingHours(),0,0);
        }
        CourierType courierType = courier.getCourierType();
        int cForSum = switch (courierType){
            case FOOT -> 2;
            case BIKE -> 3;
            case AUTO -> 4;
        };
        sum*=cForSum;
        int cForCount = switch (courierType){
            case FOOT -> 3;
            case BIKE -> 2;
            case AUTO -> 1;
        };
        Duration duration = Duration.between(changeStartData, changeEndData);
        int hours = (int)duration.toHours();
        int rating = (int)(((float)count/hours)*cForCount);
        return new GetCourierMetaInfoResponse(courierId, courier.getCourierType(), courier.getRegions(), courier.getWorkingHours(), rating, sum);
    }
}
