package com.kieranmangan.auth.db;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorities")
@IdClass(AuthorityKey.class)
public class Authority {

    @Id
    private String username;
    @Id
    private String authority;

}
