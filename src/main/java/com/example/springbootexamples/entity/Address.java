package com.example.springbootexamples.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String detail;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(columnDefinition = "timestamp not null default current_timestamp",
            updatable = false, insertable = false)
    private LocalDateTime insertTime;
}
