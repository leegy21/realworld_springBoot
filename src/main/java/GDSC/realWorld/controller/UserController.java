package GDSC.realWorld.controller;

import GDSC.realWorld.domain.UserDTO;
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
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO);
        userService.save(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.updateUser(userDTO);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
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
