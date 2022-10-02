package uos.seclass.bacchus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.dtos.InsertOrderDTO;
import uos.seclass.bacchus.services.OrderService;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Order 리스트 조회", protocols = "http")
    public List<Order> lookUpOrderList() {
        List<Order> orders = orderService.findAll();
        return orders;
    }

    @GetMapping("/{num}")
    public ResponseEntity<Order> lookUpOrderByMember(@PathVariable("num") Integer num) {
        Order order = orderService.findOne(num);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity register(@RequestBody InsertOrderDTO orderDTO) {
        orderService.insert(orderDTO);
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }
}