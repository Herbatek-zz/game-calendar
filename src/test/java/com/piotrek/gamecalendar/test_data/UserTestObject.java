package com.piotrek.gamecalendar.test_data;

import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import com.piotrek.gamecalendar.role.RoleRepository;
import com.piotrek.gamecalendar.security.oauth2.providers.AuthProvider;
import com.piotrek.gamecalendar.user.User;
import lombok.Getter;

import java.util.Set;

public class UserTestObject {

    @Getter
    private User user;

    public static final String PIOTREK_USERNAME = "kenshin985";

    private UserTestObject() {
        user = new User();
    }

    public static UserTestObject builder() {
        return new UserTestObject();
    }

    public UserTestObject piotrek() {
        user.setUsername(PIOTREK_USERNAME);
        user.setImageUrl("http://bit.do/eSaxa");
        return this;
    }

    public UserTestObject withEmailVerified() {
        user.setEmailVerified(true);
        return this;
    }

    public UserTestObject withEmailNotVerified() {
        user.setEmailVerified(false);
        return this;
    }

    public UserTestObject withFacebookLogged() {
        user.setAuthProvider(AuthProvider.facebook);
        return this;
    }

    public UserTestObject withLocalLogged() {
        user.setAuthProvider(AuthProvider.local);
        return this;
    }

    public UserTestObject withGoogleLogged() {
        user.setAuthProvider(AuthProvider.google);
        return this;
    }

    public UserTestObject withRoleUser() {
        user.setRoles(Set.of(Role.builder().name(RoleName.ROLE_USER).build()));
        return this;
    }

    public UserTestObject withRoleUser(RoleRepository roleRepository) {
        Role userRole = Role.builder().name(RoleName.ROLE_USER).build();
        user.setRoles(Set.of(roleRepository.save(userRole)));
        return this;
    }

    public UserTestObject withRoleUserAndAdmin() {
        user.setRoles(Set.of(
                Role.builder().name(RoleName.ROLE_USER).build(),
                Role.builder().name(RoleName.ROLE_ADMIN).build()));
        return this;
    }

    public UserTestObject withRoleUserAndAdmin(RoleRepository roleRepository) {
        Role roleUser = Role.builder().name(RoleName.ROLE_USER).build();
        Role roleAdmin = Role.builder().name(RoleName.ROLE_ADMIN).build();
        user.setRoles(Set.of(
                roleRepository.save(roleUser),
                roleRepository.save(roleAdmin)));
        return this;
    }

    public UserTestObject withId(long id) {
        user.setId(id);
        return this;
    }

    public UserTestObject withCorrectEmail() {
        switch (user.getUsername()) {
            case "kenshin985":
                user.setEmail("kenshin985@email.com");
                break;
        }
        return this;
    }

    public UserTestObject withInorrectEmail() {
        switch (user.getUsername()) {
            case "kenshin985":
                user.setEmail("kenshin985email.com");
                break;
        }
        return this;
    }

    public UserTestObject withTooShortPassword() {
        user.setPassword("1234");
        return this;
    }

    public UserTestObject withTooLongPasswordPassword() {
        user.setPassword("012345678901234567890123456789012345678901234567890123456789");
        return this;
    }

    public UserTestObject withCorrectPasswordPassword() {
        user.setPassword("$T43pd2kjuf2GwE3cDFUBgsQr");
        return this;
    }

    public User build() {
        return this.user;
    }

}
