package com.example.paymenttest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wish_list")
@Data @AllArgsConstructor @NoArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_date")
    private Date createdDate;

/*
    @ManyToOne(cascade = CascadeType.MERGE)
*/
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    public Wishlist(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate = new Date();
    }
}
