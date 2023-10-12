package com.project.security.sevice;

import com.project.entity.user.User;
import com.project.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user =  userRepository.findByEmailEquals(email);

        Set<String> roles = user.getUserRole()
                .stream()
                .map(t->t.getRoleType().getName())
                .collect(Collectors.toSet());

        if (user != null) {
            return new UserDetailsImpl(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPassword(),
                    user.getEmail(),
                    roles
            );
        }

        throw new UsernameNotFoundException("User : " + email + " not found");
    }

}


