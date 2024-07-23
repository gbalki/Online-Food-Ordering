package com.balki.service.imp;

import com.balki.model.Cart;
import com.balki.model.CartItem;
import com.balki.model.Food;
import com.balki.model.User;
import com.balki.repository.CartItemRepository;
import com.balki.repository.CartRepository;
import com.balki.request.AddCartItemRequest;
import com.balki.service.CartService;
import com.balki.service.FoodService;
import com.balki.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    CartRepository cartRepository;

    CartItemRepository cartItemRepository;

    UserService userService;

    FoodService foodService;

    public CartServiceImp(CartRepository cartRepository, CartItemRepository cartItemRepository, UserService userService, FoodService foodService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.foodService = foodService;
    }

    @Override
    public CartItem addItemToCart(AddCartItemRequest reg, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.findFoodById(reg.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + reg.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(reg.getQuantity());
        newCartItem.setIngredients(reg.getIngredients());
        newCartItem.setTotalPrice(reg.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);

        if (cartItemOpt.isEmpty()) {
            throw new Exception("Cart item not found");
        }

        CartItem cartItem = cartItemOpt.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);

        if (cartItemOpt.isEmpty()) {
            throw new Exception("Cart item not found");
        }

        CartItem cartItem = cartItemOpt.get();

        cart.getItems().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;

        for (CartItem cartItem : cart.getItems()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);

        if (optionalCart.isEmpty()){
            throw new Exception("Cart not found with id:"+id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart =findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
