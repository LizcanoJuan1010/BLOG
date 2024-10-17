package com.example.app;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private Integer numberOfFriends;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User parentUser;
}
