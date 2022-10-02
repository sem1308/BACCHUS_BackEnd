package uos.seclass.bacchus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.dtos.InsertFoodDTO;
import uos.seclass.bacchus.services.FoodService;

import java.util.List;

@RestController()
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Food 리스트 조회", protocols = "http")
    public List<Food> lookUpFoodList() {
        List<Food> foods = foodService.findAll();
        return foods;
    }

    @GetMapping("/{num}")
    public ResponseEntity<Food> lookUpFood(@PathVariable("num") Integer num) {
        Food food = foodService.findOne(num);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity register(@RequestBody InsertFoodDTO foodDTO) {
        foodService.insert(foodDTO);
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }
}