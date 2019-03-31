package com.piotrek.gamecalendar.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.piotrek.gamecalendar.common.DateAudit;
import com.piotrek.gamecalendar.role.Role;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 15)
    private String username;

    @Lob
    @NotBlank
    @JsonIgnore
    private String password;

    @Email
    @NotBlank
    @NaturalId
    @Size(min = 6, max = 40)
    private String email;

    private String imageUrl;

    private Boolean emailVerified;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
