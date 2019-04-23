package com.example.springbootexamples.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private int authorityId = 1;

    @Column(columnDefinition = "timestamp not null default current_timestamp",
            updatable = false, insertable = false)
    private LocalDateTime insertTime;

    public User(int id) {
        this.id = id;
    }
}
