package zarudnyi.trials.restaurant.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zarudnyi.trials.restaurant.config.SecurityConfig;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDAO.findByLogin(s);

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        if (user.isAdmin())
            roles.add(new SimpleGrantedAuthority(SecurityConfig.ROLE_ADMIN));

        roles.add(new SimpleGrantedAuthority(SecurityConfig.ROLE_USER));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword() ,roles );
    }
}
