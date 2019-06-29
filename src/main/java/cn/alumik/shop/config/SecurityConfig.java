package cn.alumik.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(users.username("usr1").password("pwd1").roles("LEVEL_0", "LEVEL_1"))
                .withUser(users.username("usr2").password("pwd2").roles("LEVEL_2"))
                .withUser(users.username("usr3").password("pwd3").roles("LEVEL_3"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/webjars/**", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/authenticate").permitAll()
                .and()
                .logout().deleteCookies("JSESSIONID").permitAll()
                .and()
                .rememberMe().key("shop").tokenValiditySeconds(3600 * 7 * 24);
    }
}
