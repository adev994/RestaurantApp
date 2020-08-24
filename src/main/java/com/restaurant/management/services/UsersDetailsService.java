package com.restaurant.management.services;

import com.restaurant.management.dao.UsersRepository;
import com.restaurant.management.model.USersDetails;
import com.restaurant.management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByLogin(s);
        user.orElseThrow(()->new UsernameNotFoundException("USer Not Found "));

        return user.map(myUser->new USersDetails(myUser)).get();
    }
}
