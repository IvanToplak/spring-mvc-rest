package hr.from.ivantoplak.springmvcrest.services;

import hr.from.ivantoplak.springmvcrest.api.v1.mapper.CategoryMapper;
import hr.from.ivantoplak.springmvcrest.api.v1.model.CategoryDTO;
import hr.from.ivantoplak.springmvcrest.domain.Category;
import hr.from.ivantoplak.springmvcrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    private static final Long ID = 2L;
    private static final String NAME = "Jimmy";
    private static final int SIZE = 3;

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getAllCategories() {

        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        //then
        assertEquals(SIZE, categoryDTOS.size());
    }

    @Test
    public void getCategoryByName() {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        //then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}