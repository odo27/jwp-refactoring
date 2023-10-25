package kitchenpos.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import kitchenpos.application.menu.MenuGroupService;
import kitchenpos.application.menu.MenuService;
import kitchenpos.application.menu.ProductService;
import kitchenpos.application.menu.dto.CreateMenuResponse;
import kitchenpos.application.menu.dto.SearchMenuResponse;
import kitchenpos.application.order.OrderService;
import kitchenpos.application.table.TableGroupService;
import kitchenpos.application.table.TableService;
import kitchenpos.domain.menu.Menu;
import kitchenpos.domain.menu.MenuGroup;
import kitchenpos.domain.menu.MenuProduct;
import kitchenpos.domain.menu.MenuProducts;
import kitchenpos.domain.menu.Price;
import kitchenpos.domain.menu.Product;
import kitchenpos.domain.order.Order;
import kitchenpos.domain.order.OrderLineItem;
import kitchenpos.domain.order.OrderLineItems;
import kitchenpos.domain.order.OrderStatus;
import kitchenpos.domain.table.OrderTable;
import kitchenpos.domain.table.TableGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class ControllerTest {

    @MockBean
    protected MenuGroupService menuGroupService;

    @MockBean
    protected MenuService menuService;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected TableGroupService tableGroupService;

    @MockBean
    protected TableService tableService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected CreateMenuResponse 메뉴_응답() {
        MenuGroup menuGroup = new MenuGroup(1L, "메뉴그룹");
        Product product = new Product(1L, "상품", new Price(BigDecimal.ONE));
        MenuProduct menuProduct = new MenuProduct(1L, product, 3);
        Menu menu = new Menu(1L, "메뉴", new Price(BigDecimal.ONE), menuGroup, new MenuProducts(List.of(menuProduct)));
        return CreateMenuResponse.from(menu);
    }

    protected SearchMenuResponse 메뉴_조회_응답() {
        MenuGroup menuGroup = new MenuGroup(1L, "메뉴그룹");
        Product product = new Product(1L, "상품", new Price(BigDecimal.ONE));
        MenuProduct menuProduct = new MenuProduct(1L, product, 3);
        Menu menu = new Menu(1L, "메뉴", new Price(BigDecimal.ONE), menuGroup, new MenuProducts(List.of(menuProduct)));
        return SearchMenuResponse.from(menu);
    }

    protected Order 식사중인_주문(Long id) {
        OrderLineItem orderLineItem = new OrderLineItem(1L, 1);
        return new Order(
                id,
                1L,
                OrderStatus.MEAL,
                null,
                new OrderLineItems(List.of(orderLineItem))
        );
    }

    protected TableGroup 테이블_그룹(Long id) {
        return new TableGroup(id, null);
    }

    protected OrderTable 주문_테이블(Long id) {
        return new OrderTable(id, null, 0, true);
    }
}
