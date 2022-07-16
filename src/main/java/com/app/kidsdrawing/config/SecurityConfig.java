package com.app.kidsdrawing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/user/**").hasAnyAuthority("ADMIN_USER")
                //.antMatchers("/api/v1/user/admin/**").hasAnyAuthority("SUPER_ADMIN_USER")
                //.antMatchers("/api/v1/user/staff/**").hasAnyAuthority("SUPER_ADMIN_USER","ADMIN_USER")
                //.antMatchers("/api/v1/user/teacher/**").hasAnyAuthority("SUPER_ADMIN_USER","ADMIN_USER","STAFF_USER")
                .antMatchers("/api/v1/registration").permitAll()
                .antMatchers("/api/v1/cloudinary/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/art-type/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-type/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/art-type/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-type/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/art-level/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-level/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/art-level/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-level/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/art-age/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-age/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/art-age/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-age/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/contest/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/contest/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/contest/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/contest/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/course/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/course/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/course/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/course/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/lesson-time/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/lesson-time/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/lesson-time/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/lesson-time/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/schedule/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/schedule/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/schedule/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/schedule/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/schedule-item/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/schedule-item/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/schedule-item/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/schedule-item/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-register-level/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-register-level/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-register-level/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-register-level/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/user-grade-contest/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-grade-contest/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/user-grade-contest/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-grade-contest/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/user-register-join-contest/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-join-contest/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/user-register-join-contest/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-register-join-contest/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/semester/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/semester/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/semester/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/semester-course/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/semester-course/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/semester-course/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/semester-course/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/user-register-join-semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-join-semester/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/user-register-join-semester/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-register-join-semester/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-teach-semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-teach-semester/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-teach-semester/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-teach-semester/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/classes/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/classes/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/classes/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/classes/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/section/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/section/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/section/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/section/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-leave/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-leave/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-leave/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-leave/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/student-leave/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/student-leave/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/student-leave/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/student-leave/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/exercise-level/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercise-level/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/exercise-level/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercise-level/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/exercises/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercises/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/exercises/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercises/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial-page/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial-page/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial-page/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial-page/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-register-tutorial/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-register-tutorial/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-register-tutorial/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-register-tutorial/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/exercise-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercise-submission/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/exercise-submission/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercise-submission/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/contest-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/contest-submission/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/contest-submission/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/contest-submission/**").permitAll()
                // .antMatchers(HttpMethod.GET, "/admin/**")
                // .hasAuthority("ADMIN_USER")
                // .antMatchers(HttpMethod.GET, "/user/**")
                // .hasAuthority("STANDARD_USER")
                .anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}