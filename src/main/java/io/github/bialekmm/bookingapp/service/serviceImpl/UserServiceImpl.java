package io.github.bialekmm.bookingapp.service.serviceImpl;

import io.github.bialekmm.bookingapp.dto.UserDto;
import io.github.bialekmm.bookingapp.entity.RoleEntity;
import io.github.bialekmm.bookingapp.entity.UserEntity;
import io.github.bialekmm.bookingapp.repository.RoleRepository;
import io.github.bialekmm.bookingapp.repository.UserRepository;
import io.github.bialekmm.bookingapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntity -> mapToUserDto(userEntity))
                .collect(Collectors.toList());
    }
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public void saveUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setBirthDate(userDto.getBirthDate());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        checkRole(user);
        userRepository.save(user);
    }

    private void checkRole(UserEntity user) {
        if(roleRepository.findByName("ROLE_ADMIN") == null || roleRepository.findByName("ROLE_USER") == null){
            RoleEntity role = new RoleEntity();
            if(role.getName()==null){
                role.setName("ROLE_ADMIN");
                user.setRoles(Arrays.asList(role));
            }
            if(roleRepository.findByName("ROLE_ADMIN") != null && roleRepository.findByName("ROLE_USER") == null){
                role.setName("ROLE_USER");
                user.setRoles(Arrays.asList(role));
            }
        }
        if(roleRepository.findByName("ROLE_ADMIN") != null && roleRepository.findByName("ROLE_USER") != null){
            RoleEntity role = roleRepository.findByName("ROLE_USER");
            user.setRoles(Arrays.asList(role));
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    private UserDto mapToUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setGender(userEntity.getGender());
        userDto.setBirthDate(userEntity.getBirthDate());
        userDto.setAddress(userEntity.getAddress());
        userDto.setEmail(userEntity.getEmail());
        return userDto;
    }
}
