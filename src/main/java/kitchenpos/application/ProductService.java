package kitchenpos.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.application.dto.product.CreateProductCommand;
import kitchenpos.application.dto.product.CreateProductResponse;
import kitchenpos.application.dto.product.SearchProductResponse;
import kitchenpos.domain.Price;
import kitchenpos.domain.Product;
import kitchenpos.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateProductResponse create(CreateProductCommand command) {
        Price price = new Price(command.price());
        Product product = new Product(command.name(), price);
        return CreateProductResponse.from(productRepository.save(product));
    }

    public List<SearchProductResponse> list() {
        return productRepository.findAll().stream()
                .map(SearchProductResponse::from)
                .collect(Collectors.toList());
    }
}
