package com.piotrek.gamecalendar.security.account_verification;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface VerificationTokenRepository extends PagingAndSortingRepository<VerificationToken, Long> {
}
