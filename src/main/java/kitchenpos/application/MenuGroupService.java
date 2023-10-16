package kitchenpos.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.application.dto.CreateMenuGroupCommand;
import kitchenpos.application.dto.CreateMenuGroupResponse;
import kitchenpos.application.dto.SearchMenuGroupResponse;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public CreateMenuGroupResponse create(CreateMenuGroupCommand command) {
        MenuGroup menuGroup = new MenuGroup(command.name());
        MenuGroup savedMenuGroup = menuGroupRepository.save(menuGroup);
        return CreateMenuGroupResponse.from(savedMenuGroup);
    }

    public List<SearchMenuGroupResponse> list() {
        List<MenuGroup> menuGroups = menuGroupRepository.findAll();
        return menuGroups.stream()
                .map(SearchMenuGroupResponse::from)
                .collect(Collectors.toList());
    }
}
