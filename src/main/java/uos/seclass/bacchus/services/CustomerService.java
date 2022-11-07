package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.dtos.InsertCustomerDTO;
import uos.seclass.bacchus.dtos.UpdateCustomerDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.CustomerMapper;
import uos.seclass.bacchus.repositories.CustomerRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepo, PasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Member 리스트 조회", protocols = "http")
    public List<Customer> findAll() {
        List<Customer> customers = customerRepo.findAll();

        if (customers.isEmpty()) {
            throw new ResourceNotFoundException("Not found Customers");
        }

        return customers;
    }

    public Customer findOne(Integer num) {
        Customer customer = customerRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + num));

        return customer;
    }

    public Customer login(Map<String, String> loginInfo) {
        Customer customer = customerRepo.findById(loginInfo.get("id"))
                .orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + loginInfo.get("id")));

        // 암호 일치 확인
        if (!passwordEncoder.matches(loginInfo.get("pw"), customer.getPw())) {
            throw new IllegalArgumentException("Wrong password");
        }

        return customer;
    }

    public Customer insert(InsertCustomerDTO customerDTO) {
        if (customerRepo.findById(customerDTO.getId()).isPresent()) {
            throw new DuplicateKeyException("Duplicated ID");
        }

        Customer newCustomer = CustomerMapper.INSTANCE.toEntity(customerDTO);

        newCustomer.setPw(passwordEncoder.encode(newCustomer.getPw()));
        newCustomer.setCreatedAt(new Date());
        newCustomer.setRole("customer");

        newCustomer = customerRepo.save(newCustomer);

        return newCustomer;
    }

    public Customer update(Integer num, UpdateCustomerDTO customerDTO) {

        Customer customer = customerRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + num));
        System.out.println("name : " + customerDTO.getAddress());
        CustomerMapper.INSTANCE.updateFromDto(customerDTO, customer);
        System.out.println("name : " + customer.getAddress());
        Customer newCustomer = customerRepo.save(customer);

        return newCustomer;
    }
}
