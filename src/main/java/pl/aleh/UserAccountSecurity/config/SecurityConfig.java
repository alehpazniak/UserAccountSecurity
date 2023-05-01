package pl.aleh.UserAccountSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.aleh.UserAccountSecurity.services.PersonDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PersonDetailsService personDetailsService;

  @Autowired
  public SecurityConfig(final PersonDetailsService personDetailsService) {
    this.personDetailsService = personDetailsService;
  }

  //configuring Spring Security
  //configuring authorization
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests()
        .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
        .anyRequest().hasAnyRole("USER", "ADMIN")
        .and()
        .formLogin().loginPage("/auth/login")
        .loginProcessingUrl("/process_login")
        .defaultSuccessUrl("/hello", true)
        .failureUrl("/auth/login?error")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/auth/login");
  }

  //Setting up authentication
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
