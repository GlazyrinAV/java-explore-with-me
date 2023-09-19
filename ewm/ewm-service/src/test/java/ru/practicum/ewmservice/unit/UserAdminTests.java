package ru.practicum.ewmservice.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.practicum.ewmclient.model.UserDto;
import ru.practicum.ewmservice.exceptions.exceptions.UserNotFound;
import ru.practicum.ewmservice.exceptions.exceptions.WrongParameter;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.model.mapper.UserMapper;
import ru.practicum.ewmservice.repository.UserRepository;
import ru.practicum.ewmservice.service.user.UserAdminService;
import ru.practicum.ewmservice.service.user.UserAdminServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class UserAdminTests {

    private final UserRepository mockUserRepository = Mockito.mock(UserRepository.class);

    private final UserMapper mockMapper = Mockito.mock(UserMapper.class);

    private final UserAdminService service = new UserAdminServiceImpl(mockUserRepository, mockMapper);

    @Test
    void saveNormal() {
        User user = User.builder()
                .id(1)
                .name("testName")
                .email("test@email.ru")
                .build();
        UserDto dto = UserDto.builder()
                .id(1)
                .name("testName")
                .email("test@email.ru")
                .build();
        UserDto newDto = UserDto.builder()
                .name("testName")
                .email("test@email.ru")
                .build();
        when(mockUserRepository.save(any()))
                .thenReturn(user);
        when(mockUserRepository.findByName(any()))
                .thenReturn(null);
        when(mockMapper.fromDto(newDto))
                .thenReturn(user);
        when(mockMapper.toDto(any()))
                .thenReturn(dto);

        Assertions.assertEquals(dto, service.save(newDto));
        Mockito.verify(mockUserRepository, times(1)).save(user);
    }

    @Test
    void saveWrongName() {
        User user = User.builder()
                .id(1)
                .name("testName")
                .email("test@email.ru")
                .build();
        UserDto newDto = UserDto.builder()
                .name("testName")
                .email("test@email.ru")
                .build();
        when(mockUserRepository.findByName(any()))
                .thenReturn(user);
        WrongParameter exception = Assertions.assertThrows(WrongParameter.class, () -> service.save(newDto));

        Assertions.assertEquals("Пользователь с таким именем уже существует.", exception.getMessage());
        Mockito.verify(mockUserRepository, times(0)).save(user);
    }

    @Test
    void findAllWithIds() {
        User user = User.builder()
                .id(1)
                .name("testName")
                .email("test@email.ru")
                .build();
        UserDto dto = UserDto.builder()
                .id(1)
                .name("testName")
                .email("test@email.ru")
                .build();
        Page<User> page = new PageImpl<>(List.of(user));
        when(mockUserRepository.findAllAdminWithCriteria(any(), any()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(dto);

        Assertions.assertEquals(List.of(dto), service.findAll(0, 10, List.of(1)));
    }

    @Test
    void findAllWithoutIds() {
        User user = User.builder()
                .id(1)
                .name("testName")
                .email("test@email.ru")
                .build();
        UserDto dto = UserDto.builder()
                .id(1)
                .name("testName")
                .email("test@email.ru")
                .build();
        Page<User> page = new PageImpl<>(List.of(user));
        when(mockUserRepository.findAllAdminWithCriteria(any(), any()))
                .thenReturn(page);
        when(mockMapper.toDto(any()))
                .thenReturn(dto);

        Assertions.assertEquals(List.of(dto), service.findAll(0, 10, null));
    }

    @Test
    void remove() {
        User user = User.builder()
                .id(1)
                .name("testName")
                .email("test@email.ru")
                .build();
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user));
        service.remove(1);

        Mockito.verify(mockUserRepository, times(1)).deleteById(1);
    }

    @Test
    void removeWrongId() {
        when(mockUserRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        UserNotFound exception = Assertions.assertThrows(UserNotFound.class, () -> service.remove(1));

        Assertions.assertEquals("Пользователь с ID 1 не найден.", exception.getMessage());
        Mockito.verify(mockUserRepository, times(0)).deleteById(1);
    }


}