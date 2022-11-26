package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.dtos.*;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.CustomerMapper;
import uos.seclass.bacchus.repositories.CustomerRepository;

import java.util.*;

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

    public PrintCustomerDTO findOne(Integer num) {
        Customer customer = customerRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 고객이 존재하지 않습니다."));

        return getPrintCustomerDTO(customer);
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

    public void checkDuplicate(String id){
        if (customerRepo.findById(id).isPresent()) {
            throw new DuplicateKeyException("아이디가 존재합니다.");
        }
    }

    public Customer insert(InsertCustomerDTO customerDTO) {
        Customer newCustomer = CustomerMapper.INSTANCE.toEntity(customerDTO);

        checkDuplicate(newCustomer.getId());

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
