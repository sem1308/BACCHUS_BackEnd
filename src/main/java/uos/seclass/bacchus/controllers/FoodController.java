package uos.seclass.bacchus.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.dtos.InsertFoodDTO;
import uos.seclass.bacchus.dtos.UpdateFoodDTO;
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
    @ApiOperation(value = "전체 음식 목록 조회", protocols = "http")
    public List<Food> lookUpFoodList() {
        List<Food> foods = foodService.findAll();
        return foods;
    }

    @GetMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "음식 상세 조회", protocols = "http")
    public ResponseEntity<Food> lookUpFood(@PathVariable("num") Integer num) {
        Food food = foodService.findOne(num);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "음식 등록", protocols = "http")
    public ResponseEntity register(@RequestBody InsertFoodDTO foodDTO) {
        foodService.insert(foodDTO);
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }

    @PutMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "음식 정보 변경", protocols = "http")
    public ResponseEntity update(@PathVariable("num") Integer num, @RequestBody UpdateFoodDTO foodDTO) {
        foodService.update(num,foodDTO);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }
}