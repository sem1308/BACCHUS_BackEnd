package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Employee;
import uos.seclass.bacchus.dtos.InsertEmployeeDTO;
import uos.seclass.bacchus.dtos.UpdateEmployeeDTO;

;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toEntity(InsertEmployeeDTO employeeDTO);

    InsertEmployeeDTO toDTO(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateEmployeeDTO dto, @MappingTarget Employee entity);
}

