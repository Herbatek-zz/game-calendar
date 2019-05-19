package com.piotrek.gamecalendar.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.piotrek.gamecalendar.achievement.Achievement;
import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.security.oauth2.providers.AuthProvider;
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
    @JsonIgnore
    private String password;

    @Email
    @NotBlank
    @NaturalId
    @Size(min = 6, max = 40)
    @Column(nullable = false, unique = true)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "user_achievement",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id"))
    private Set<Achievement> achievements = new HashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String providerId;

    public UserProfile toUserProfile() {
        return UserProfile.builder()
                .id(id)
                .username(username)
                .email(email)
                .imageUrl(imageUrl)
                .achievements(achievements).build();
    }
}