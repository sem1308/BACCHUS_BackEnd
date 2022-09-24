package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uos.seclass.bacchus.domains.Employee;
import uos.seclass.bacchus.domains.Member;
import uos.seclass.bacchus.dtos.InsertEmployeeDTO;
import uos.seclass.bacchus.dtos.InsertMemberDTO;
import uos.seclass.bacchus.dtos.UpdateEmployeeDTO;
import uos.seclass.bacchus.dtos.UpdateMemberDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.EmployeeMapper;
import uos.seclass.bacchus.mappers.MemberMapper;
import uos.seclass.bacchus.repositories.EmployeeRepository;
import uos.seclass.bacchus.repositories.MemberRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Member 리스트 조회", protocols = "http")
    public List<Employee> findAll() {
        List<Employee> employees = employeeRepo.findAll();

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Not found Members");
        }

        return employees;
    }

    public Employee findOne(Integer num) {
        Employee employee = employeeRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + num));

        return employee;
    }

    public Employee login(Map<String, String> loginInfo) {
        Employee employee = employeeRepo.findById(loginInfo.get("id"))
                .orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + loginInfo.get("id")));

        // 암호 일치 확인
        if (!passwordEncoder.matches(loginInfo.get("pw"), employee.getPw())) {
            throw new IllegalArgumentException("Wrong password");
        }

        return employee;
    }

    public Employee insert(InsertEmployeeDTO employeeDTO) {
        if (employeeRepo.findById(employeeDTO.getId()).isPresent()) {
            throw new DuplicateKeyException("Duplicated ID");
        }

        Employee newEmployee = EmployeeMapper.INSTANCE.toEntity(employeeDTO);

        newEmployee.setPw(passwordEncoder.encode(newEmployee.getPw()));
        newEmployee.setEmployeedAt(new Date());

        newEmployee = employeeRepo.save(newEmployee);

        return newEmployee;
    }

    public Employee update(Integer num, UpdateEmployeeDTO employeeDTO) {

        Employee employee = employeeRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + num));
        EmployeeMapper.INSTANCE.updateFromDto(employeeDTO, employee);
        Employee newEmployee = employeeRepo.save(employee);

        return newEmployee;
    }
}
