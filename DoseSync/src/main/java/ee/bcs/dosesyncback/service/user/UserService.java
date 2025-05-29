package ee.bcs.dosesyncback.service.user;

import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import ee.bcs.dosesyncback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.user.User;
import ee.bcs.dosesyncback.persistence.user.UserMapper;
import ee.bcs.dosesyncback.persistence.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = userMapper.toUserDtos(users);
        return userDtos;
    }

    @Transactional
    public Integer createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public UserDto findUser(Integer userId) {
        User user = getValidUserBy(userId);
        UserDto userDto = userMapper.toUserDto(user);
        return userDto;
    }

    private User getValidUserBy(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("userId", userId));
        return user;
    }
}