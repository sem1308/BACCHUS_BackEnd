package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.dtos.InsertFoodDTO;
import uos.seclass.bacchus.dtos.UpdateCustomerDTO;
import uos.seclass.bacchus.dtos.UpdateFoodDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.CustomerMapper;
import uos.seclass.bacchus.mappers.FoodMapper;
import uos.seclass.bacchus.repositories.FoodRepository;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepo;

    @Autowired
    public FoodService(FoodRepository foodRepo) {
        this.foodRepo = foodRepo;
    }

    public List<Food> findAll() {
        List<Food> foods = foodRepo.findAll(Sort.by("state").ascending().and(Sort.by("type").descending()));

        return foods;
    }

    public Food findOne(Integer num) {
        Food food = foodRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 음식이 존재하지 않습니다."));
        return food;
    }

    public Food insert(InsertFoodDTO foodDTO) {
        Food newFood = FoodMapper.INSTANCE.toEntity(foodDTO);
        newFood.setState("SA"); // SALE AVAILABLE
        newFood = foodRepo.save(newFood);

        return newFood;
    }

    public Food update(Integer num, UpdateFoodDTO foodDTO) {

        Food food = foodRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 음식이 존재하지 않습니다."));
        FoodMapper.INSTANCE.updateFromDto(foodDTO, food);
        Food newFood = foodRepo.save(food);

        return newFood;
    }
}
