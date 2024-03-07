package com.desafio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Transfer")
@Getter
@Setter
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    User from;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    User to;

    @Column(name = "value")
    Double value;

}
