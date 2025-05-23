package ee.bcs.dosesyncback.service.user;

import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import ee.bcs.dosesyncback.persistence.user.User;
import ee.bcs.dosesyncback.persistence.user.UserMapper;
import ee.bcs.dosesyncback.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

private final UserRepository userRepository;
private final UserMapper userMapper;

public List<UserDto> getAllUsers(){
List<User> users = userRepository.findAll();
List<UserDto> userDtos = userMapper.toUserDtos(users);
return userDtos;

}

}
