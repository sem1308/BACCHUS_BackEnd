package uos.seclass.bacchus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Employee;
import uos.seclass.bacchus.dtos.InsertEmployeeDTO;
import uos.seclass.bacchus.dtos.UpdateEmployeeDTO;
import uos.seclass.bacchus.services.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Member 리스트 조회", protocols = "http")
    public List<Employee> lookUpMemberList() {
        List<Employee> employees = employeeService.findAll();
        return employees;
    }

    @GetMapping("/{num}")
    public ResponseEntity<Employee> lookUpMember(@PathVariable("num") Integer num) {
        Employee employee = employeeService.findOne(num);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity register(@RequestBody InsertEmployeeDTO employeeDTO) {
        employeeService.insert(employeeDTO);
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity login(@RequestBody Map<String, String> loginInfo) {
        employeeService.login(loginInfo);
        return new ResponseEntity<>("login success", HttpStatus.OK);
    }

    @PutMapping("/{num}")
    public ResponseEntity update(@PathVariable("num") Integer num, @RequestBody UpdateEmployeeDTO employeeDTO) {
        employeeService.update(num,employeeDTO);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }
}