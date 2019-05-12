package com.piotrek.gamecalendar.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.security.AuthProvider;
import com.piotrek.gamecalendar.user.dto.UserProfile;
import com.piotrek.gamecalendar.util.DateAudit;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private String firstName;

    private String lastName;

    @NotBlank
    @Size(min = 2, max = 15)
    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @Lob
    @NotBlank
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Email
    @NotBlank
    @NaturalId
    @Size(min = 6, max = 40)
    @Column(nullable = false, unique = true)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String providerId;

    public UserProfile toUserProfile() {
        return UserProfile.builder()
                .id(id)
                .username(username)
                .email(email)
                .imageUrl(imageUrl).build();
    }
}