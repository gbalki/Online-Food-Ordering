package com.balki.request;

import com.balki.model.Category;
import com.balki.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;

    private String description;

    private Long price;

    private Category category;

    private List<String> images;

    private Long restaurantId;

    private boolean vegetarian;

    private boolean seasonal;

    private List<IngredientsItem> ingredients;
}
