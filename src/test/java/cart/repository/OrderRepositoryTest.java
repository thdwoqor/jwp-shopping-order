package cart.repository;

import cart.dao.OrderItemDao;
import cart.domain.order.Order;
import cart.domain.order.OrderItem;
import cart.domain.product.Product;
import cart.entity.OrderEntity;
import cart.entity.OrderItemEntity;
import cart.integration.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

class OrderRepositoryTest extends IntegrationTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemDao orderItemDao;

    @Test
    void create() {
        Product product = new Product(1L, "치킨", 10000, "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80");
        OrderItem orderItem = new OrderItem(product, 10, Collections.emptyList());
        Long orderId = orderRepository.create(List.of(orderItem), new OrderEntity(1L, 1L, 3000));

        List<OrderItemEntity> orderItemEntities = orderItemDao.findAllByOrderId(orderId);
        OrderItemEntity orderItemEntity = orderItemEntities.get(0);

        assertAll(
                () -> Assertions.assertThat(orderItemEntities).hasSize(1),
                () -> Assertions.assertThat(orderItemEntity.getProduct()).isEqualTo(product)
        );
    }

    @Test
    void findAllByMemberId() {
        Product product = new Product(1L, "치킨", 10000, "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80");
        OrderItem orderItem = new OrderItem(product, 10, Collections.emptyList());
        orderRepository.create(List.of(orderItem), new OrderEntity(1L, 1L, 3000));
        List<Order> orders = orderRepository.findAllByMemberId(1L);
        Assertions.assertThat(orders.get(0).getOrderItems().get(0).getProduct()).isEqualTo(product);
    }
}
