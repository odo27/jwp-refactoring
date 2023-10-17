package kitchenpos.application.dto.order;

import static kitchenpos.exception.OrderExceptionType.ORDER_LINE_ITEM_COMMANDS_CAN_NOT_NULL;

import java.util.List;
import java.util.Objects;
import kitchenpos.application.dto.orderlineitem.OrderLineItemCommand;
import kitchenpos.exception.OrderException;

public class CreateOrderCommand {

    private Long orderTableId;
    private List<OrderLineItemCommand> orderLineItemCommands;

    public CreateOrderCommand(Long orderTableId, List<OrderLineItemCommand> orderLineItemCommands) {
        if (Objects.isNull(orderLineItemCommands)) {
            throw new OrderException(ORDER_LINE_ITEM_COMMANDS_CAN_NOT_NULL);
        }
        this.orderTableId = orderTableId;
        this.orderLineItemCommands = orderLineItemCommands;
    }

    public Long orderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemCommand> orderLineItemCommands() {
        return orderLineItemCommands;
    }
}
