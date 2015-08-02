package zarudnyi.trials.restaurant.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zarudnyi.trials.restaurant.config.SecurityConfig;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.entity.User;
import zarudnyi.trials.restaurant.model.validator.UserValidator;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;


    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user;
        try{
            user = userDAO.findByLogin(s);
        }catch (EmptyResultDataAccessException e){
            throw new UsernameNotFoundException(s);
        }

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        if (user.isAdmin())
            roles.add(new SimpleGrantedAuthority(SecurityConfig.ROLE_ADMIN));

        roles.add(new SimpleGrantedAuthority(SecurityConfig.ROLE_USER));
        String password = user.getPassword()==null?"":user.getPassword();
        if (password.contains("{bcrypt}"))
            password= password.substring(8);


        return new org.springframework.security.core.userdetails.User(user.getLogin(),password ,roles );
    }

    public void updateUser(User user){
        userDAO.updateUser(user);
    }

    public User createUser(String login){
        return userDAO.createUser(login);
    }

    public User getUserByLogin(String login){
        return userDAO.findByLogin(login);
    }

    public User getUserById(Integer id){
        return userDAO.findById(id);
    }


    public void createUser(User user) {
        User created = userDAO.createUser(user.getLogin());
        user.setId(created.getId());
        userDAO.updateUser(user);
    }

    public User currentUser(){
        return getUserByLogin(currentUserLogin());
    }

    public String currentUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
