package kitchenpos.table.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.table.application.TableService;
import kitchenpos.table.application.dto.ChangeOrderTableEmptyResponse;
import kitchenpos.table.application.dto.ChangeOrderTableNumberOfGuestsResponse;
import kitchenpos.table.application.dto.CreateOrderTableResponse;
import kitchenpos.table.application.dto.SearchOrderTableResponse;
import kitchenpos.table.ui.dto.ChangeOrderTableEmptyRequest;
import kitchenpos.table.ui.dto.ChangeOrderTableNumberOfGuestsRequest;
import kitchenpos.table.ui.dto.CreateOrderTableRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableRestController {
    private final TableService tableService;

    public TableRestController(final TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/api/tables")
    public ResponseEntity<CreateOrderTableResponse> create(@RequestBody CreateOrderTableRequest request) {
        CreateOrderTableResponse response = tableService.create(request.toCommand());
        URI uri = URI.create("/api/tables/" + response.id());
        return ResponseEntity.created(uri)
                .body(response);
    }

    @GetMapping("/api/tables")
    public ResponseEntity<List<SearchOrderTableResponse>> list() {
        return ResponseEntity.ok()
                .body(tableService.list());
    }

    @PutMapping("/api/tables/{orderTableId}/empty")
    public ResponseEntity<ChangeOrderTableEmptyResponse> changeEmpty(
            @PathVariable Long orderTableId,
            @RequestBody ChangeOrderTableEmptyRequest request
    ) {
        return ResponseEntity.ok()
                .body(tableService.changeEmpty(request.toCommand(orderTableId)));
    }

    @PutMapping("/api/tables/{orderTableId}/number-of-guests")
    public ResponseEntity<ChangeOrderTableNumberOfGuestsResponse> changeNumberOfGuests(
            @PathVariable Long orderTableId,
            @RequestBody ChangeOrderTableNumberOfGuestsRequest request
    ) {
        return ResponseEntity.ok()
                .body(tableService.changeNumberOfGuests(request.toCommand(orderTableId)));
    }
}