package GDSC.realWorld.service;

import GDSC.realWorld.domain.UserDTO;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.UserNotFoundException;
import GDSC.realWorld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return Optional.ofNullable(userRepository.findUserByEmail(email)).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public User updateUser(UserDTO userDTO) {
        User user = Optional.ofNullable(userRepository.findUserByEmail(userDTO.getEmail()))
                .orElseThrow(UserNotFoundException::new);
        user.setEmail(userDTO.getEmail());
        user.setBio(userDTO.getBio());
        user.setImage(userDTO.getImage());
        return user;
    }
}
