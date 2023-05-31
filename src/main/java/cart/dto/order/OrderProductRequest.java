package cart.dto.order;

import cart.domain.product.Product;

public class OrderProductRequest {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    private OrderProductRequest() {
    }

    public OrderProductRequest(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product toProduct() {
        return new Product(id, name, price, imageUrl);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
