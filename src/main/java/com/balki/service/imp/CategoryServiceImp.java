package com.balki.service.imp;

import com.balki.model.Category;
import com.balki.model.Restaurant;
import com.balki.repository.CategoryRepository;
import com.balki.service.CategoryService;
import com.balki.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    CategoryRepository categoryRepository;

    RestaurantService restaurantService;

    public CategoryServiceImp(CategoryRepository categoryRepository, RestaurantService restaurantService) {
        this.categoryRepository = categoryRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long Id) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantByUserId(Id);
        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()){
            throw new Exception("Category not found");
        }
        return optionalCategory.get();
    }
}
