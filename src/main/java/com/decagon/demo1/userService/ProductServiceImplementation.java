package com.decagon.demo1.userService;

import com.decagon.demo1.Repository.ProductRepository;
import com.decagon.demo1.dto.ProductDto;
import com.decagon.demo1.dto.UserDto;
import com.decagon.demo1.model.Cart;
import com.decagon.demo1.model.Order;
import com.decagon.demo1.model.Product;
import com.decagon.demo1.model.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class ProductServiceImplementation {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public  Function<Product,Product> saveProduct = (product)-> productRepository.save(product);
   public  void   deleteProduct(Long id, HttpServletRequest request){
       HttpSession session= request.getSession();
       Product product = new Product();
       productRepository.deleteById(id);
   }




    public  Supplier<List<Product>> findAllProducts = ()->productRepository.findAll();
    public Function<Long, Product> findById = (id)->
            productRepository.findById(id)
                    .orElseThrow(()-> new NullPointerException("No Product is found with ID: "+ id));

    public void addProductToCart (Long id, HttpServletRequest request){
        HttpSession session = request.getSession();
        Cart cart;
        if(session.getAttribute("cart")!=null){
            cart = (Cart) session.getAttribute("cart");
            cart.setProductIds(cart.getProductIds()+" ,"+id);
            session.setAttribute("cartItems", cart.getProductIds().split(",").length);

        }
        else {
            cart = Cart. builder().productIds(id.toString())
                    .userId((Long)session.getAttribute("userID")).build();
            session.setAttribute("cart",cart);
            session.setAttribute("cartItems", cart.getProductIds());

        }
    }
    public  void checkOutCart(HttpSession session, Model model){
        Cart cart =(Cart) session.getAttribute("cart");
        List<Product> listOfProduct= new ArrayList<>();
        List<String> productIds = Arrays.stream(cart.getProductIds().split(",")).toList();
        productIds.forEach(id-> listOfProduct.add(productRepository.findById(Long.parseLong(id)).orElseThrow(()-> new NullPointerException("No such product is found "+ id))));

        final BigDecimal[] totalPrice ={new BigDecimal(0)};
        listOfProduct.forEach(product->totalPrice[0] =totalPrice[0].add(product.getPrice()));
        model.addAttribute("totalPrice","Total Price: #"+ totalPrice[0]);
        session.setAttribute("cart", null);
        Order order = Order.builder().listOfProducts(listOfProduct)
                .userid((Long)session.getAttribute("userID"))
                .totalPrice(totalPrice[0])
                .build();
        session.setAttribute("order",order);
        model.addAttribute("order",order);
    }

}
