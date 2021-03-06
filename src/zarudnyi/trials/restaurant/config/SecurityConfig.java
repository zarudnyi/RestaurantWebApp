package zarudnyi.trials.restaurant.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROLE_ADMIN  = "ROLE_ADMIN";
    public static final String ROLE_USER  = "ROLE_USER";



    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register*").permitAll()
                .antMatchers("/profile**").authenticated()
                .antMatchers("/groups**").authenticated()
                .antMatchers("/api/**").authenticated()
                .antMatchers(HttpMethod.POST, "/**").authenticated()
                .antMatchers("/menu/createItem**").access("hasRole('" + SecurityConfig.ROLE_ADMIN + "')")
                .antMatchers("/menu/createCategory**").access("hasRole('" + SecurityConfig.ROLE_ADMIN + "')")
                .antMatchers(HttpMethod.POST, "/menu**").access("hasRole('"+SecurityConfig.ROLE_ADMIN+"')")
                .anyRequest().permitAll().and();


        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error=true")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll();

        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}