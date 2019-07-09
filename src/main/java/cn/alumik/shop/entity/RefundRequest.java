package cn.alumik.shop.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "refund_request")
public class RefundRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_transaction")
    private Transaction transaction;

    @Column(name = "reason")
    private String reason;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToOne(mappedBy = "refundRequest", cascade = CascadeType.ALL)
    private Refund refund;

    @Column(name = "dealt")
    private Boolean dealt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public Boolean getDealt() {
        return dealt;
    }

    public void setDealt(Boolean dealt) {
        this.dealt = dealt;
    }
}
