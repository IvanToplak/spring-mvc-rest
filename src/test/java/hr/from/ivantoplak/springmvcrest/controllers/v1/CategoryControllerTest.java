package hr.from.ivantoplak.springmvcrest.controllers.v1;

import hr.from.ivantoplak.springmvcrest.api.v1.model.CategoryDTO;
import hr.from.ivantoplak.springmvcrest.controllers.RestResponseEntityExceptionHandler;
import hr.from.ivantoplak.springmvcrest.services.CategoryService;
import hr.from.ivantoplak.springmvcrest.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;
    private static final String JIM = "Jim";
    private static final String BOB = "Bob";

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void listCategories() throws Exception {

        //given
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(ID_1);
        categoryDTO1.setName(JIM);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(ID_2);
        categoryDTO2.setName(BOB);

        List<CategoryDTO> categories = Arrays.asList(categoryDTO1, categoryDTO2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        //when
        mockMvc.perform(get(CategoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void getCategoryByName() throws Exception {

        //given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID_1);
        categoryDTO.setName(JIM);

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        //when
        mockMvc.perform(get(CategoryController.BASE_URL + "/Jim")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.name", equalTo(JIM)));
    }

    @Test
    public void getByNameNotFound() throws Exception {

        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CategoryController.BASE_URL + "/Foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}