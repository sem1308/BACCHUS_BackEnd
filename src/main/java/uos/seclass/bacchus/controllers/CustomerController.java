package uos.seclass.bacchus.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.dtos.InsertCustomerDTO;
import uos.seclass.bacchus.dtos.UpdateCustomerDTO;
import uos.seclass.bacchus.services.CustomerService;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Member 리스트 조회", protocols = "http")
    public List<Customer> lookUpMemberList() {
        List<Customer> customers = customerService.findAll();
        return customers;
    }

    @GetMapping("/{num}")
    public ResponseEntity<Customer> lookUpMember(@PathVariable("num") Integer num) {
        Customer customer = customerService.findOne(num);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity register(@RequestBody InsertCustomerDTO customerDTO) {
        customerService.insert(customerDTO);
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity login(@RequestBody Map<String, String> loginInfo) {
        Customer customer = customerService.login(loginInfo);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{num}")
    public ResponseEntity update(@PathVariable("num") Integer num, @RequestBody UpdateCustomerDTO customerDTO) {
        customerService.update(num,customerDTO);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }
}