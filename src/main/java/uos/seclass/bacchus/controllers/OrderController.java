package uos.seclass.bacchus.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.dtos.*;
import uos.seclass.bacchus.mappers.CustomerMapper;
import uos.seclass.bacchus.misc.ReturnMessage;
import uos.seclass.bacchus.misc.StatusEnum;
import uos.seclass.bacchus.services.AccountService;
import uos.seclass.bacchus.services.CustomerService;
import uos.seclass.bacchus.services.OrderService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController()
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final AccountService accountService;

    @Autowired
    public OrderController(OrderService orderService, AccountService accountService, CustomerService customerService) {
        this.orderService = orderService;
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "전체 주문 목록 조회", protocols = "http")
    public List<PrintOrderDTO> lookUpOrderList() {
        List<Order> orders = orderService.findAll();
        List<PrintOrderDTO> printOrders = new ArrayList<>();
        orders.forEach(order ->{
            HashSet<PrintOrderDinnerDTO> printOrderDinners = new HashSet<>();
            order.getOrderDinners().forEach((orderDinner -> {
                PrintDinnerDTO printDinnerDTO = PrintDinnerDTO.builder().dinnerNum(orderDinner.getDinner().getDinnerNum())
                        .name(orderDinner.getDinner().getName()).build();
                printOrderDinners.add(PrintOrderDinnerDTO.builder().dinner(printDinnerDTO)
                        .foodCounts(orderDinner.getFoodCounts()).style(orderDinner.getStyle()).build());
            }));

            printOrders.add(PrintOrderDTO.builder().orderNum(order.getOrderNum()).customerNum(order.getCustomer().getCustomerNum())
                    .customerName(order.getCustomer().getName()).orderTime(order.getOrderTime()).orderDinners(printOrderDinners)
                    .address(order.getAddress()).deliveredTime(order.getDeliveredTime()).wantedDeliveredTime(order.getWantedDeliveredTime())
                    .state(order.getState()).totalPrice(order.getTotalPrice()).build());
        });
        return printOrders;
    }

    @GetMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "고객별 주문 조회", protocols = "http")
    public ResponseEntity<Order> lookUpOrderByMember(@PathVariable("num") Integer num) {
        Order order = orderService.findOne(num);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "주문 등록", protocols = "http")
    public ResponseEntity register(@RequestBody InsertOrderForm orderForm) {
        InsertOrderDTO orderDTO = orderForm.getInsertOrderDTO();

        accountService.pay(orderDTO.getCardNum(), orderDTO.getCustomerNum(),orderDTO.getTotalPrice());
        Order order = orderService.insert(orderDTO, orderForm.getOrderDinnerDTOs());

        ReturnMessage<Order> msg = new ReturnMessage<>();
        msg.setMessage("주문이 완료되었습니다.");
        msg.setStatus(StatusEnum.OK);
        msg.setData(order);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "주문 상태 변경", protocols = "http")
    public ResponseEntity update(@PathVariable("num") Integer num, @RequestBody UpdateOrderDTO orderDTO) {
        Order order = orderService.update(num,orderDTO);

        // 주문 취소면 환불
        if(orderDTO.getState().equals("OC")){
            Customer customer = customerService.findOne(orderDTO.getCustomerNum());
            accountService.pay(customer.getCardNum(), customer.getCustomerNum(),-orderDTO.getTotalPrice());
        }

        ReturnMessage<Order> msg = new ReturnMessage<>();
        msg.setMessage("update suceess");
        msg.setStatus(StatusEnum.OK);
        msg.setData(order);

        return new ResponseEntity<ReturnMessage>(msg, HttpStatus.OK);
    }
}