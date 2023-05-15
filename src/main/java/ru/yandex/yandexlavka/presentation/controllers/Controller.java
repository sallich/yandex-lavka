package ru.yandex.yandexlavka.presentation.controllers;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.presentation.createDto.CompleteOrderRequestDto;
import ru.yandex.yandexlavka.presentation.createDto.CreateCourierRequest;
import ru.yandex.yandexlavka.presentation.createDto.GetCourierMetaInfoResponse;
import ru.yandex.yandexlavka.presentation.createDto.CreateOrderDto;
import ru.yandex.yandexlavka.business.entities.CourierDto;
import ru.yandex.yandexlavka.business.entities.OrderDto;
import ru.yandex.yandexlavka.business.services.CourierService;
import ru.yandex.yandexlavka.business.services.OrderService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {
    Bucket bucket;
    Bucket bucket1;
    Bucket bucket2;
    Bucket bucket3;
    Bucket bucket4;
    Bucket bucket5;
    Bucket bucket6;
    Bucket bucket7;

    private final OrderService orderService;
    private final CourierService courierService;
    @Autowired
    public Controller(OrderService orderService, CourierService courierService){
        this.orderService = orderService;
        this.courierService = courierService;
        bucket = Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1))))
                .build();
        bucket1 = Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1))))
                .build();
        bucket2 = Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1))))
                .build();
        bucket3 = Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1))))
                .build();
        bucket4 = Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1))))
                .build();
        bucket5 = Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1))))
                .build();
        bucket6 = Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1))))
                .build();
        bucket7 = Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1))))
                .build();
    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@RequestParam(defaultValue = "1") int limit, @RequestParam(defaultValue = "0") int offset){
        if(limit<0||offset<0){
            return ResponseEntity.badRequest().build();
        }
        if (bucket.tryConsume(1)) {
            final List<OrderDto> orders = orderService.getOrders(limit,offset);
            return orders!=null && !orders.isEmpty()
                    ? new ResponseEntity<>(orders, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
    @GetMapping("/orders/{order_id}")
    public ResponseEntity<OrderDto> getOrdersId(@PathVariable long order_id) {
        if (bucket1.tryConsume(1)) {
            final Optional<OrderDto> order = orderService.getOrdersId(order_id);
            return order.isPresent()
                    ? new ResponseEntity<>(order.get(), HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping("/orders")
    public ResponseEntity<List<OrderDto>> postOrders(@RequestBody CreateOrderDto withoutId){
        if (bucket2.tryConsume(1)) {
            final List<OrderDto> orders = orderService.postOrders(withoutId);
            return orders!=null && !orders.isEmpty()
                    ? new ResponseEntity<>(orders, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
    @GetMapping("/couriers")
    public ResponseEntity<List<CourierDto>> getCouriers(@RequestParam(defaultValue = "1") int limit, @RequestParam(defaultValue = "0") int offset){
        if (limit<0 || offset<0){
            return ResponseEntity.badRequest().build();
        }
        if (bucket3.tryConsume(1)) {
            final List<CourierDto> couriers = courierService.getCouriers(limit,offset);
            return couriers!=null && !couriers.isEmpty()
                    ? new ResponseEntity<>(couriers, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
    @GetMapping("/couriers/{courier_id}")
    public ResponseEntity<Optional<CourierDto>> getCouriersId(@PathVariable long courier_id) {
        if (bucket4.tryConsume(1)) {
            final Optional<CourierDto> courier = courierService.getCouriersId(courier_id);
            return courier.isPresent()
                    ? new ResponseEntity<>(courier, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping("/couriers")
    public ResponseEntity<List<CourierDto>> postCouriers(@RequestBody CreateCourierRequest withoutId){
        if (bucket5.tryConsume(1)) {
            final List<CourierDto> couriers = courierService.postCouriers(withoutId);
            return couriers!=null && !couriers.isEmpty()
                    ? new ResponseEntity<>(couriers, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
    @PostMapping("/orders/complete")
    public ResponseEntity<List<OrderDto>> postOrdersComplete(@RequestBody CompleteOrderRequestDto completeOrderRequestDto){
        if (bucket6.tryConsume(1)) {
            final List<OrderDto> orders = orderService.postOrdersComplete(completeOrderRequestDto);
            return orders!=null && !orders.isEmpty()
                    ? new ResponseEntity<>(orders, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
    @GetMapping("/couriers/meta-info/{courier_id}")
    public ResponseEntity<GetCourierMetaInfoResponse> getCouriersMeta(@PathVariable long courier_id, @RequestParam LocalDate startDate, LocalDate endDate){
        if (bucket7.tryConsume(1)) {
            final GetCourierMetaInfoResponse courierMetaInfoResponse = orderService.getCouriersMeta(courier_id,startDate,endDate);
            return courierMetaInfoResponse!=null
                    ? new ResponseEntity<>(courierMetaInfoResponse, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

    }



}
