package uos.seclass.bacchus.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uos.seclass.bacchus.domains.Style;
import uos.seclass.bacchus.dtos.InsertStyleDTO;
import uos.seclass.bacchus.dtos.UpdateStyleDTO;
import uos.seclass.bacchus.services.StyleService;

import java.util.List;

@RestController()
@RequestMapping("/style")
public class StyleController {

    private final StyleService styleService;

    @Autowired
    public StyleController(StyleService styleService){
        this.styleService = styleService;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "전체 스타일 목록 조회", protocols = "http")
    public List<Style> lookUpStyle() {
        List<Style> styles = styleService.findAll();
        return styles;
    }

    @GetMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "스타일 상세 조회", protocols = "http")
    public ResponseEntity<Style> lookUpStyle(@PathVariable("code") String code) {
        Style style = styleService.findOne(code);
        return new ResponseEntity<>(style, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "스타일 등록", protocols = "http")
    public ResponseEntity register(@RequestBody InsertStyleDTO styleDTO) {
        styleService.insert(styleDTO);
        return new ResponseEntity<>("register success", HttpStatus.OK);
    }

    @PutMapping("/{num}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "스타일 정보 변경", protocols = "http")
    public ResponseEntity update(@PathVariable("code") String code, @RequestBody UpdateStyleDTO styleDTO) {
        styleService.update(code,styleDTO);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }

}

