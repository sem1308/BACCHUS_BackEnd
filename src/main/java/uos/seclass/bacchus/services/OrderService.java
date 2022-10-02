package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.dtos.InsertOrderDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.OrderMapper;
import uos.seclass.bacchus.repositories.DinnerRepository;
import uos.seclass.bacchus.repositories.OrderRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final DinnerRepository dinnerRepo;
    private final OrderRepository orderRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo, DinnerRepository dinnerRepo) {
        this.orderRepo = orderRepo;
        this.dinnerRepo = dinnerRepo;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Member 리스트 조회", protocols = "http")
    public List<Order> findAll() {
        List<Order> orders = orderRepo.findAll();

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Not found Members");
        }

        return orders;
    }

    public Order findOne(Integer orderNum) {
        Order order = orderRepo.findById(orderNum).get();
        return order;
    }

    public List<Order> findAllByMember(Integer num) {
        List<Order> order = orderRepo.findByMember(num);
        return order;
    }

    public Order insert(InsertOrderDTO orderDTO) {
        Order newOrder = OrderMapper.INSTANCE.toEntity(orderDTO);

        HashSet<Dinner> dinners = new HashSet<Dinner>();
        orderDTO.getDinnerNum().forEach(dinnerNum -> dinners.add(dinnerRepo.findById(dinnerNum).get()));

        newOrder.setOrderTime(new Date());

        newOrder = orderRepo.save(newOrder);

        return newOrder;
    }
}
