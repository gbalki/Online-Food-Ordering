package com.balki.controller;

import com.balki.model.Food;
import com.balki.model.Restaurant;
import com.balki.model.User;
import com.balki.request.CreateFoodRequest;
import com.balki.service.FoodService;
import com.balki.service.RestaurantService;
import com.balki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestHeader("Authorization") String jwt,
                                                 @RequestParam String keyword) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(keyword);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestHeader("Authorization") String jwt,
                                                        @RequestParam boolean vegetarian,
                                                        @RequestParam boolean nonveg,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam(required = false) String foodCategory,
                                                        @PathVariable Long restaurantId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantsFood(restaurantId,vegetarian,nonveg,seasonal,foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
