package com.mindhub.orderMicroservice.events;

import com.mindhub.orderMicroservice.dtos.OrderItemDTO;
import com.mindhub.orderMicroservice.dtos.UserEntityData;
import com.mindhub.orderMicroservice.models.Status;

import java.util.List;

public class OrderCreatedEvent {

    private Long id;
    private UserData user;
    private List<ProductOrderDTO> products;
    private String status;

    public OrderCreatedEvent(Long id, UserData user, List<ProductOrderDTO> products, String status) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.status = status;
    }

    public OrderCreatedEvent() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public List<ProductOrderDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderDTO> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                ", status='" + status + '\'' +
                '}';
    }

    public static class UserData {
        private Long id;
        private String username;
        private String email;
        private String roles;

        public UserData(Long id, String username, String email, String roles) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        @Override
        public String toString() {
            return "UserData{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", roles='" + roles + '\'' +
                    '}';
        }
    }

    public static class ProductOrderDTO {
        private Long id;
        private ProductData product;
        private Integer quantity;

        public ProductOrderDTO(Long id, ProductData product, Integer quantity) {
            this.id = id;
            this.product = product;
            this.quantity = quantity;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public ProductData getProduct() {
            return product;
        }

        public void setProduct(ProductData product) {
            this.product = product;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "ProductOrderDTO{" +
                    "id=" + id +
                    ", product=" + product +
                    ", quantity=" + quantity +
                    '}';
        }

        public static class ProductData {
            private int id;
            private String name;
            private String description;
            private Double price;

            public ProductData(int id, String name, String description, Double price) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.price = price;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Double getPrice() {
                return price;
            }

            public void setPrice(Double price) {
                this.price = price;
            }

            @Override
            public String toString() {
                return "ProductData{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        ", price=" + price +
                        '}';
            }
        }
    }
}