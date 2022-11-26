package uos.seclass.bacchus.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.domains.Employee;
import uos.seclass.bacchus.dtos.InsertEmployeeDTO;
import uos.seclass.bacchus.dtos.UpdateEmployeeDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.misc.JwtTokenProvider;
import uos.seclass.bacchus.misc.ReturnMessage;
import uos.seclass.bacchus.misc.StatusEnum;
import uos.seclass.bacchus.services.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public EmployeeController(EmployeeService employeeService, JwtTokenProvider jwtTokenProvider) {
        this.employeeService = employeeService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "전체 직원 목록 조회", protocols = "http")
    public List<Employee> lookUpMemberList() {
        List<Employee> employees = employeeService.findAll();
        return employees;
    }

    @GetMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "직원 상세 조회", protocols = "http")
    public ResponseEntity<Employee> lookUpMember(@PathVariable("num") Integer num) {
        Employee employee = employeeService.findOne(num);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "직원 등록", protocols = "http")
    public ResponseEntity register(@RequestBody InsertEmployeeDTO employeeDTO) {
        ReturnMessage<Employee> msg = new ReturnMessage<>();
        Employee employee = employeeService.insert(employeeDTO);
        msg.setMessage("회원가입 요청이 완료되었습니다.");
        msg.setData(employee);
        msg.setStatus(StatusEnum.OK);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "직원 로그인", protocols = "http")
    public ResponseEntity login(@RequestBody Map<String, String> loginInfo) {
        ReturnMessage<String> msg = new ReturnMessage<>();

        Employee employee = employeeService.login(loginInfo);
        msg.setMessage("로그인이 완료되었습니다.");
        msg.setStatus(StatusEnum.OK);

        List<String> roles = new ArrayList<>();
        roles.add("EMPLO");
        if(employee.getOccupation().equals("RM")){
            roles.add("ADMIN");
        }
        String token = jwtTokenProvider.createToken(employee.getEmployeeNum(),employee.getId(), employee.getName(),roles);
        msg.setData(token);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "직원 정보 변경", protocols = "http")
    public ResponseEntity update(@PathVariable("num") Integer num, @RequestBody UpdateEmployeeDTO employeeDTO) {
        employeeService.update(num,employeeDTO);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }
}