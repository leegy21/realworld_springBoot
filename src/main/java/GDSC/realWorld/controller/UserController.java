package GDSC.realWorld.controller;

import GDSC.realWorld.domain.UserDTO;
import GDSC.realWorld.domain.UserWrapper;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.UserNotFoundException;
import GDSC.realWorld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity registerUser(@RequestBody UserWrapper userWrapper) {
        User user = new User(userWrapper.getUser());
        userService.save(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.updateUser(userDTO);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profiles")
    public ResponseEntity getProfile(@RequestParam String username) {
        try {
            User user = userService.findByUsername(username);
            Map<String, Object> profile = new HashMap<>();
            Map<String, Object> json = new HashMap<>();
            profile.put("username", user.getUsername());
            profile.put("bio", user.getBio());
            profile.put("image", user.getImage());
            profile.put("following", user.isDemo());
            json.put("profile", profile);
            return new ResponseEntity(json, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
