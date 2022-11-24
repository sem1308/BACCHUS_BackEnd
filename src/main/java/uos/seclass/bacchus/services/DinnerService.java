package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.DinnerFoodCount;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.dtos.*;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.DinnerFoodCountMapper;
import uos.seclass.bacchus.mappers.DinnerMapper;
import uos.seclass.bacchus.repositories.DinnerRepository;
import uos.seclass.bacchus.repositories.DinnerFoodCountRepository;
import uos.seclass.bacchus.repositories.FoodRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DinnerService {
    private final DinnerRepository dinnerRepo;
    private final FoodRepository foodRepo;
    private final DinnerFoodCountRepository foodCountRepo;

    @Autowired
    public DinnerService(DinnerRepository dinnerRepo, DinnerFoodCountRepository foodCountRepo, FoodRepository foodRepo) {
        this.dinnerRepo = dinnerRepo;
        this.foodRepo = foodRepo;
        this.foodCountRepo = foodCountRepo;
    }

    public List<Dinner> findAll() {
        List<Dinner> dinners = dinnerRepo.findAll(Sort.by("state").ascending());

        return dinners;
    }

    public PrintDinnerDTO findOne(Integer num) {
        Dinner dinner = dinnerRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Dinner with id = " + num));

        PrintDinnerDTO printDinner = PrintDinnerDTO.builder().dinnerNum(dinner.getDinnerNum())
                .name(dinner.getName()).extraContent(dinner.getExtraContent()).numPeople(dinner.getNumPeople()).build();
        HashSet<PrintDinnerFoodCountDTO> foodCounts = new HashSet<>();
        dinner.getFoodCounts().forEach(foodCount -> foodCounts.add(PrintDinnerFoodCountDTO.builder()
                .count(foodCount.getCount()).foodDinnerCountNum(foodCount.getDinnerFoodCountNum())
                .dinnerNum(foodCount.getDinner().getDinnerNum()).food(foodCount.getFood()).build()));
        printDinner.setFoodCounts(foodCounts);
        return printDinner;
    }

    public Dinner insert(InsertDinnerDTO dinnerDTO, Set<InsertDinnerFoodCountDTO> foodCountDTOs) {
        Dinner newDinner = DinnerMapper.INSTANCE.toEntity(dinnerDTO);
        newDinner.setState("SA");
        dinnerRepo.save(newDinner).getDinnerNum();

        foodCountDTOs.forEach(foodCountDTO -> {
            Food food = foodRepo.findById(foodCountDTO.getFoodNum()).get();
            DinnerFoodCount newFoodCount = DinnerFoodCountMapper.INSTANCE.toEntity(foodCountDTO);
            newFoodCount.setFood(food);
            newFoodCount.setDinner(newDinner);

            foodCountRepo.save(newFoodCount);
        });

        return newDinner;
    }

    public Dinner update(Integer num, UpdateDinnerDTO dinnerDTO, Set<UpdateDinnerFoodCountDTO> foodCountDTOs) {

        Dinner dinner = dinnerRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("Not found Dinner with id = " + num));
        DinnerMapper.INSTANCE.updateFromDto(dinnerDTO, dinner);

        Dinner modifiedDinner = dinnerRepo.save(dinner);

        foodCountDTOs.forEach(foodCountDTO -> {
            Food food = foodRepo.findById(foodCountDTO.getFoodNum()).get();
            Integer fcNum = foodCountDTO.getDinnerFoodCountNum();
            DinnerFoodCount foodCount;
            if(fcNum != -1){
                foodCount = foodCountRepo.findById(foodCountDTO.getDinnerFoodCountNum())
                        .orElseThrow(() -> new ResourceNotFoundException("Not found FoodCount with id = " + num));
                DinnerFoodCountMapper.INSTANCE.updateFromDto(foodCountDTO, foodCount);
                foodCount.setFood(food);
            }else{
                InsertDinnerFoodCountDTO dto = new InsertDinnerFoodCountDTO();
                dto.setCount(foodCountDTO.getCount());
                foodCount = DinnerFoodCountMapper.INSTANCE.toEntity(dto);
                foodCount.setFood(food);
                foodCount.setDinner(modifiedDinner);
            }
            foodCountRepo.save(foodCount);
        });

        return modifiedDinner;
    }
}
