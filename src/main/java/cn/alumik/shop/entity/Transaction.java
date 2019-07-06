package cn.alumik.shop.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_item")
    private Item item;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_user")
    private User buyer;

    @Column(name = "single_price")
    private BigDecimal singlePrice;

    @Column(name = "amount")
    private int amount;

    @Column(name = "sold_at")
    private Timestamp soldAt;

    @OneToOne(mappedBy = "transaction")
    private Comment comment;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private Set<RefundRequest> refundRequests;

    @Column(name = "address")
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(Timestamp soldAt) {
        this.soldAt = soldAt;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Set<RefundRequest> getRefundRequests() {
        return refundRequests;
    }

    public void setRefundRequests(Set<RefundRequest> refundRequests) {
        this.refundRequests = refundRequests;
    }

    public void addRefundRequest(RefundRequest refundRequest) {
        if (refundRequests == null) {
            refundRequests = new HashSet<>();
        }
        refundRequests.add(refundRequest);
    }

    public void removeRefundRequest(RefundRequest refundRequest) {
        if (refundRequests != null) {
            refundRequests.remove(refundRequest);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
