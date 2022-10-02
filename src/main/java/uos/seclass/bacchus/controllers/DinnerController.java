package uos.seclass.bacchus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.dtos.InsertDinnerDTO;
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
    public ResponseEntity<Dinner> lookUpDinner(@PathVariable("num") Integer num) {
        Dinner order = dinnerService.findOne(num);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity register(@RequestBody InsertDinnerDTO dinnerDTO) {
        dinnerService.insert(dinnerDTO);
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }
}