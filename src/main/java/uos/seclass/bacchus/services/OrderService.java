package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.domains.FoodOrderCount;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.dtos.InsertFoodOrderCountDTO;
import uos.seclass.bacchus.dtos.InsertOrderDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.FoodOrderCountMapper;
import uos.seclass.bacchus.mappers.OrderMapper;
import uos.seclass.bacchus.repositories.*;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final FoodRepository foodRepo;
    private final FoodOrderCountRepository foodCountRepo;
    private final DinnerRepository dinnerRepo;
    private final CustomerRepository customerRepo;
    private final StyleRepository styleRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo, FoodOrderCountRepository foodCountRepo,
                        FoodRepository foodRepo, DinnerRepository dinnerRepo, CustomerRepository customerRepo, StyleRepository styleRepo) {
        this.orderRepo = orderRepo;
        this.foodRepo = foodRepo;
        this.foodCountRepo = foodCountRepo;
        this.dinnerRepo = dinnerRepo;
        this.customerRepo = customerRepo;
        this.styleRepo = styleRepo;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Order 리스트 조회", protocols = "http")
    public List<Order> findAll() {
        List<Order> orders = orderRepo.findAll();

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Not found Orders");
        }

        return orders;
    }

    public Order findOne(Integer orderNum) {
        Order order = orderRepo.findById(orderNum).get();
        return order;
    }

    public List<Order> findAllByCustomer(Integer num) {
        List<Order> order = orderRepo.findByCustomer(num);
        return order;
    }

    public Order insert(InsertOrderDTO orderDTO, Set<InsertFoodOrderCountDTO> foodCountDTOs) {

        Order newOrder = OrderMapper.INSTANCE.toEntity(orderDTO);

        HashSet<Dinner> dinners = new HashSet<>();
        orderDTO.getDinnerNum().forEach(dinnerNUm -> dinners.add(dinnerRepo.findById(dinnerNUm).get()));

        newOrder.setDinners(dinners);
        newOrder.setStyle(styleRepo.findByStyleCode(orderDTO.getStyleCode()));
        newOrder.setCustomer(customerRepo.findById(orderDTO.getMemberNum()).get());
        newOrder.setOrderTime(new Date());
        System.out.println(newOrder.getStyle().getStyleCode());
        orderRepo.save(newOrder);

        foodCountDTOs.forEach(foodCountDTO -> {
            Food food = foodRepo.findById(foodCountDTO.getFoodNum()).get();
            FoodOrderCount newFoodCount = FoodOrderCountMapper.INSTANCE.toEntity(foodCountDTO);
            newFoodCount.setFood(food);
            newFoodCount.setOrder(newOrder);
            foodCountRepo.save(newFoodCount);
        });

        return newOrder;
    }
}
