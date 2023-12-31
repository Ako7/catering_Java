package com.example.catering.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/usersList").hasRole("ADMIN") //do wyswietlania kont uzytkownikow
                                .requestMatchers("/usersList/delete/**").hasRole("ADMIN") //do usuwania konta użytkownika
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/main").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/menu/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/cart/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/contact").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/calcbmi").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/calculateBMR").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/bmr").hasAnyRole("ADMIN", "USER")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/main")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}