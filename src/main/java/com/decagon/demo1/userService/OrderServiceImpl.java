package com.decagon.demo1.userService;

import com.decagon.demo1.Repository.OrderRepository;
import com.decagon.demo1.Repository.UserRepository;
import com.decagon.demo1.model.Order;
import com.decagon.demo1.model.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.function.Function;

@Service
public class OrderServiceImpl {
    private OrderRepository orderRepository;
    private UserServiceImplementation userServiceImplementation;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserServiceImplementation userServiceImplementation){
        this.orderRepository = orderRepository;
        this.userServiceImplementation= userServiceImplementation;
    }
public Function<Order, Order> saveOrder=(order -> orderRepository.save(order));
    public String makePayment(HttpSession session, Model model){
        Users users =userServiceImplementation.findUserById.apply((Long) session.getAttribute("userID"));
        Order order= (Order)session.getAttribute("order");
        if(users.getBalance().doubleValue()<order.getTotalPrice().doubleValue()){
            model.addAttribute("paid", "Insufficient Fund, Load more!!");
            return "checkout";
        }
        users.setBalance(users.getBalance().subtract(order.getTotalPrice()));
        userServiceImplementation.saveUser.apply(users);
        Order order1= saveOrder.apply(order);
        session.setAttribute("order", null);
        model.addAttribute("paid", "Payment was successful!");
        return "payment-successful";
    }
}
