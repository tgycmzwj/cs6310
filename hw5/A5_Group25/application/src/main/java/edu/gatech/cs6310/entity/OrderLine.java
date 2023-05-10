package edu.gatech.cs6310.entity;


import javax.persistence.*;

@Entity
@Table(name="orderLine")
public class OrderLine {

    @EmbeddedId
    private OrderLineId orderLineId;

    @Column(name="quantity")
    private int quantity;

    @Column(name="unit_price")
    private int unitPrice;

    @Column(name="line_cost")
    private int lineCost;

    @Column(name="line_weight")
    private int lineWeight;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false),
            @JoinColumn(name = "store_name", referencedColumnName = "store_name", insertable = false, updatable = false)
    })
    private Order order;

    public OrderLine(OrderLineId orderLineId, int quantity, int unitPrice, int itemWeight) {
        this.orderLineId = orderLineId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineCost = quantity*unitPrice;
        this.lineWeight = quantity*itemWeight;
    }

    public OrderLine() {

    }

    public String displayOrderLine(){
        return "item_name:"+this.orderLineId.getItemName()+",total_quantity:"+this.quantity+",total_cost:"+this.lineCost+",total_weight:"+lineWeight;
    }


}
