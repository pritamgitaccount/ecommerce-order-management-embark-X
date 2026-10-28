package ecom_application.service;

import ecom_application.dto.OrderItemDto;
import ecom_application.dto.OrderResponse;
import ecom_application.entity.CartItem;
import ecom_application.entity.Order;
import ecom_application.entity.OrderItem;
import ecom_application.entity.User;
import ecom_application.enums.OrderStatus;
import ecom_application.repository.OrderRepository;
import ecom_application.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        //validate from cart items
        List<CartItem> cartItems = cartService.getCartItems(Long.valueOf(userId));
        if (cartItems.isEmpty()) {
            return empty();
        }
        //validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            return empty();
        }
        User user = userOptional.get();

        //Calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice().divide(
                                BigDecimal.valueOf(item.getQuantity()),
                                item.getPrice().scale(),
                                RoundingMode.HALF_UP
                        ),
                        order
                )).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        //Clear the cart
        cartService.clearCart(userId);
        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()
                        .map(
                                item -> new OrderItemDto(
                                        item.getId(),
                                        item.getProduct().getId(),
                                        item.getQuantity(),
                                        item.getPrice(),
                                        item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                                )).toList(),
                savedOrder.getCreatedAt()
        );
    }
}
