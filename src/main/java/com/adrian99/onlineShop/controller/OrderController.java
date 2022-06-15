package com.adrian99.onlineShop.controller;

import com.adrian99.onlineShop.exception.ApiRequestException;
import com.adrian99.onlineShop.model.*;
import com.adrian99.onlineShop.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class OrderController {

    private final OrderProductService orderProductService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final AddressService addressService;

    public OrderController(OrderProductService orderProductService, UserService userService, ProductService productService, OrderService orderService, AddressService addressService) {
        this.orderProductService = orderProductService;
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
        this.addressService = addressService;
    }

    @GetMapping("/api/orders")
    public List<Map<String, Object>> allOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/api/order/{orderId}")
    public Map<String, Object> getOrder(@PathVariable Long orderId) {
        if(orderService.findById(orderId) == null)
            throw new ApiRequestException("The order with id " + orderId + " do not exists!");
        return orderService.findOrderById(orderId);
    }

    @PutMapping("/api/order/{orderId}")
    public Map<String, Object> updateOrder(@RequestBody Map<String, Object> orderInfo,
                                           @PathVariable Long orderId,
                                           Principal principal) {

        Order currentOrder = orderService.findById(orderId);

        if (currentOrder == null)
            throw new ApiRequestException("The order does not exists!");


        if (orderInfo.containsKey("billingAddressId")) {
            String billingAddressId = (String) orderInfo.get("billingAddressId");
            if (billingAddressId != null && !billingAddressId.isBlank()) {
                Address currentAddress = addressService.findById(Long.valueOf(billingAddressId));
                if (currentAddress != null) {
                    currentOrder.setBillingAddress(currentAddress);
                }
            }
        } else if (orderInfo.containsKey("billingAddress") &&
                orderInfo.containsKey("billingAddressFullName") &&
                orderInfo.containsKey("billingAddressPhoneNumber")) {
            String billingAddress = (String) orderInfo.get("billingAddress");
//                String fullName = (String) orderInfo.get("billingAddressFullName");
//                String phoneNumber = (String) orderInfo.get("billingAddressPhoneNumber");
//                if (!billingAddress.isBlank() && !fullName.isBlank() && !phoneNumber.isBlank()) {
//                    currentOrder.setBillingAddress(new Address(billingAddress,phoneNumber,fullName));
//                }
            //TODO VEZI SUS
        }

        if (orderInfo.containsKey("shippingAddressId")) {
            String shippingAddressId = (String) orderInfo.get("shippingAddressId");
            if (shippingAddressId != null && !shippingAddressId.isBlank()) {
                Address currentAddress = addressService.findById(Long.valueOf(shippingAddressId));
                if (currentAddress != null) {
                    currentOrder.setShippingAddress(currentAddress);
                }
            }
        } else if (orderInfo.containsKey("shippingAddress") &&
                orderInfo.containsKey("shippingAddressFullName") &&
                orderInfo.containsKey("shippingAddressPhoneNumber")) {
            String shippingAddress = (String) orderInfo.get("shippingAddress");
//                String fullName = (String) orderInfo.get("billingAddressFullName");
//                String phoneNumber = (String) orderInfo.get("billingAddressPhoneNumber");
//                if (!shippingAddress.isBlank() && !fullName.isBlank() && !phoneNumber.isBlank()) {
//                    //currentOrder.setBillingAddress(new Address(shippingAddress));
//                }
            //TODO SE EDITEAZA ABSOLUT TOT, TAIE FRATE
        }


        if (orderInfo.containsKey("email")) {
            String email = (String) orderInfo.get("email");
            if (!email.isBlank()) {
                if (!userService.isPhoneNumberValid(email))
                    throw new ApiRequestException("The email is not valid!");
                currentOrder.getUser().setEmail(email);
            }
        }

        if (orderInfo.containsKey("products")) {
            List<Map<String, Object>> products = (List<Map<String, Object>>) orderInfo.get("products");
            List<OrderProduct> orderProductList = orderProductService.findAllOrderProductByOrderId(orderId);

            if (products != null && !products.isEmpty()) {
                for (Map<String, Object> product : products) {
                    if (product.containsKey("productId") && product.containsKey("amount")) {
                        String productId = (String) product.get("productId");
                        String amount = (String) product.get("amount");

                        if (!productId.isBlank()) {
                            Long id;
                            try {
                                id = Long.valueOf(productId);
                            } catch (NumberFormatException e) {
                                throw new ApiRequestException("Invalid product!");
                            }

                            Product currentProduct = productService.findById(id);

                            if (currentProduct != null) {
                                int productAmount;
                                try {
                                    productAmount = Integer.parseInt(amount);
                                } catch (NumberFormatException e) {
                                    throw new ApiRequestException("Invalid amount!");
                                }
                                int counter = 0;
                                for (OrderProduct currentOrderProduct : orderProductList) {
                                    if (Objects.equals(currentOrderProduct.getProduct().getId(), currentProduct.getId())) {
                                        counter++;
                                        int difference = currentOrderProduct.getAmount() - currentProduct.getAmount();
//                                        if(difference > 0){
//
//                                        }
                                        //TODO VEZI CA AICI SUS E CAM DIFICIL, CA CE E IN FOR E VARIABILA DOAR IN SCOPE SI TREBUIGE SA ITI DAI SEAMA CUM SA LE SI MEMOREZI TOTODATA
                                    }
                                }

                                //TODO CHECK AND UPDATE ALL PRODUCTS
                            }
                        }
                    }
                }
            }
        }

        if (orderInfo.containsKey("status")){
           currentOrder.setOrderStatus(OrderStatus.valueOf((String) orderInfo.get("status")));
        }

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> currentMap = objectMapper.convertValue(orderService.save(currentOrder), Map.class);

        return currentMap;
    }

    @PostMapping("/api/order")
    public Map<String, Object> addOrder(@RequestBody Map<String, Object> orderInfo, Principal principal) {

        double value = 0D;
        Order order = new Order();

        Address shippingAddress = null;
        Address billingAddress = null;
        Map<String, Object> shippingAddressInfo;
        Map<String, Object> billingAddressInfo;

        String email = null;

        long productId = 0L;
        Integer amount = 0;
        User user = null;

        if (orderInfo.containsKey("billingAddress")) {

            billingAddressInfo = (Map<String, Object>) orderInfo.get("billingAddress");

            String billingAddressString = (String) orderInfo.get("id");

            if (billingAddressString != null && !billingAddressString.isBlank()) {
                billingAddress = addressService.findById((long) orderInfo.get("billingAddressId"));

                if (billingAddress == null)
                    throw new ApiRequestException("Shipping address do not exists");

            } else {
                String fullName = (String) billingAddressInfo.get("fullName");
                String phoneNumber = (String) billingAddressInfo.get("phoneNumber");
                String fullAddress = (String) billingAddressInfo.get("fullAddress");
                if (!userService.isPhoneNumberValid(phoneNumber))
                    throw new ApiRequestException("Billing address phone number is invalid!");

                if (fullName.isBlank())
                    throw new ApiRequestException("Billing address name cannot be empty!");

                billingAddress = addressService.save(new Address(
                        fullAddress,
                        phoneNumber,
                        fullName,
                        null));
            }
        } else
            throw new ApiRequestException("Billing address cannot be empty");

        //TODO FA O FUNCTIE DIN CELE 2 IF-URI CA NU E FRUMOS SA AI COD REPETITIV
        if (orderInfo.containsKey("shippingAddress")) {

            shippingAddressInfo = (Map<String, Object>) orderInfo.get("billingAddress");

            String shippingAddressString = (String) shippingAddressInfo.get("id");

            if (shippingAddressString != null && !shippingAddressString.isBlank()) {
                shippingAddress = addressService.findById((long) orderInfo.get("billingAddressId"));
                if (shippingAddress == null)
                    throw new ApiRequestException("Shipping address do not exists");
            } else {
                String fullName = (String) shippingAddressInfo.get("fullName");
                String phoneNumber = (String) shippingAddressInfo.get("phoneNumber");
                String fullAddress = (String) shippingAddressInfo.get("fullAddress");

                if (!userService.isPhoneNumberValid(phoneNumber))
                    throw new ApiRequestException("Shipping address phone number is invalid!");

                if (fullName.isBlank())
                    throw new ApiRequestException("Shipping address name cannot be empty!");

                shippingAddress = addressService.save(new Address(fullAddress,
                        phoneNumber,
                        fullName,
                        null));
            }
        } else
            throw new ApiRequestException("Shipping address cannot be empty");

        if (principal != null) {
            user = userService.findByUsername(principal.getName());

            if (user == null)
                throw new ApiRequestException("User is invalid!");

        } else {
            user = new User();
            user.setActive(true);

            if (!orderInfo.containsKey("email"))
                throw new ApiRequestException("The email cannot be null!");

            email = (String) orderInfo.get("email");

            if (!email.isBlank() && !userService.isEmailValid(email))
                throw new ApiRequestException("Email is invalid!");

            user.setEmail(email);
            user.setPassword(userService.passwordGenerator(10));
            user = userService.save(user);
            //TODO SEND MAIL with credentials
        }
        shippingAddress.setUser(user);
        billingAddress.setUser(user);

        shippingAddress = addressService.save(shippingAddress);
        billingAddress = addressService.save(billingAddress);

        order.setUser(user);
        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);
        order.setShippingFee(BigDecimal.valueOf(10));

        Order currentOrder = orderService.save(order);

        List<Map<String, Integer>> products = (List<Map<String, Integer>>) orderInfo.get("products");

        for (var productInfo : products) {
            productId = productInfo.get("productId");
            amount = productInfo.get("amount");

            Product currentProduct = productService.findById(productId);

            if (currentProduct.getAmount() < amount) {
                throw new ApiRequestException("Not enough stock!");
            }

            value += amount * currentProduct.getPrice().doubleValue();
            currentProduct.setAmount(currentProduct.getAmount() - amount);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(currentOrder);
            orderProduct.setProduct(currentProduct);
            orderProduct.setAmount(amount);

            productService.save(currentProduct);
            orderProductService.save(orderProduct);
        }

        currentOrder.setValue(BigDecimal.valueOf(value));

        orderService.save(currentOrder);

        //TODO FIX THE RETURN
        return null;
    }
}
