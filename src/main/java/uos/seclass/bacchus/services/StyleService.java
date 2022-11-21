package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uos.seclass.bacchus.domains.Style;
import uos.seclass.bacchus.dtos.InsertStyleDTO;
import uos.seclass.bacchus.dtos.UpdateStyleDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.StyleMapper;
import uos.seclass.bacchus.repositories.StyleRepository;
import java.util.List;

@Service
public class StyleService {
    private final StyleRepository styleRepo;

    @Autowired
    public StyleService(StyleRepository styleRepo) {
        this.styleRepo = styleRepo;
    }

    public List<Style> findAll() {
        List<Style> styles = styleRepo.findAll();

        if (styles.isEmpty()) {
            throw new ResourceNotFoundException("스타일이 없습니다.");
        }

        return styles;
    }

    public Style findOne(String code) {
        Style style = styleRepo.findByStyleCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("코드가 "+code+"인 스타일이 존재하지 않습니다."));

        return style;
    }

    public Style insert(InsertStyleDTO styleDTO) {
        Style newStyle = StyleMapper.INSTANCE.toEntity(styleDTO);
        newStyle = styleRepo.save(newStyle);

        return newStyle;
    }

    public Style update(String code, UpdateStyleDTO styleDTO) {

        Style style = styleRepo.findByStyleCode(code).orElseThrow(() -> new ResourceNotFoundException("코드가 "+code+"인 스타일이 존재하지 않습니다."));
        StyleMapper.INSTANCE.updateFromDto(styleDTO, style);
        Style newStyle = styleRepo.save(style);

        return newStyle;
    }
}
