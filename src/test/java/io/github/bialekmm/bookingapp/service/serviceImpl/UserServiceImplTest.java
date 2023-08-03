package io.github.bialekmm.bookingapp.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.bialekmm.bookingapp.dto.UserDto;
import io.github.bialekmm.bookingapp.entity.UserEntity;
import io.github.bialekmm.bookingapp.service.AgeService;
import io.github.bialekmm.bookingapp.repository.RoleRepository;
import io.github.bialekmm.bookingapp.repository.UserRepository;
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AgeService ageService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllUsers() {
        UserEntity user1 = createUserEntity(1L, "John", "Doe", "john@example.com");
        UserEntity user2 = createUserEntity(2L, "Jane", "Smith", "jane@example.com");
        List<UserEntity> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDTOs = userService.findAllUsers();

        assertTrue(userDTOs.stream().allMatch(Objects::nonNull));

        List<Long> userIdsFromDtoList = userDTOs.stream().map(UserDto::getId).toList();
        assertTrue(userIdsFromDtoList.containsAll(Arrays.asList(1L, 2L)));

        assertEquals("John", userDTOs.get(0).getFirstName());
        assertEquals("Doe", userDTOs.get(0).getLastName());
        assertEquals("john@example.com", userDTOs.get(0).getEmail());

        assertEquals("Jane", userDTOs.get(1).getFirstName());
        assertEquals("Smith", userDTOs.get(1).getLastName());
        assertEquals("jane@example.com", userDTOs.get(1).getEmail());
    }

    @Test
    public void testFindByEmail_ExistingEmail_ShouldReturnUserEntity() {
        String existingEmail = "john@example.com";
        UserEntity user = createUserEntity(1L, "John", "Doe", existingEmail);

        when(userRepository.findByEmail(existingEmail)).thenReturn(user);

        UserEntity foundUser = userRepository.findByEmail(existingEmail);

        assertEquals(user, foundUser);
    }

    @Test
    public void testFindByEmail_NonExistingEmail_ShouldReturnNull() {
        String nonExistingEmail = "nonexistent@example.com";

        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(null);

        UserDto foundUser = userService.findByEmail(nonExistingEmail);

        assertNull(foundUser);
    }

    @Test
    public void testSaveUser() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setGender("Male");
        userDto.setBirthDate("15-05-1990");
        userDto.setAddress("123 Main St");
        userDto.setEmail("john@example.com");
        userDto.setPassword("secretpassword");

        UserEntity mockedSavedUser = new UserEntity();
        when(userRepository.save(any(UserEntity.class))).thenReturn(mockedSavedUser);

        when(passwordEncoder.encode(eq(userDto.getPassword()))).thenReturn("encodedPassword");

        userService.saveUser(userDto);

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());

        UserEntity capturedUser = userCaptor.getValue();
        assertEquals("John", capturedUser.getFirstName());
        assertEquals("Doe", capturedUser.getLastName());
        assertEquals("Male", capturedUser.getGender());
        assertEquals("123 Main St", capturedUser.getAddress());
        assertEquals("john@example.com", capturedUser.getEmail());
        assertEquals("encodedPassword", capturedUser.getPassword());
    }

    @Test
    public void testDeleteUser() {
        UserEntity user = createUserEntity(1L, "John", "Doe", "john@example.com");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            user.setId(id);
            return null;
        }).when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);

        assertNull(userRepository.findByEmail("john@example.com"));
    }

    @Test
    public void testAgeFromBirthDate() {
        UserEntity user = createUserEntity(1L, "John", "Doe", "john@example.com");
        user.setBirthDate("01-01-1996");

        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        when(ageService.ageFromBirthDate("john@example.com", "01-01-1996")).thenReturn(27);

        int age = userService.ageFromBirthDate("john@example.com", "01-01-1996");

        assertEquals(27, age);
    }


    private UserDto createUserDto(String firstName, String lastName, String email, String password, String gender,
                                  String birthDate) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setGender(gender);
        userDto.setBirthDate(String.valueOf(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        return userDto;
    }

    private UserEntity createUserEntity(Long id, String firstName, String lastName, String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setBirthDate(String.valueOf(LocalDate.now().minusYears(25)));
        return userEntity;
    }

    private UserEntity createUserEntity(Long id, String firstName, String lastName, String email, LocalDate birthDate) {
        UserEntity userEntity = createUserEntity(id, firstName, lastName, email);
        userEntity.setBirthDate(String.valueOf(birthDate));
        return userEntity;
    }
}
