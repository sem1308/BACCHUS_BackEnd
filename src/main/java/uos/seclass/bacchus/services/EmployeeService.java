package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uos.seclass.bacchus.domains.Employee;
import uos.seclass.bacchus.dtos.InsertEmployeeDTO;
import uos.seclass.bacchus.dtos.UpdateEmployeeDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.EmployeeMapper;
import uos.seclass.bacchus.repositories.EmployeeRepository;

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

    public List<Employee> findAll() {
        List<Employee> employees = employeeRepo.findAll();

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("직원이 없습니다.");
        }

        return employees;
    }

    public Employee findOne(Integer num) {
        Employee employee = employeeRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 직원이 존재하지 않습니다."));

        return employee;
    }

    public Employee login(Map<String, String> loginInfo) {
        Employee employee = employeeRepo.findById(loginInfo.get("id"))
                .orElseThrow(() -> new ResourceNotFoundException("아이디가 존재하지 않습니다."));

        // 암호 일치 확인
        if (!passwordEncoder.matches(loginInfo.get("pw"), employee.getPw())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return employee;
    }

    public boolean checkDuplicate(String id){
        return employeeRepo.findById(id).isPresent();
    }

    public Employee insert(InsertEmployeeDTO employeeDTO) {
        if (checkDuplicate(employeeDTO.getId())) {
            throw new DuplicateKeyException("ID가 중복되었습니다.");
        }

        Employee newEmployee = EmployeeMapper.INSTANCE.toEntity(employeeDTO);

        newEmployee.setPw(passwordEncoder.encode(newEmployee.getPw()));
        newEmployee.setEmployeedAt(new Date());

        newEmployee = employeeRepo.save(newEmployee);

        return newEmployee;
    }

    public Employee update(Integer num, UpdateEmployeeDTO employeeDTO) {

        Employee employee = employeeRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("번호가 "+num+"인 직원이 존재하지 않습니다."));
        EmployeeMapper.INSTANCE.updateFromDto(employeeDTO, employee);
        Employee newEmployee = employeeRepo.save(employee);

        return newEmployee;
    }
}
