package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uos.seclass.bacchus.domains.*;
import uos.seclass.bacchus.dtos.InsertOrderDinnerDTO;
import uos.seclass.bacchus.dtos.InsertOrderDTO;
import uos.seclass.bacchus.dtos.PrintDinnerFoodCountDTO;
import uos.seclass.bacchus.dtos.UpdateOrderDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.OrderDinnerMapper;
import uos.seclass.bacchus.mappers.OrderMapper;
import uos.seclass.bacchus.mappers.OrderFoodCountMapper;
import uos.seclass.bacchus.repositories.*;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final FoodRepository foodRepo;
    private final OrderFoodCountRepository foodCountRepo;
    private final OrderDinnerRepository orderDinnerRepo;
    private final DinnerRepository dinnerRepo;
    private final CustomerRepository customerRepo;
    private final StyleRepository styleRepo;

    @Autowired
    public OrderService(OrderRepository orderRepo, OrderFoodCountRepository foodCountRepo, AccountRepository accountRepo, OrderDinnerRepository orderDinnerRepo,
                        FoodRepository foodRepo, DinnerRepository dinnerRepo, CustomerRepository customerRepo, StyleRepository styleRepo) {
        this.orderRepo = orderRepo;
        this.foodRepo = foodRepo;
        this.foodCountRepo = foodCountRepo;
        this.dinnerRepo = dinnerRepo;
        this.customerRepo = customerRepo;
        this.styleRepo = styleRepo;
        this.orderDinnerRepo = orderDinnerRepo;
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

    public Order insert(InsertOrderDTO orderDTO, Set<InsertOrderDinnerDTO> orderDinnerDTOs) {
        Order newOrder = OrderMapper.INSTANCE.toEntity(orderDTO);

        newOrder.setCustomer(customerRepo.findById(orderDTO.getCustomerNum()).orElseThrow(() ->
                new ResourceNotFoundException("고객을 찾을 수 없습니다.")));
        newOrder.setOrderTime(new Date());
        newOrder.setDeliveredTime(null);
        newOrder.setState("OS");
        /*
            - STATE -
            OC : Order Canceld
            OS : Order Start
            CS : Cooking Start
            CE : Cooking End
            DS : Delivery Start
            DE : Delivery End
        */
        orderRepo.save(newOrder);

        orderDinnerDTOs.forEach(orderDinnerDTO -> {
            OrderDinner newOrderDinner = OrderDinnerMapper.INSTANCE.toEntity(orderDinnerDTO);
            newOrderDinner.setOrder(newOrder);
            newOrderDinner.setStyle(styleRepo.findByStyleCode(orderDinnerDTO.getStyleCode()).orElseThrow(() ->
                    new ResourceNotFoundException("스타일("+orderDinnerDTO.getStyleCode()+")을 찾을 수 없습니다.")));
            newOrderDinner.setDinner(dinnerRepo.findById(orderDinnerDTO.getDinnerNum()).orElseThrow(() ->
                    new ResourceNotFoundException("디너를 찾을 수 없습니다.")));

            orderDinnerRepo.save(newOrderDinner);
            orderDinnerDTO.getInsertOrderFoodCountDTOs().forEach((orderFoodCountDTO)->{
                OrderFoodCount orderFoodCount = OrderFoodCountMapper.INSTANCE.toEntity(orderFoodCountDTO);
                orderFoodCount.setOrderDinner(newOrderDinner);
                orderFoodCount.setFood(foodRepo.findById(orderFoodCountDTO.getFoodNum()).orElseThrow(() ->
                        new ResourceNotFoundException("디너를 찾을 수 없습니다.")));
                foodCountRepo.save(orderFoodCount);
            });
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
