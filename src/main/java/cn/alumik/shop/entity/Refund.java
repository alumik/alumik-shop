package cn.alumik.shop.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "refund")
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_refund_request")
    private RefundRequest refundRequest;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "refunded_at")
    private Timestamp refundedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RefundRequest getRefundRequest() {
        return refundRequest;
    }

    public void setRefundRequest(RefundRequest refundRequest) {
        this.refundRequest = refundRequest;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getRefundedAt() {
        return refundedAt;
    }

    public void setRefundedAt(Timestamp refundedAt) {
        this.refundedAt = refundedAt;
    }
}
