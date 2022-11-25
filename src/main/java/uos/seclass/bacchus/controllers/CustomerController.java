package uos.seclass.bacchus.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.dtos.*;
import uos.seclass.bacchus.misc.JwtTokenProvider;
import uos.seclass.bacchus.misc.ReturnMessage;
import uos.seclass.bacchus.misc.StatusEnum;
import uos.seclass.bacchus.services.AccountService;
import uos.seclass.bacchus.services.CustomerService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CustomerController(CustomerService customerService, AccountService accountService, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "전체 고객 목록 조회", protocols = "http")
    public List<Customer> lookUpMemberList() {
        List<Customer> customers = customerService.findAll();
        return customers;
    }

    @GetMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "고객 상세 조회", protocols = "http")
    public ResponseEntity<PrintCustomerDTO> lookUpMember(@PathVariable("num") Integer num) {
        Customer customer = customerService.findOne(num);

        PrintCustomerDTO cust = getPrintCustomerDTO(customer);
        return new ResponseEntity<>(cust, HttpStatus.OK);
    }

    private PrintCustomerDTO getPrintCustomerDTO(Customer customer){
        List<PrintOrderDTO> printOrders = new ArrayList<>();
        customer.getOrders().forEach(order ->{
            HashSet<PrintOrderDinnerDTO> printOrderDinners = new HashSet<>();
            order.getOrderDinners().forEach((orderDinner -> {
                PrintDinnerDTO printDinnerDTO = PrintDinnerDTO.builder().dinnerNum(orderDinner.getDinner().getDinnerNum())
                        .name(orderDinner.getDinner().getName()).extraContent(orderDinner.getDinner().getExtraContent()).build();
                printOrderDinners.add(PrintOrderDinnerDTO.builder().dinner(printDinnerDTO)
                        .foodCounts(orderDinner.getFoodCounts()).style(orderDinner.getStyle()).build());
            }));

            printOrders.add(PrintOrderDTO.builder().orderNum(order.getOrderNum())
                    .customerName(order.getCustomer().getName()).orderTime(order.getOrderTime()).orderDinners(printOrderDinners)
                    .address(order.getAddress()).deliveredTime(order.getDeliveredTime()).wantedDeliveredTime(order.getWantedDeliveredTime())
                    .state(order.getState()).totalPrice(order.getTotalPrice()).build());
        });

       return PrintCustomerDTO.builder().customerNum(customer.getCustomerNum()).cardNum(customer.getCardNum()).address(customer.getAddress())
                .name(customer.getName()).orders(printOrders).build();
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "고객 회원가입", protocols = "http")
    public ResponseEntity register(@RequestBody InsertCustomerDTO customerDTO) {
        ReturnMessage<Customer> msg = new ReturnMessage<>();
        accountService.checkDuplicate(customerDTO.getCardNum());
        Customer customer = customerService.insert(customerDTO);
        msg.setMessage("회원가입이 완료되었습니다.");
        msg.setData(customer);
        msg.setStatus(StatusEnum.OK);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "고객 로그인", protocols = "http")
    public ResponseEntity login(@RequestBody Map<String, String> loginInfo) {
        ReturnMessage<String> msg = new ReturnMessage<>();

        Customer customer = customerService.login(loginInfo);

        msg.setMessage("로그인이 완료되었습니다.");
        msg.setStatus(StatusEnum.OK);

        List<String> roles = new ArrayList<>();
        roles.add("CUSTO");
        String token = jwtTokenProvider.createToken(customer.getCustomerNum(),customer.getId(), roles);
        msg.setData(token);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "고객 정보 변경", protocols = "http")
    public ResponseEntity update(@PathVariable("num") Integer num, @RequestBody UpdateCustomerDTO customerDTO) {
        customerService.update(num,customerDTO);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }
}