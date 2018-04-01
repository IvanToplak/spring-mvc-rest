package hr.from.ivantoplak.springmvcrest.api.v1.mapper;

import hr.from.ivantoplak.springmvcrest.api.v1.model.CategoryDTO;
import hr.from.ivantoplak.springmvcrest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDto(Category category);
}
