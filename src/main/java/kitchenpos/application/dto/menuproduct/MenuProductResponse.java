package kitchenpos.application.dto.menuproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
import kitchenpos.application.dto.product.ProductResponse;
import kitchenpos.domain.MenuProduct;

public class MenuProductResponse {

    @JsonProperty("seq")
    private Long seq;
    @JsonProperty("menuId")
    private Long menuId;
    @JsonProperty("product")
    private ProductResponse productResponse;
    @JsonProperty("quantity")
    private long quantity;

    private MenuProductResponse(
            Long seq,
            Long menuId,
            ProductResponse productResponse,
            long quantity
    ) {
        this.seq = seq;
        this.menuId = menuId;
        this.productResponse = productResponse;
        this.quantity = quantity;
    }

    public static MenuProductResponse from(MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.seq(),
                menuProduct.menu().id(),
                ProductResponse.from(menuProduct.product()),
                menuProduct.quantity()
        );
    }

    public Long seq() {
        return seq;
    }

    public Long menuId() {
        return menuId;
    }

    public ProductResponse productResponse() {
        return productResponse;
    }

    public long quantity() {
        return quantity;
    }
}
