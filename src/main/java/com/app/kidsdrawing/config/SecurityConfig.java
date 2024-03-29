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
                .antMatchers("/api/v1/user/**").permitAll()
                .antMatchers("/api/v1/password-reset-token/**").permitAll()
                //.antMatchers("/api/v1/user/staff/**").hasAnyAuthority("SUPER_ADMIN","ADMIN")
                //.antMatchers("/api/v1/user/teacher/**").hasAnyAuthority("SUPER_ADMIN","ADMIN","STAFF_USER")
                .antMatchers("/api/v1/notification/**").permitAll()
                .antMatchers("/api/v1/role/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/notification-database/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/notification-database/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers("/api/v1/user-read-notification/**").permitAll()
                .antMatchers("/api/v1/registration").permitAll()
                .antMatchers("/api/v1/cloudinary/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/final-course/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/art-type/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-type/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/art-type/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-type/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/class-has-register-join-semester/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/class-has-register-join-semester/**").hasAnyAuthority("TEACHER", "PARENT", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/user-register-tutorial/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-tutorial/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-register-tutorial/**").hasAnyAuthority("TEACHER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-register-tutorial/**").hasAnyAuthority("TEACHER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/user-register-tutorial-page/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-tutorial-page/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-register-tutorial-page/**").hasAnyAuthority("TEACHER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-register-tutorial-page/**").hasAnyAuthority("TEACHER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/art-level/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-level/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/art-level/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-level/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/art-age/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-age/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/art-age/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-age/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/user-attendance/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/user-attendance/section-student/**").hasAnyAuthority("ADMIN", "TEACHER", "STUDENT")
                .antMatchers(HttpMethod.GET, "/api/v1/contest/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/contest/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/contest/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/contest/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/course/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/course/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/course/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/course/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/lesson-time/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/lesson-time/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/lesson-time/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/holiday/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/holiday/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/holiday/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/holiday/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/lesson-time/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/schedule/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/schedule/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/schedule/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/schedule/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-register-level/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-register-level/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-register-level/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-register-level/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/user-grade-contest/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-grade-contest/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-grade-contest/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-grade-contest/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/user-register-join-contest/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-join-contest/**").hasAnyAuthority("PARENT", "STUDENT")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-register-join-contest/**").hasAnyAuthority("PARENT", "STUDENT")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-register-join-contest/**").hasAnyAuthority("ADMIN", "PARENT", "STUDENT")
                .antMatchers(HttpMethod.GET, "/api/v1/semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/semester/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/semester/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/semester/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/semester-class/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/semester-class/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/semester-class/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/semester-class/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/user-register-join-semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-join-semester").hasAnyAuthority("PARENT", "STUDENT")
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-join-semester/payment/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/user-register-join-semester/**").hasAnyAuthority("PARENT", "STUDENT")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-register-join-semester/**").hasAnyAuthority("ADMIN", "PARENT", "STUDENT")
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-teach-semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-teach-semester/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-teach-semester/**").hasAnyAuthority("TEACHER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-teach-semester/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/classes/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/classes/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/classes/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/classes/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/section/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/section/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/section/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/section/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/section-template/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/section-template/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/section-template/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/section-template/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-leave/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-leave/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-leave/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-leave/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/student-leave/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/student-leave/**").hasAnyAuthority("STUDENT", "PARENT")
                .antMatchers(HttpMethod.PUT, "/api/v1/student-leave/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/student-leave/**").hasAnyAuthority("STUDENT", "PARENT", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/exercise-level/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercise-level/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/exercise-level/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercise-level/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/exercises/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercises/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/exercises/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercises/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial-template/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial-template/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial-template/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial-template/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial-template-page/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial-template-page/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial-template-page/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial-template-page/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial-page/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial-page/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial-page/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial-page/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-register-tutorial/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-register-tutorial/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-register-tutorial/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-register-tutorial/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/exercise-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercise-submission/**").hasAnyAuthority("STUDENT", "PARENT")
                .antMatchers(HttpMethod.PUT, "/api/v1/exercise-submission/**").hasAnyAuthority("STUDENT", "PARENT", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercise-submission/**").hasAnyAuthority("STUDENT", "PARENT")
                .antMatchers(HttpMethod.GET, "/api/v1/contest-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/contest-submission/**").hasAnyAuthority("STUDENT", "PARENT", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/contest-submission/**").hasAnyAuthority("STUDENT", "PARENT", "TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/contest-submission/**").hasAnyAuthority("STUDENT", "PARENT")
                .antMatchers(HttpMethod.GET, "/api/v1/user-grade-exercise-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-grade-exercise-submission/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-grade-exercise-submission/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-grade-exercise-submission/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.GET, "/api/v1/user-grade-contest-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-grade-contest-submission/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-grade-contest-submission/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-grade-contest-submission/**").hasAnyAuthority("TEACHER")
                .antMatchers(HttpMethod.POST, "/api/v1/sendEmail/**").permitAll()
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