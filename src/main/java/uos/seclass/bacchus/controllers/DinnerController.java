package uos.seclass.bacchus.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.dtos.InsertDinnerForm;
import uos.seclass.bacchus.dtos.PrintDinnerDTO;
import uos.seclass.bacchus.dtos.UpdateDinnerForm;
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
    @ApiOperation(value = "전체 디너 목록 조회", protocols = "http")
    public List<Dinner> lookUpDinnerList() {
        List<Dinner> dinners = dinnerService.findAll();
        return dinners;
    }

    @GetMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "디너 상세 조회", protocols = "http")
    public ResponseEntity<PrintDinnerDTO> lookUpDinner(@PathVariable("num") Integer num) {
        PrintDinnerDTO dinner = dinnerService.findOne(num);
        return new ResponseEntity<>(dinner, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "디너 등록", protocols = "http")
    public ResponseEntity register(@RequestBody InsertDinnerForm dinnerForm) {
        dinnerService.insert(dinnerForm.getInsertDinnerDTO(), dinnerForm.getFoodCountDTOs());
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }

    @PutMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "디너 내용 변경", protocols = "http")
    public ResponseEntity update(@PathVariable("num") Integer num, @RequestBody UpdateDinnerForm dinnerForm) {
        dinnerService.update(num,dinnerForm.getUpdateDinnerDTO(), dinnerForm.getFoodCountDTOs());

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }
}