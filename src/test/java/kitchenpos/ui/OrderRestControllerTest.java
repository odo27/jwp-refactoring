package kitchenpos.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kitchenpos.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class OrderRestControllerTest extends ControllerTest {

    @Test
    void 주문_생성() throws Exception {
        // given
        Order order = 주문();
        String request = objectMapper.writeValueAsString(order);
        order.setId(1L);
        given(orderService.create(any())).willReturn(order);
        String response = objectMapper.writeValueAsString(order);

        // when & then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().json(response));
    }

    @Test
    void 주문_조회() throws Exception {
        // given
        Order order1 = 주문();
        order1.setId(1L);
        Order order2 = 주문();
        order2.setId(2L);
        List<Order> orders = List.of(order1, order2);
        given(orderService.list()).willReturn(orders);
        String response = objectMapper.writeValueAsString(orders);

        // when & then
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void 주문_상태_변경() throws Exception {
        // given
        Order order = 주문();
        String request = objectMapper.writeValueAsString(order);
        order.setId(1L);
        given(orderService.changeOrderStatus(anyLong(), any())).willReturn(order);
        String response = objectMapper.writeValueAsString(order);

        // when & then
        mockMvc.perform(put("/api/orders/1/order-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }
}
