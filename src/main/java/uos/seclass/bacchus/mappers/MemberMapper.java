package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import uos.seclass.bacchus.domains.Member;
import uos.seclass.bacchus.dtos.InsertMemberDTO;;
import uos.seclass.bacchus.dtos.UpdateMemberDTO;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member toEntity(InsertMemberDTO memberDTO);

    InsertMemberDTO toDTO(Member member);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateMemberDTO dto, @MappingTarget Member entity);
}

