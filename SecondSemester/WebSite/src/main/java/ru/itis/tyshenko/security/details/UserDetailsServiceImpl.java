package ru.itis.tyshenko.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.tyshenko.repository.UserRepository;

@Service("myUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return  new UserDetailsImpl(userRepository
                .getByEmail(s).orElseThrow(() ->
                        new UsernameNotFoundException("user with this email wasn't found")));
    }
}
