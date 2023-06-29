package com.hse.marketplace;

import com.hse.marketplace.model.dao.entity.Product;
import com.hse.marketplace.model.dao.repository.ProductRepository;
import com.hse.marketplace.model.dto.request.CreateProductDto;
import com.hse.marketplace.model.dto.request.UpdateProductDto;
import com.hse.marketplace.model.dto.response.NewProductResponseDto;
import com.hse.marketplace.model.dto.response.ProductDto;
import com.hse.marketplace.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest extends BaseTest {

    private final ProductService productService;

    private final ProductRepository productRepository;

    @Autowired
    ProductServiceTest(MockMvc mockMvc, ProductService productService, ProductRepository productRepository) {
        super(mockMvc);
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Test
    @DisplayName("Создание нового продукта")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clear.sql")
    public void createProductTest() {
        CreateProductDto testRequest = CreateProductDto.builder()
                .name("Игрушка Медвежонок")
                .description("Мягкая плюшевая игрушка Медвежонок, розового цвета")
                .price(new BigDecimal("800"))
                .availableQuantity(300)
                .build();

        NewProductResponseDto result = productService.createProduct(testRequest);
        assertTrue(result.isSuccess());
        assertNotNull(result.getId());
        assertEquals("Игрушка Медвежонок", result.getName());
        assertEquals("Мягкая плюшевая игрушка Медвежонок, розового цвета", result.getDescription());
        assertEquals(new BigDecimal(800), result.getPrice());
        assertEquals(300, result.getAvailableQuantity());

        Optional<Product> product = productRepository.findById(result.getId());
        assertTrue(product.isPresent());
        assertEquals("Игрушка Медвежонок", product.get().getName());
        assertEquals("Мягкая плюшевая игрушка Медвежонок, розового цвета", product.get().getDescription());
        assertEquals(0, product.get().getPrice().compareTo(new BigDecimal(800)));
        assertEquals(300, product.get().getAvailableQuantity());
        assertEquals(0, product.get().getReviews().size());
    }

    @Test
    @DisplayName("Изменение информации о продукте")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/add_products_with_reviews.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clear.sql")
    })
    public void changeProductInfoTest() {
        Optional<Product> oldProduct = productRepository.findById(1L);
        assertTrue(oldProduct.isPresent());
        assertEquals("Сок апельсиновый, 0.75 л", oldProduct.get().getName());
        assertEquals(50, oldProduct.get().getAvailableQuantity());

        UpdateProductDto request = UpdateProductDto.builder()
                .id(oldProduct.get().getId())
                .name("Апельсиновый сок")
                .availableQuantity(33)
                .build();
        NewProductResponseDto response = productService.updateProduct(request);
        assertTrue(response.isSuccess());
        assertEquals(33, response.getAvailableQuantity());

        Optional<Product> newProduct = productRepository.findById(1L);
        assertTrue(newProduct.isPresent());
        assertEquals("Апельсиновый сок", newProduct.get().getName());
        assertEquals(33, newProduct.get().getAvailableQuantity());
    }

    @Test
    @DisplayName("Получение информации о продукте")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/add_products_with_reviews.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clear.sql")
    })
    public void getProductInfoTest() {
        ProductDto firstProduct = productService.getProductWithReviews(1L);
        assertTrue(firstProduct.isSuccess());
        assertEquals("Сок апельсиновый, 0.75 л", firstProduct.getName());
        assertEquals("100% натуральный сок, без сахара и консервантов", firstProduct.getDescription());
        assertEquals(50, firstProduct.getAvailableQuantity());
        assertEquals(0, firstProduct.getPrice().compareTo(new BigDecimal("99.9")));
        assertTrue(firstProduct.getReviews().isEmpty());
        assertNull(firstProduct.getAverageRating());

        ProductDto secondProduct = productService.getProductWithReviews(2L);
        assertTrue(secondProduct.isSuccess());
        assertEquals("Шоколад Milka", secondProduct.getName());
        assertNull(secondProduct.getDescription());
        assertEquals(19, secondProduct.getAvailableQuantity());
        assertEquals(0, secondProduct.getPrice().compareTo(new BigDecimal("85")));
        assertEquals(2, secondProduct.getReviews().size());
        assertEquals(0, secondProduct.getAverageRating().compareTo(new BigDecimal("4.5")));

        ProductDto missingProduct = productService.getProductWithReviews(123L);
        assertFalse(missingProduct.isSuccess());
        assertEquals("Product with id 123 not found", missingProduct.getMessage());
        assertNull(missingProduct.getName());
    }

    @Test
    @DisplayName("Удаление продукта")
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/add_products_with_reviews.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/clear.sql")
    })
    public void deleteProductTest() {
        Optional<Product> firstProduct = productRepository.findById(1L);
        assertTrue(firstProduct.isPresent());
        assertEquals("Сок апельсиновый, 0.75 л", firstProduct.get().getName());

        Boolean result = productService.deleteProduct(1L);
        assertTrue(result);

        Optional<Product> firstProductAfterDeleting = productRepository.findById(1L);
        assertTrue(firstProductAfterDeleting.isEmpty());
    }
}
