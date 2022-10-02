package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.dtos.InsertDinnerDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.DinnerMapper;
import uos.seclass.bacchus.repositories.DinnerRepository;
import uos.seclass.bacchus.repositories.FoodRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class DinnerService {
    private final DinnerRepository dinnerRepo;
    private final FoodRepository foodRepo;

    @Autowired
    public DinnerService(DinnerRepository dinnerRepo, FoodRepository foodRepo) {
        this.dinnerRepo = dinnerRepo;
        this.foodRepo = foodRepo;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Dinner 리스트 조회", protocols = "http")
    public List<Dinner> findAll() {
        List<Dinner> dinners = dinnerRepo.findAll();

        if (dinners.isEmpty()) {
            throw new ResourceNotFoundException("Not found Members");
        }

        return dinners;
    }

    public Dinner findOne(Integer num) {
        Dinner dinner = dinnerRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Dinner with id = " + num));
        return dinner;
    }

    public Dinner insert(InsertDinnerDTO dinnerDTO) {
        Dinner newDinner = DinnerMapper.INSTANCE.toEntity(dinnerDTO);

        HashSet<Food> foods = new HashSet<Food>();
        dinnerDTO.getFoodNum().forEach(foodNum -> foods.add(foodRepo.findById(foodNum).get()));

        newDinner.setFoods(foods);
        newDinner = dinnerRepo.save(newDinner);

        return newDinner;
    }
}
