package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.domains.FoodDinnerCount;
import uos.seclass.bacchus.dtos.*;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.DinnerMapper;
import uos.seclass.bacchus.mappers.FoodDinnerCountMapper;
import uos.seclass.bacchus.repositories.DinnerRepository;
import uos.seclass.bacchus.repositories.FoodDinnerCountRepository;
import uos.seclass.bacchus.repositories.FoodRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DinnerService {
    private final DinnerRepository dinnerRepo;
    private final FoodRepository foodRepo;
    private final FoodDinnerCountRepository foodCountRepo;

    @Autowired
    public DinnerService(DinnerRepository dinnerRepo, FoodDinnerCountRepository foodCountRepo, FoodRepository foodRepo) {
        this.dinnerRepo = dinnerRepo;
        this.foodRepo = foodRepo;
        this.foodCountRepo = foodCountRepo;
    }

    public List<Dinner> findAll() {
        List<Dinner> dinners = dinnerRepo.findAll();

        return dinners;
    }

    public PrintDinnerDTO findOne(Integer num) {
        Dinner dinner = dinnerRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Dinner with id = " + num));

        PrintDinnerDTO printDinner = PrintDinnerDTO.builder().dinnerNum(dinner.getDinnerNum())
                .name(dinner.getName()).extraContent(dinner.getExtraContent()).numPeople(dinner.getNumPeople()).build();
        HashSet<PrintFoodDinnerCountDTO> foodCounts = new HashSet<>();
        dinner.getFoodCounts().forEach(foodCount -> foodCounts.add(PrintFoodDinnerCountDTO.builder()
                .count(foodCount.getCount()).foodDinnerCountNum(foodCount.getFoodDinnerCountNum())
                .dinnerNum(foodCount.getDinner().getDinnerNum()).food(foodCount.getFood()).build()));
        printDinner.setFoodCounts(foodCounts);
        return printDinner;
    }

    public Dinner insert(InsertDinnerDTO dinnerDTO, Set<InsertFoodDinnerCountDTO> foodCountDTOs) {
        Dinner newDinner = DinnerMapper.INSTANCE.toEntity(dinnerDTO);
        newDinner.setState("SA");
        dinnerRepo.save(newDinner).getDinnerNum();

        foodCountDTOs.forEach(foodCountDTO -> {
            Food food = foodRepo.findById(foodCountDTO.getFoodNum()).get();
            FoodDinnerCount newFoodCount = FoodDinnerCountMapper.INSTANCE.toEntity(foodCountDTO);
            newFoodCount.setFood(food);
            newFoodCount.setDinner(newDinner);

            foodCountRepo.save(newFoodCount);
        });

        return newDinner;
    }

    public Dinner update(Integer num, UpdateDinnerDTO dinnerDTO, Set<UpdateFoodDinnerCountDTO> foodCountDTOs) {

        Dinner dinner = dinnerRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("Not found Dinner with id = " + num));
        DinnerMapper.INSTANCE.updateFromDto(dinnerDTO, dinner);

        Dinner modifiedDinner = dinnerRepo.save(dinner);

        foodCountDTOs.forEach(foodCountDTO -> {
            Food food = foodRepo.findById(foodCountDTO.getFoodNum()).get();
            Integer fcNum = foodCountDTO.getFoodDinnerCountNum();
            FoodDinnerCount foodCount;
            if(fcNum != -1){
                foodCount = foodCountRepo.findById(foodCountDTO.getFoodDinnerCountNum())
                        .orElseThrow(() -> new ResourceNotFoundException("Not found FoodCount with id = " + num));
                FoodDinnerCountMapper.INSTANCE.updateFromDto(foodCountDTO, foodCount);
                foodCount.setFood(food);
            }else{
                InsertFoodDinnerCountDTO dto = new InsertFoodDinnerCountDTO();
                dto.setCount(foodCountDTO.getCount());
                foodCount = FoodDinnerCountMapper.INSTANCE.toEntity(dto);
                foodCount.setFood(food);
                foodCount.setDinner(modifiedDinner);
            }
            foodCountRepo.save(foodCount);
        });

        return modifiedDinner;
    }
}
