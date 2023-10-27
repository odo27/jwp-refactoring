package kitchenpos.ui.menu;

import java.net.URI;
import java.util.List;
import kitchenpos.application.menu.ProductService;
import kitchenpos.application.menu.dto.CreateProductResponse;
import kitchenpos.application.menu.dto.SearchProductResponse;
import kitchenpos.ui.menu.dto.CreateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ResponseEntity<CreateProductResponse> create(@RequestBody final CreateProductRequest request) {
        CreateProductResponse response = productService.create(request.toCommand());
        URI uri = URI.create("/api/products/" + response.id());
        return ResponseEntity.created(uri)
                .body(response);
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<SearchProductResponse>> list() {
        return ResponseEntity.ok()
                .body(productService.list());
    }
}