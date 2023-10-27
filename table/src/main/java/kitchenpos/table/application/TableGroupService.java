package kitchenpos.table.application;

import static kitchenpos.table.exception.TableGroupExceptionType.ORDER_TABLES_CAN_NOT_LESS_THAN_TWO;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.event.TableUngroupedEvent;
import kitchenpos.table.application.dto.CreateTableGroupCommand;
import kitchenpos.table.application.dto.CreateTableGroupResponse;
import kitchenpos.table.application.dto.UngroupTableGroupCommand;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.table.domain.TableGroup;
import kitchenpos.table.domain.TableGroupRepository;
import kitchenpos.table.exception.TableGroupException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class TableGroupService {

    private final ApplicationEventPublisher publisher;
    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(
            ApplicationEventPublisher publisher,
            OrderTableRepository orderTableRepository,
            TableGroupRepository tableGroupRepository
    ) {
        this.publisher = publisher;
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    @Transactional
    public CreateTableGroupResponse create(CreateTableGroupCommand command) {
        List<OrderTable> orderTables = orderTableRepository.findAllByIdInOrElseThrow(command.orderTableIds());
        validateOrderTables(orderTables);
        TableGroup tableGroup = new TableGroup();
        orderTables.forEach(it -> it.group(tableGroup));
        return CreateTableGroupResponse.from(tableGroupRepository.save(tableGroup), orderTables);
    }

    private void validateOrderTables(List<OrderTable> orderTables) {
        if (CollectionUtils.isEmpty(orderTables) || orderTables.size() < 2) {
            throw new TableGroupException(ORDER_TABLES_CAN_NOT_LESS_THAN_TWO);
        }
    }

    @Transactional
    public void ungroup(UngroupTableGroupCommand command) {
        List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(command.tableGroupId());
        List<Long> orderTableIds = orderTables.stream()
                .map(OrderTable::id)
                .collect(Collectors.toList());
        publisher.publishEvent(new TableUngroupedEvent(orderTableIds));
        orderTables.forEach(OrderTable::ungroup);
    }
}