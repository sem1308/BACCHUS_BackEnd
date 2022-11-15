package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public List<Customer> findAll() {
        List<Customer> customers = customerRepo.findAll();

        if (customers.isEmpty()) {
            throw new ResourceNotFoundException("고객이 없습니다.");
        }

        return customers;
    }

    public Customer findOne(Integer num) {
        Customer customer = customerRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 고객이 존재하지 않습니다."));

        return customer;
    }

    public Customer login(Map<String, String> loginInfo) {
        Customer customer = customerRepo.findById(loginInfo.get("id"))
                .orElseThrow(() -> new ResourceNotFoundException("아이디가 존재하지 않습니다."));

        // 암호 일치 확인
        if (!passwordEncoder.matches(loginInfo.get("pw"), customer.getPw())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return customer;
    }

    public Customer insert(InsertCustomerDTO customerDTO) {
        if (customerRepo.findById(customerDTO.getId()).isPresent()) {
            throw new DuplicateKeyException("아이디가 존재합니다.");
        }

        Customer newCustomer = CustomerMapper.INSTANCE.toEntity(customerDTO);

        newCustomer.setPw(passwordEncoder.encode(newCustomer.getPw()));
        newCustomer.setCreatedAt(new Date());

        newCustomer = customerRepo.save(newCustomer);

        return newCustomer;
    }

    public Customer update(Integer num, UpdateCustomerDTO customerDTO) {

        Customer customer = customerRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 고객이 존재하지 않습니다."));
        CustomerMapper.INSTANCE.updateFromDto(customerDTO, customer);
        Customer newCustomer = customerRepo.save(customer);

        return newCustomer;
    }
}
