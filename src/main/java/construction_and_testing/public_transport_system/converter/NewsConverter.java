package construction_and_testing.public_transport_system.converter;

import construction_and_testing.public_transport_system.domain.DTO.NewsDTO;
import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.domain.Operator;

public class NewsConverter extends AbstractConverter {

    public static News toEntity(NewsDTO dto){
        News entity = new News();
        entity.setId(dto.getId());
        entity.setActive(dto.isActive());
        entity.setContent(dto.getContent());
        entity.setDate(dto.getDate());
        entity.setTitle(dto.getTitle());
        Operator operator = new Operator();
        operator.setId(dto.getOperator());
        entity.setOperator(operator);
        return entity;
    }

    public static NewsDTO fromEntity(News entity){
        NewsDTO dto = new NewsDTO(entity);
        return dto;
    }
}
