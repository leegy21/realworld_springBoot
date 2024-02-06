package GDSC.realWorld.controller;

import GDSC.realWorld.domain.UserDTO;
import GDSC.realWorld.domain.UserWrapper;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.UserNotFoundException;
import GDSC.realWorld.login.LoginForm;
import GDSC.realWorld.login.SessionConst;
import GDSC.realWorld.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
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
    public ResponseEntity updateUser(@RequestBody UserWrapper userWrapper) {
        try {
            User user = userService.updateUser(userWrapper.getUser());
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

    @PostMapping("/users/login")
        public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) {
            if (bindingResult.hasErrors()) {
            return "login/loginForm";
            }

        Member loginMember = userService.login(form.getLoginId(), form.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @PostMapping("/users/logout")
    public String logoutV3(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }


    @PostMapping("/profiles/{username}/follow")
    public ResponseEntity followUser(@PathVariable String username, @RequestParam String usernameToFollow) {
        try {
            userService.followUser(username, usernameToFollow);

            return getProfile(usernameToFollow);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/profiles/{username}/follow")
    public ResponseEntity unfollowUser(@PathVariable String username, @RequestParam String usernameToUnfollow) {
        try {
            userService.unfollowUser(username, usernameToUnfollow);
    
            return getProfile(username);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
