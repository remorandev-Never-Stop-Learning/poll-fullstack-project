package es.remorandev.poll.controller;

import es.remorandev.poll.exception.ResourceNotFoundException;
import es.remorandev.poll.model.User;
import es.remorandev.poll.payload.UserIdentityAvailability;
import es.remorandev.poll.payload.UserProfile;
import es.remorandev.poll.payload.UserSummary;
import es.remorandev.poll.repository.PollRepository;
import es.remorandev.poll.repository.UserRepository;
import es.remorandev.poll.repository.VoteRepository;
import es.remorandev.poll.security.CurrentUser;
import es.remorandev.poll.security.UserPrincipal;
import es.remorandev.poll.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser){
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());

        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(name = "username") String username){
        Boolean isAvailable = !this.userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(name = "email") String email){
        Boolean isAvailable = !this.userRepository.existsByUsername(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username){
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long pollCount = this.pollRepository.countByCreatedBy(user.getId());
        long voteCount = this.voteRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }
}
