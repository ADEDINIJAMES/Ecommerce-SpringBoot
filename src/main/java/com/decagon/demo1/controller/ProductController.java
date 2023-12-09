package com.decagon.demo1.controller;

import com.decagon.demo1.Repository.OrderRepository;
import com.decagon.demo1.Repository.ProductRepository;
import com.decagon.demo1.dto.ProductDto;
import com.decagon.demo1.dto.UserDto;
import com.decagon.demo1.model.Product;
import com.decagon.demo1.model.Users;
import com.decagon.demo1.userService.OrderServiceImpl;
import com.decagon.demo1.userService.ProductServiceImplementation;
import com.decagon.demo1.userService.UserServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.function.Supplier;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductServiceImplementation productServiceImplementation;
    private UserServiceImplementation userServiceImplementation; ;
    private OrderServiceImpl orderServiceImpl;
    @Autowired
    public ProductController(ProductServiceImplementation productServiceImplementation,  UserServiceImplementation userServiceImplementation, OrderServiceImpl orderServiceImpl){
        this.productServiceImplementation = productServiceImplementation;
        this.orderServiceImpl =orderServiceImpl;
        this.userServiceImplementation =userServiceImplementation;

    }
    @GetMapping("/all")
    public ModelAndView findAllProducts(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Product>productList = productServiceImplementation.findAllProducts.get();
        return new ModelAndView("product-dashboard")
                .addObject("products", productList)
                .addObject("cartItems", "Cart Items: "+ session.getAttribute("cartItems"));


    }


    @GetMapping("/add-cart")
    public String addToCart(@RequestParam("cart") Long id, HttpServletRequest request, Model model){
        productServiceImplementation.addProductToCart(id,request);
        return "redirect:/products/all";
    }

    @GetMapping("/payment")
    public String checkOut(HttpSession session, Model model){
        productServiceImplementation.checkOutCart(session,model);
        model.addAttribute("paid","");

        return "payment";

    }
    @GetMapping("/pay")
    public String orderPayment(HttpSession session, Model model){
        return orderServiceImpl.makePayment(session, model);
    }


    @GetMapping("/admin-view")
public ModelAndView AdminViewProducts(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Product>productList = productServiceImplementation.findAllProducts.get();
        return new ModelAndView("admin-view-product")
                .addObject("products", productList);

    }

    @GetMapping("/admin-add-prod")
    public ModelAndView AdminAddProductsPage(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        return new ModelAndView("admin-add-product")
                .addObject("products", new ProductDto());
    }


    @PostMapping("/admin-add-prod")
    public String  AdminAddProduct(@ModelAttribute ProductDto productDto){
        Product product =productServiceImplementation.saveProduct.apply(new Product(productDto));
        System.out.println("user details----> {}"+ product);
        return "redirect:/products/admin-view";
    }

    @GetMapping("/delete-product")
    public String DeleteProduct(@RequestParam Long id, HttpServletRequest request, Model model){
        request.getSession();
        productServiceImplementation.deleteProduct(id, request);
        return "redirect:/products/admin-view";
    }
    @GetMapping("/edit-product")
    public ModelAndView EditProduct(@RequestParam Long id, HttpSession session, HttpServletRequest request){
        session.getAttribute("userID");
        request.getSession();
       Product product= productServiceImplementation.findById.apply(id);
        return new ModelAndView("admin-add-product")
                .addObject("products",product);
    }




}
