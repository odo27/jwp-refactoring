package kitchenpos.domain.order;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kitchenpos.domain.menu.Menu;

@Entity
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private long quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(Menu menu, long quantity) {
        this(null, menu, quantity);
    }

    public OrderLineItem(Long seq, Menu menu, long quantity) {
        this.seq = seq;
        this.menu = menu;
        this.quantity = quantity;
    }

    public Long seq() {
        return seq;
    }

    public Menu menu() {
        return menu;
    }

    public long quantity() {
        return quantity;
    }
}