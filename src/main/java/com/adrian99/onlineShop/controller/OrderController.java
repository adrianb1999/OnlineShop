package com.adrian99.onlineShop.controller;

import com.adrian99.onlineShop.dto.OrderDTO;
import com.adrian99.onlineShop.dto.OrderProductDTO;
import com.adrian99.onlineShop.exception.ApiRequestException;
import com.adrian99.onlineShop.model.*;
import com.adrian99.onlineShop.service.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;

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
        //TODO Needs work
        return orderService.findAllOrders();
    }

    @GetMapping("/api/order/{orderId}")
    public Map<String, Object> getOrder(@PathVariable Long orderId) {
        //TODO Needs work
        if (orderService.findById(orderId) == null)
            throw new ApiRequestException("The order with id " + orderId + " do not exists!");
        return orderService.findOrderById(orderId);
    }

    @Transactional
    @PutMapping("/api/order/{orderId}")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderInfo,
                                @PathVariable Long orderId,
                                Principal principal) {

        Order currentOrder = orderService.findById(orderId);
        Address shippingAddress;
        Address billingAddress;
        int amount = 0;

        if (orderInfo == null)
            throw new ApiRequestException("Nothing to change!");

        OrderDTO updatedOrder = new OrderDTO();

        if (currentOrder == null)
            throw new ApiRequestException("The order does not exists!");

        if(orderInfo.getShippingAddress().getId() != null) {
            shippingAddress = addressService.findById(orderInfo.getShippingAddress().getId());

            if(shippingAddress == null)
                throw new ApiRequestException("Shipping address do not exists!");

        }

        if(orderInfo.getBillingAddress().getId() != null) {
            billingAddress = addressService.findById(orderInfo.getBillingAddress().getId());

            if(billingAddress == null)
                throw new ApiRequestException("Billing address do not exists!");

        }

        if (orderInfo.getEmail() != null) {
            if (!orderInfo.getEmail().isBlank()) {
                if (!userService.isPhoneNumberValid(orderInfo.getEmail()))
                    throw new ApiRequestException("The email is not valid!");

                currentOrder.getUser().setEmail(orderInfo.getEmail());
            }
        }

        if (orderInfo.getPhoneNumber() != null) {
            if (!orderInfo.getPhoneNumber().isBlank()) {
                if (userService.isPhoneNumberValid(orderInfo.getPhoneNumber()))
                    throw new ApiRequestException("The phone number is invalid!");

                currentOrder.setPhoneNumber(orderInfo.getPhoneNumber());
            }
        }

        if (orderInfo.getOrderProductDTOList() != null) {
            Map<Long, OrderProduct> allOrdersMap = orderProductService.findAllOrderProductByOrderIdMap(orderId);

            for (OrderProductDTO order : orderInfo.getOrderProductDTOList()) {

                //If the product is present, update it!
                if (allOrdersMap.containsKey(order.getProductId())) {
                    //The difference between the order amount and the new amount
                    int difference = allOrdersMap.get(order.getProductId()).getAmount() - order.getAmount();

                    //If is the same amount
                    if (difference == 0)
                        continue;

                    //Update the product amount
                    Product currentProduct = productService.findById(order.getProductId());

                    if(currentProduct.getAmount() + difference < 0)
                        throw new ApiRequestException("Sorry but we do not have enough stock of " + currentProduct.getName());

                    Integer currentAmount = currentProduct.getAmount();
                    currentProduct.setAmount(currentAmount + difference);
                    productService.save(currentProduct);

                    //Update the product service amount
                    OrderProduct currentProductOrder = orderProductService.findById(allOrdersMap.get(order.getProductId()).getId());
                    currentProductOrder.setAmount(order.getAmount());
                    orderProductService.save(currentProductOrder);

                    amount += Integer.parseInt("" + currentProduct.getPrice()) * order.getAmount();

                    continue;
                }
                // If the product is not present, add it!
                OrderProduct currentProductOrder = new OrderProduct();

                currentProductOrder.setProduct(productService.findById(order.getProductId()));
                currentProductOrder.setOrder(currentOrder);
                currentProductOrder.setAmount(order.getAmount());

                orderProductService.save(currentProductOrder);

                amount += currentProductOrder.getAmount() * Integer.parseInt(""+ currentProductOrder.getProduct().getPrice());
            }
        }

        if (orderInfo.getOrderStatus() != null) {
            currentOrder.setOrderStatus(orderInfo.getOrderStatus());
        }

        Order order = orderService.save(currentOrder);
        updatedOrder.addUser(order.getUser());
        updatedOrder.addOrder(order);
        updatedOrder.addProducts(orderProductService.findAllOrderProductByOrderId(orderId));
        updatedOrder.setValue(BigDecimal.valueOf(amount));

        return updatedOrder;
    }


    @Transactional
    @PostMapping("/api/order")
    public OrderDTO addOrder(@RequestBody OrderDTO orderInfo, Principal principal) {

        Address shippingAddress = null;
        Address billingAddress = null;
        OrderDTO orderDTO = new OrderDTO();
        int value = 0;

        if(principal.getName() == null)
            throw new ApiRequestException("You need to be logged in!");

        User currentUser = userService.findByUsername(principal.getName());

        if(currentUser == null)
            throw new ApiRequestException("The user is invalid!");

        if(orderInfo.getShippingAddress() == null)
            throw new ApiRequestException("Shipping address needed!");

        if(orderInfo.getBillingAddress() == null)
            throw new ApiRequestException("Billing address needed!");

        if(orderInfo.getShippingAddress().getId() != null) {
            shippingAddress = addressService.findById(orderInfo.getShippingAddress().getId());

            if(shippingAddress == null)
                throw new ApiRequestException("Shipping address do not exists!");

        } else {
            shippingAddress =  addressService.addAddress(orderInfo.getShippingAddress());
        }

        if(orderInfo.getBillingAddress().getId() != null) {
            billingAddress = addressService.findById(orderInfo.getBillingAddress().getId());

            if(billingAddress == null)
                throw new ApiRequestException("Billing address do not exists!");

        } else {
            billingAddress =  addressService.addAddress(orderInfo.getBillingAddress());
        }

        //Products
        List<OrderProductDTO> productList = orderInfo.getOrderProductDTOList();
        if(productList == null || productList.isEmpty())
            throw new ApiRequestException("No products in order!");

        Order order = new Order();
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setUser(currentUser);
        order.setShippingFee(BigDecimal.valueOf(20));

        order = orderService.save(order);

        for(OrderProductDTO productDTO : orderInfo.getOrderProductDTOList()){
            //Check the stock
            Product currentProduct = productService.findById(productDTO.getProductId());
            OrderProduct orderProduct = new OrderProduct();

            if(currentProduct.getAmount() < productDTO.getAmount())
                throw new ApiRequestException("Not enough stock for product " + productDTO.getProductId());

            //Update stock
            currentProduct.setAmount(currentProduct.getAmount() - productDTO.getAmount());
            productService.save(currentProduct);

            orderProduct.setOrder(order);
            orderProduct.setProduct(currentProduct);
            orderProduct.setAmount(productDTO.getAmount());

            orderDTO.getOrderProductDTOList().add(new OrderProductDTO(orderProductService.save(orderProduct)));

            value += productDTO.getAmount() *  currentProduct.getPrice().intValue();
        }

        order.setValue( BigDecimal.valueOf(value));
        order = orderService.save(order);
        orderDTO.addUser(currentUser);
        orderDTO.addOrder(order);

        return orderDTO;
    }

}
