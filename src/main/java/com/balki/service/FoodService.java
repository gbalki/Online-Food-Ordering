package com.balki.service;

import com.balki.model.Category;
import com.balki.model.Food;
import com.balki.model.Restaurant;
import com.balki.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest reg, Category category, Restaurant restaurant);

    void deleteFood(Long id) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegetarian ,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
