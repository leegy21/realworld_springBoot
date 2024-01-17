package GDSC.realWorld.controller;

import GDSC.realWorld.domain.UserDTO;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.repository.UserRepository;
import GDSC.realWorld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        userService.save(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }

}
