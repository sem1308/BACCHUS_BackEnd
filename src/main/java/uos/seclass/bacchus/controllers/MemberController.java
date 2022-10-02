package uos.seclass.bacchus.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Member;
import uos.seclass.bacchus.dtos.InsertMemberDTO;
import uos.seclass.bacchus.dtos.UpdateMemberDTO;
import uos.seclass.bacchus.services.MemberService;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Member 리스트 조회", protocols = "http")
    public List<Member> lookUpMemberList() {
        List<Member> members = memberService.findAll();
        return members;
    }

    @GetMapping("/{num}")
    public ResponseEntity<Member> lookUpMember(@PathVariable("num") Integer num) {
        Member member = memberService.findOne(num);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity register(@RequestBody InsertMemberDTO memberDTO) {
        memberService.insert(memberDTO);
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity login(@RequestBody Map<String, String> loginInfo) {
        memberService.login(loginInfo);
        return new ResponseEntity<>("login success", HttpStatus.OK);
    }

    @PutMapping("/{num}")
    public ResponseEntity update(@PathVariable("num") Integer num, @RequestBody UpdateMemberDTO memberDTO) {
        memberService.update(num,memberDTO);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }
}