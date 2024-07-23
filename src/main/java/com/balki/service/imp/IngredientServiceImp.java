package com.balki.service.imp;

import com.balki.model.IngredientCategory;
import com.balki.model.IngredientsItem;
import com.balki.model.Restaurant;
import com.balki.repository.IngredientCategoryRepository;
import com.balki.repository.IngredientItemRepository;
import com.balki.service.IngredientsService;
import com.balki.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientsService {

    IngredientItemRepository ingredientItemRepository;

    IngredientCategoryRepository ingredientCategoryRepository;

    RestaurantService restaurantService;

    public IngredientServiceImp(IngredientItemRepository ingredientItemRepository, IngredientCategoryRepository ingredientCategoryRepository, RestaurantService restaurantService) {
        this.ingredientItemRepository = ingredientItemRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = new IngredientCategory();
        category.setName(name);
        category.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("Ingredient category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String name, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(name);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredient = ingredientItemRepository.save(item);
        category.getIngredientsItems().add(ingredient);
        return ingredient;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> opt = ingredientItemRepository.findById(id);

        if (opt.isEmpty()) {
            throw new Exception("ingredient not found");
        }

        IngredientsItem ingredientsItem = opt.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientItemRepository.save(ingredientsItem);
    }
}
