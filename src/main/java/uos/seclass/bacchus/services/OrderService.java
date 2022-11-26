package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uos.seclass.bacchus.domains.*;
import uos.seclass.bacchus.dtos.*;
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

    private List<PrintOrderDTO> getPrintOrderDTOs(List<Order> orders){
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
                    .state(order.getState()).totalPrice(order.getTotalPrice()).cardNum(order.getCardNum()).build());
        });
        return printOrders;
    }

    public List<PrintOrderDTO> findAll() {
        List<Order> orders = orderRepo.findAll(Sort.by("orderNum").descending());
        return getPrintOrderDTOs(orders);
    }

    public Order findOne(Integer orderNum) {
        Order order = orderRepo.findById(orderNum).get();

        return order;
    }

    public List<Order> findAllByCustomer(Integer num) {
        List<Order> orders = orderRepo.findByCustomer(num);
        return orders;
    }

    private Order saveOrder(InsertOrderForm orderForm){
        InsertOrderDTO orderDTO = orderForm.getInsertOrderDTO();
        Order newOrder = OrderMapper.INSTANCE.toEntity(orderDTO);

        newOrder.setCustomer(customerRepo.findById(orderDTO.getCustomerNum()).orElseThrow(() ->
                new ResourceNotFoundException("고객을 찾을 수 없습니다.")));
        newOrder.setOrderTime(new Date());
        newOrder.setDeliveredTime(null);
        newOrder.setState("OS");
        /*
            - STATE -
            OC : Order Cancel
            OS : Order Start
            CS : Cooking Start
            CE : Cooking End
            DS : Delivery Start
            DE : Delivery End
        */
        orderRepo.save(newOrder);

        Set<InsertOrderDinnerDTO> orderDinnerDTOs = orderForm.getOrderDinnerDTOs();
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
        return(newOrder);
    }

    public Order insert(InsertOrderForm orderForm) {
        return saveOrder(orderForm);
    }

    public Order update(Integer num, UpdateOrderDTO orderDTO) {

        Order order = orderRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 주문이 존재하지 않습니다."));

        OrderMapper.INSTANCE.updateFromDto(orderDTO, order);

        Order modifiedOrder = orderRepo.save(order);

        return modifiedOrder;
    }
}
