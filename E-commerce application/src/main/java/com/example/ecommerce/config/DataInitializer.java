package com.example.ecommerce.config;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            initializeProducts();
        }
    }

    private void initializeProducts() {
        // Electronics
        productRepository.save(new Product(
                "iPhone 15 Pro",
                "Latest iPhone with advanced camera system and A17 Pro chip",
                new BigDecimal("134900.00"),
                25,
                "https://img.jabko.ua/image/cache/catalog/111/iph-15-pr0full.jpg",
                "Electronics"));

        productRepository.save(new Product(
                "Samsung Galaxy S24 Ultra",
                "Flagship Android smartphone with S Pen and AI features",
                new BigDecimal("129999.00"),
                30,
                "https://tse3.mm.bing.net/th/id/OIP.pJCoDVCeQLkTGOgmyiaDdAHaHa?rs=1&pid=ImgDetMain&o=7&rm=3",
                "Electronics"));

        productRepository.save(new Product(
                "MacBook Air M3",
                "Ultra-thin laptop with M3 chip and all-day battery life",
                new BigDecimal("114900.00"),
                15,
                "https://tse3.mm.bing.net/th/id/OIP.Z-NOpRATigInE9ilmwHtfwHaHa?rs=1&pid=ImgDetMain&o=7&rm=3",
                "Electronics"));

        productRepository.save(new Product(
                "Sony WH-1000XM5",
                "Premium noise-canceling wireless headphones",
                new BigDecimal("29990.00"),
                40,
                "https://fdn.gsmarena.com/imgroot/news/22/05/sony-wh-1000xm5/inline/-1200/gsmarena_001.jpg",
                "Electronics"));

        // Clothing
        productRepository.save(new Product(
                "Levi's Classic Denim Jacket",
                "Timeless denim jacket perfect for any season - Premium quality",
                new BigDecimal("4999.00"),
                50,
                "https://tse3.mm.bing.net/th/id/OIP.FUQb0t7dGbBKCHZdxfzkPgHaHa?rs=1&pid=ImgDetMain&o=7&rm=3",
                "Clothing"));

        productRepository.save(new Product(
                "Nike Cotton T-Shirt",
                "Comfortable 100% cotton t-shirt in various colors - Premium fit",
                new BigDecimal("1299.00"),
                100,
                "https://m.media-amazon.com/images/I/71VwubRGeVL._AC_UX569_.jpg",
                "Clothing"));

        productRepository.save(new Product(
                "Adidas Running Sneakers",
                "Lightweight running shoes with excellent cushioning - Boost technology",
                new BigDecimal("8999.00"),
                35,
                "https://th.bing.com/th?id=OPAC.FtezFPlRQbIc%2bw474C474&w=200&h=150&rs=1&o=5&dpr=1.5&pid=21.1",
                "Clothing"));

        // Home & Garden
        productRepository.save(new Product(
                "Philips Coffee Maker",
                "Programmable coffee maker with thermal carafe - HD7431/20",
                new BigDecimal("5999.00"),
                20,
                "https://images.philips.com/is/image/PhilipsConsumer/HD7434_20-IMS-en_IN?$jpglarge$&wid=1250",
                "Home & Garden"));

        productRepository.save(new Product(
                "Indoor Plant Combo",
                "Set of 3 low-maintenance indoor plants with decorative pots",
                new BigDecimal("2499.00"),
                25,
                "https://m.media-amazon.com/images/I/81NYex6+pcL._SL1500_.jpg",
                "Home & Garden"));

        productRepository.save(new Product(
                "Premium Cushion Cover",
                "Decorative throw pillow with premium fabric cover - Set of 2",
                new BigDecimal("899.00"),
                60,
                "https://tse1.explicit.bing.net/th/id/OIP.KLilCAycFaEjgHg2Td_sYAHaJ3?rs=1&pid=ImgDetMain&o=7&rm=3",
                "Home & Garden"));

        // Books
        productRepository.save(new Product(
                "Clean Code by Robert Martin",
                "A handbook of agile software craftsmanship - Programming best practices",
                new BigDecimal("599.00"),
                45,
                "https://m.media-amazon.com/images/I/51E2055ZGUL._SL1000_.jpg",
                "Books"));

        productRepository.save(new Product(
                "Bestseller Mystery Collection",
                "Set of 5 bestselling mystery novels by popular authors",
                new BigDecimal("1299.00"),
                30,
                "https://m.media-amazon.com/images/I/51NkHn9wjpL.jpg",
                "Books"));

        // Sports
        productRepository.save(new Product(
                "Premium Yoga Mat",
                "Non-slip yoga mat with carrying strap - 6mm thickness",
                new BigDecimal("1899.00"),
                40,
                "https://tse3.mm.bing.net/th/id/OIP.Y2cVCyaAYXnl7f7dQqx8NAHaHa?rs=1&pid=ImgDetMain&o=7&rm=3",
                "Sports"));

        productRepository.save(new Product(
                "Adjustable Dumbbell Set",
                "Professional adjustable dumbbell set for home workouts - 20kg pair",
                new BigDecimal("12999.00"),
                15,
                "https://i5.walmartimages.com/asr/e82b1965-46ee-4871-9f7c-0aea40d9e823.4cc1c109ef8cff89f0f4e9699f883feb.jpeg",
                "Sports"));

        System.out.println("Sample products initialized successfully!");
    }
}