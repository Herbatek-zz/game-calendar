package com.piotrek.gamecalendar.role;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
