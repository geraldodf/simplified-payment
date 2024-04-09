package com.simplifiedpayment.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "transfer")
@Getter
@Setter
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_from_fk")
    User from;

    @ManyToOne
    @JoinColumn(name = "user_to_fk")
    User to;

    @Column(name = "transfer_value")
    Double value;

}
