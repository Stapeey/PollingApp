package Stapi.VotingApp.Controllers;

import Stapi.VotingApp.Dto.RegisterDto;
import Stapi.VotingApp.Models.UserEntity;
import Stapi.VotingApp.Repositories.UserRepository;
import Stapi.VotingApp.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/register")
    public String register(Model model){
        RegisterDto register = new RegisterDto();
        model.addAttribute("register", register);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("register") RegisterDto registerDto){
        UserEntity user = UserEntity.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();
        userRepository.save(user);
        return "redirect:/polls";
    }

    @GetMapping("/login")
    public String login(Model model){
        RegisterDto login = new RegisterDto();
        model.addAttribute("login", login);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("login") RegisterDto login){
        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Authentication response = authenticationManager.authenticate(authentication);
        if (response.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(response);
        }
        return "redirect:/polls";
    }
}
