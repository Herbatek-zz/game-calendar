package com.piotrek.gamecalendar.gaming_platform;

import com.piotrek.gamecalendar.test_data.GamingPlatformTestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Java6BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GamingPlatformServiceTest {

    @Mock
    private GamingPlatformRepository gamingPlatformRepository;

    @InjectMocks
    private GamingPlatformService gamingPlatformService;

    @Test
    @DisplayName("Should find page of gaming platforms, when not found, then return empty page")
    void shouldFindPageOfGamingPlatforms_whenZeroGamingPlatforms_thenReturnEmptyPage() {
        // given
        Pageable pageable = PageRequest.of(1, 20);
        PageImpl<GamingPlatform> emptyPageable = new PageImpl<>(Collections.emptyList(), pageable, 0);
        given(gamingPlatformRepository.findAll(pageable)).willReturn(emptyPageable);

        // when
        Page<GamingPlatform> returnedPage = gamingPlatformService.findPageOfGamingPlatforms(pageable);

        // then
        then(returnedPage).isEqualTo(emptyPageable).isEmpty();
        verify(gamingPlatformRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(gamingPlatformRepository);
    }

    @Test
    @DisplayName("Should find page of gaming platforms, when found three, then return page with three objects")
    void shouldFindPageOfGamingPlatforms_whenThreeGamingPlatforms_thenReturnPageWithThreeObjects() {
        // given
        Pageable pageable = PageRequest.of(1, 20);
        PageImpl<GamingPlatform> expectedPage = new PageImpl<>(Arrays.asList(
                GamingPlatformTestObject.builder().pc().build(),
                GamingPlatformTestObject.builder().xbox360().build(),
                GamingPlatformTestObject.builder().ps4().build()
        ), pageable, 3);
        given(gamingPlatformRepository.findAll(pageable)).willReturn(expectedPage);

        // when
        Page<GamingPlatform> returnedPage = gamingPlatformService.findPageOfGamingPlatforms(pageable);

        // then
        then(returnedPage).isEqualTo(expectedPage).isNotEmpty();
        verify(gamingPlatformRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(gamingPlatformRepository);
    }

    @Test
    @DisplayName("Should find by id, when not found, then throw NotFoundException")
    void shouldFindById_whenNotFound_thenThrowNotFoundException() {
        // given
        given(gamingPlatformRepository.findById(15L)).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> gamingPlatformService.findById(15L));

        // then
        then(throwable).isNotNull().hasMessage("Not found Gaming Platform with id = 15").isEqualTo(throwable);
        verify(gamingPlatformRepository, times(1)).findById(15L);
        verifyNoMoreInteractions(gamingPlatformRepository);
    }

    @Test
    @DisplayName("Should find by id, when found, then return")
    void shouldFindById_whenFound_thenReturn() {
        // given
        final Long ID = 33L;
        GamingPlatform expectedGamingPlatform = GamingPlatformTestObject.builder().pc().withId(ID).build();
        given(gamingPlatformRepository.findById(ID)).willReturn(Optional.of(expectedGamingPlatform));

        // when
        GamingPlatform byId = gamingPlatformService.findById(ID);

        // then
        then(byId).isNotNull().isEqualTo(expectedGamingPlatform);
        verify(gamingPlatformRepository, times(1)).findById(ID);
        verifyNoMoreInteractions(gamingPlatformRepository);
    }

    @Test
    @DisplayName("Should save, then save")
    void shouldSave_thenSave() {
        // given
       GamingPlatform expectedGamingPlatformBySaved = GamingPlatformTestObject.builder().pc().build();
        given(gamingPlatformRepository.save(expectedGamingPlatformBySaved)).willReturn(expectedGamingPlatformBySaved);

        // when
        GamingPlatform savedGamingPlatform = gamingPlatformService.save(expectedGamingPlatformBySaved);

        // then
        then(savedGamingPlatform).isNotNull().isEqualTo(expectedGamingPlatformBySaved);
        verify(gamingPlatformRepository, times(1)).save(expectedGamingPlatformBySaved);
        verifyNoMoreInteractions(gamingPlatformRepository);
    }

    @Test
    @DisplayName("Should delete, when not found, then throw NotFoundException")
    void shouldDeleteById_whenNotFound_thenThrowNotFoundException() {
        // given
        final Long ID = 50L;
        given(gamingPlatformRepository.findById(ID)).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> gamingPlatformService.deleteById(ID));

        // then
        then(throwable).isNotNull().isEqualTo(throwable).hasMessage("Not found Gaming Platform with id = " + ID);
        verify(gamingPlatformRepository, times(1)).findById(ID);
        verifyNoMoreInteractions(gamingPlatformRepository);
    }

    @Test
    @DisplayName("Should delete, when not found, then throw NotFoundException")
    void shouldDeleteById_whenFound_thenDeleteAndReturnDeletedObject() {
        // given
        GamingPlatform gamingPlatformToDelete = GamingPlatformTestObject.builder().pc().withId(63L).build();
        given(gamingPlatformRepository.findById(gamingPlatformToDelete.getId())).willReturn(Optional.of(gamingPlatformToDelete));

        // when
        GamingPlatform deletedGamingPlatform = gamingPlatformService.deleteById(gamingPlatformToDelete.getId());

        // then
        then(deletedGamingPlatform).isNotNull().isEqualTo(gamingPlatformToDelete);
        verify(gamingPlatformRepository, times(1)).findById(gamingPlatformToDelete.getId());
        verify(gamingPlatformRepository, times(1)).delete(gamingPlatformToDelete);
        verifyNoMoreInteractions(gamingPlatformRepository);
    }
}