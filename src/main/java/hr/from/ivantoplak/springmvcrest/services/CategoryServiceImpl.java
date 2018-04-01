package hr.from.ivantoplak.springmvcrest.services;

import hr.from.ivantoplak.springmvcrest.api.v1.mapper.CategoryMapper;
import hr.from.ivantoplak.springmvcrest.api.v1.model.CategoryDTO;
import hr.from.ivantoplak.springmvcrest.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {

        return categoryMapper.categoryToCategoryDto(categoryRepository.findByName(name));
    }
}
