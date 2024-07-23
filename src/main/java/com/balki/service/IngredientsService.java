package com.balki.service;

import com.balki.model.IngredientCategory;
import com.balki.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId,String name,Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;
}
