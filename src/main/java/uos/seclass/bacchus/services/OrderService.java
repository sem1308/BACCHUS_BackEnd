package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import uos.seclass.bacchus.dtos.UpdateOrderDTO;
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
    public OrderService(OrderRepository orderRepo, FoodOrderCountRepository foodCountRepo, AccountRepository accountRepo,
                        FoodRepository foodRepo, DinnerRepository dinnerRepo, CustomerRepository customerRepo, StyleRepository styleRepo) {
        this.orderRepo = orderRepo;
        this.foodRepo = foodRepo;
        this.foodCountRepo = foodCountRepo;
        this.dinnerRepo = dinnerRepo;
        this.customerRepo = customerRepo;
        this.styleRepo = styleRepo;
    }

    public List<Order> findAll() {
        List<Order> orders = orderRepo.findAll(Sort.by("orderNum").descending());
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

        newOrder.setCustomer(customerRepo.findById(orderDTO.getCustomerNum()).orElseThrow(() ->
                new ResourceNotFoundException("고객을 찾을 수 없습니다.")));
        newOrder.setStyle(styleRepo.findByStyleCode(orderDTO.getStyleCode()).orElseThrow(() ->
                new ResourceNotFoundException("스타일("+orderDTO.getStyleCode()+")을 찾을 수 없습니다.")));
        newOrder.setDinners(dinners);
        newOrder.setOrderTime(new Date());
        newOrder.setDeliveredTime(null);
        newOrder.setState("OS");
        /*
            - STATE -
            OS : Order Start
            CS : Cooking Start
            CE : Cooking End
            DS : Delivery Start
            DE : Delivery End
        */
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

    public Order update(Integer num, UpdateOrderDTO orderDTO) {

        Order order = orderRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 주문이 존재하지 않습니다."));

        OrderMapper.INSTANCE.updateFromDto(orderDTO, order);

        order.setDeliveredTime(orderDTO.getDeliveredTime());
        order.setState(orderDTO.getState());
        Order modifiedOrder = orderRepo.save(order);

        return modifiedOrder;
    }
}
