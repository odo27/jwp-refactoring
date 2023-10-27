package kitchenpos.ui.table;

import java.net.URI;
import kitchenpos.application.table.TableGroupService;
import kitchenpos.application.table.dto.CreateTableGroupResponse;
import kitchenpos.application.table.dto.UngroupTableGroupCommand;
import kitchenpos.ui.table.dto.CreateTableGroupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableGroupRestController {
    private final TableGroupService tableGroupService;

    public TableGroupRestController(final TableGroupService tableGroupService) {
        this.tableGroupService = tableGroupService;
    }

    @PostMapping("/api/table-groups")
    public ResponseEntity<CreateTableGroupResponse> create(@RequestBody CreateTableGroupRequest request) {
        CreateTableGroupResponse response = tableGroupService.create(request.toCommand());
        URI uri = URI.create("/api/table-groups/" + response.id());
        return ResponseEntity.created(uri)
                .body(response);
    }

    @DeleteMapping("/api/table-groups/{tableGroupId}")
    public ResponseEntity<Void> ungroup(@PathVariable Long tableGroupId) {
        tableGroupService.ungroup(new UngroupTableGroupCommand(tableGroupId));
        return ResponseEntity.noContent()
                .build();
    }
}