package com.kieranmangan.auth.db;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(columnDefinition = "varchar(50)")
    private UUID id;
    @Column(columnDefinition = "varchar(36)") // TODO - This is needed to stop hibernate complaining. Why?
    private String username;
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="username", cascade = CascadeType.ALL)
    private List<Authority> authorities;

}
