package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Member;
import uos.seclass.bacchus.dtos.InsertMemberDTO;
import uos.seclass.bacchus.dtos.UpdateMemberDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.MemberMapper;
import uos.seclass.bacchus.repositories.MemberRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    private final MemberRepository memberRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepo, PasswordEncoder passwordEncoder) {
        this.memberRepo = memberRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    //@ApiOperation(value = "Member 리스트 조회", protocols = "http")
    public List<Member> findAll() {
        List<Member> members = memberRepo.findAll();

        if (members.isEmpty()) {
            throw new ResourceNotFoundException("Not found Members");
        }

        return members;
    }

    public Member findOne(Integer num) {
        Member member = memberRepo.findById(num)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + num));

        return member;
    }

    public Member login(Map<String, String> loginInfo) {
        Member member = memberRepo.findById(loginInfo.get("id"))
                .orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + loginInfo.get("id")));

        // 암호 일치 확인
        if (!passwordEncoder.matches(loginInfo.get("pw"), member.getPw())) {
            throw new IllegalArgumentException("Wrong password");
        }

        return member;
    }

    public Member insert(InsertMemberDTO memberDTO) {
        if (memberRepo.findById(memberDTO.getId()).isPresent()) {
            throw new DuplicateKeyException("Duplicated ID");
        }

        Member newMember = MemberMapper.INSTANCE.toEntity(memberDTO);

        newMember.setPw(passwordEncoder.encode(newMember.getPw()));
        newMember.setCreatedAt(new Date());
        newMember.setRole("customer");

        newMember = memberRepo.save(newMember);

        return newMember;
    }

    public Member update(Integer num,UpdateMemberDTO memberDTO) {

        Member member = memberRepo.findById(num).orElseThrow(() -> new ResourceNotFoundException("Not found Member with id = " + num));
        System.out.println("name : " + memberDTO.getAddress());
        MemberMapper.INSTANCE.updateFromDto(memberDTO, member);
        System.out.println("name : " + member.getAddress());
        Member newMember = memberRepo.save(member);

        return newMember;
    }
}
