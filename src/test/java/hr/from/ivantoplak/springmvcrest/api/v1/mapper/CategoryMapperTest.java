package hr.from.ivantoplak.springmvcrest.api.v1.mapper;

import hr.from.ivantoplak.springmvcrest.api.v1.model.CategoryDTO;
import hr.from.ivantoplak.springmvcrest.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    private static final long ID = 1L;
    private static final String JOE = "Joe";

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDto() {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(JOE);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDto(category);

        //then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(JOE, categoryDTO.getName());
    }
}