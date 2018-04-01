package hr.from.ivantoplak.springmvcrest.services;

import hr.from.ivantoplak.springmvcrest.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
