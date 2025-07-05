package com.smartorders.productservice;

import com.github.javafaker.Faker;
import com.smartorders.productservice.model.*;
import com.smartorders.productservice.repository.BrandRepository;
import com.smartorders.productservice.repository.CategoryRepository;
import com.smartorders.productservice.repository.ProductRepository;
import com.smartorders.productservice.repository.TagRepository;
import com.smartorders.productservice.util.SkuGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
public class ProductServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}


	private final BrandRepository brandRepository;
	private final CategoryRepository categoryRepository;
	private final TagRepository tagRepository;
	private final ProductRepository productRepository;


	@Override
	public void run(String... args) throws Exception {
		// Save Brand
		Brand brand = Brand.builder()
				.brandName("Cool Brand")
				.brandCode("BRND001")
				.description("This is a default brand for testing purposes.")
				.logoUrl("https://example.com/default-brand-image.jpg")
				.isActive(true)
				.websiteUrl("https://example.com")
				.build();
		brand = brandRepository.save(brand);

		// Save Category
		Category category = Category.builder()
				.categoryName("Cool Category")
				.description("This is a default category for testing purposes.")
				.isActive(true)
				.build();
		category = categoryRepository.save(category);

		// Save Tags
		Tag tag1 = Tag.builder()
				.tagName("Cool Tag 1")
				.description("This is a default tag for testing purposes.")
				.isActive(true)
				.build();

		Tag tag2 = Tag.builder()
				.tagName("Cool Tag 2")
				.description("This is a default tag for testing purposes.")
				.isActive(true)
				.build();

		tag1 = tagRepository.save(tag1);
		tag2 = tagRepository.save(tag2);

		Faker faker = new Faker(Locale.ENGLISH);

		for (int i = 0; i < 20; i++) {
			// Create Product without relationships first
			
			Product product = Product.builder()
					.title(faker.commerce().productName())
					.description(faker.lorem().paragraph())
					.basePrice(BigDecimal.valueOf(99.99))
					.salePrice(BigDecimal.valueOf(89.99))
					.productStatus(Status.ACTIVE)
					.build();

			// Generate SKU
			product.setSku(SkuGenerator.generateSku(product.getTitle(), category.getCategoryName()));

			// Set relationships
			product.setBrand(brand);
			product.setCategory(category);

			Set<Tag> tags = new HashSet<>();
			tags.add(tag1);
			tags.add(tag2);

			product.setTags(tags);

			// Save product first to get ID
			product = productRepository.save(product);

			// Create and save ProductImages
			ProductImage productImage = ProductImage.builder()
					.imageUrl("https://example.com/default-product-image.jpg")
					.thumbnailUrl("https://example.com/default-product-thumbnail.jpg")
					.altText("Default Product Image")
					.caption("This is a default product image for testing purposes.")
					.isPrimary(true)
					.product(product)
					.build();

			// Create and save ProductAttributes
			ProductAttribute productAttribute = ProductAttribute.builder()
					.name("Color")
					.value("Red")
					.type("color")
					.unit("N/A")
					.product(product)
					.build();

			// Set collections
			product.setProductImages(List.of(productImage));
			product.setProductAttributes(List.of(productAttribute));

			// Final save
			productRepository.save(product);

		}


	}
}