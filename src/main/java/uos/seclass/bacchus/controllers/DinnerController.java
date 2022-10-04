package uos.seclass.bacchus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.dtos.InsertDinnerForm;
import uos.seclass.bacchus.dtos.PrintDinnerDTO;
import uos.seclass.bacchus.services.DinnerService;

import java.util.List;

@RestController()
@RequestMapping("/dinner")
public class DinnerController {

    private final DinnerService dinnerService;

    @Autowired
    public DinnerController(DinnerService dinnerService) {
        this.dinnerService = dinnerService;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Dinner 리스트 조회", protocols = "http")
    public List<Dinner> lookUpDinnerList() {
        List<Dinner> dinners = dinnerService.findAll();
        return dinners;
    }

    @GetMapping("/{num}")
    public ResponseEntity<PrintDinnerDTO> lookUpDinner(@PathVariable("num") Integer num) {
        PrintDinnerDTO dinner = dinnerService.findOne(num);
        return new ResponseEntity<>(dinner, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity register(@RequestBody InsertDinnerForm dinnerForm) {
        dinnerService.insert(dinnerForm.getInsertDinnerDTO(), dinnerForm.getFoodCountDTOS());
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }
}