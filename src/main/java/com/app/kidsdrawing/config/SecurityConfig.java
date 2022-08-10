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
                //.antMatchers("/api/v1/user/admin/**").hasAnyAuthority("SUPER_ADMIN_USER")
                //.antMatchers("/api/v1/user/staff/**").hasAnyAuthority("SUPER_ADMIN_USER","ADMIN_USER")
                //.antMatchers("/api/v1/user/teacher/**").hasAnyAuthority("SUPER_ADMIN_USER","ADMIN_USER","STAFF_USER")
                .antMatchers("/api/v1/registration").permitAll()
                .antMatchers("/api/v1/cloudinary/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/art-type/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-type/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/art-type/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-type/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/art-level/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-level/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/art-level/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-level/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/art-age/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/art-age/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/art-age/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/art-age/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/contest/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/contest/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/contest/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/contest/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/course/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/course/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/course/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/course/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/lesson-time/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/lesson-time/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/lesson-time/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/lesson-time/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/schedule/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/schedule/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/schedule/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/schedule/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/schedule-item/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/schedule-item/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/schedule-item/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/schedule-item/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-register-level/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-register-level/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-register-level/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-register-level/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/user-grade-contest/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.POST, "/api/v1/user-grade-contest/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-grade-contest/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-grade-contest/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/user-register-join-contest/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-join-contest/**").hasAnyAuthority("PARENT_USER", "STUDENT_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-register-join-contest/**").hasAnyAuthority("PARENT_USER", "STUDENT_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-register-join-contest/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/semester/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/semester/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/semester/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/semester-course/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/semester-course/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/semester-course/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/semester-course/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/user-register-join-semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-register-join-semester/**").hasAnyAuthority("PARENT_USER", "STUDENT_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-register-join-semester/**").hasAnyAuthority("PARENT_USER", "STUDENT_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-register-join-semester/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-teach-semester/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-teach-semester/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-teach-semester/**").hasAnyAuthority("TEACHER_USER", "ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-teach-semester/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/classes/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/classes/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/classes/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/classes/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/section/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/section/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/section/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/section/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/section-template/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/section-template/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/section-template/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/section-template/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-leave/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-leave/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-leave/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-leave/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/student-leave/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/student-leave/**").hasAnyAuthority("STUDENT_USER", "PARENT_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/student-leave/**").hasAnyAuthority("STUDENT_USER", "PARENT_USER", "ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/student-leave/**").hasAnyAuthority("STUDENT_USER", "PARENT_USER", "ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/exercise-level/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercise-level/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/exercise-level/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercise-level/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/exercises/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercises/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/exercises/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercises/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial-template/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial-template/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial-template/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial-template/**").hasAnyAuthority("ADMIN_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial-template-page/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial-template-page/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial-template-page/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial-template-page/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/tutorial-page/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/tutorial-page/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/tutorial-page/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/tutorial-page/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/teacher-register-tutorial/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.POST, "/api/v1/teacher-register-tutorial/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/teacher-register-tutorial/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher-register-tutorial/**").hasAnyAuthority("ADMIN_USER", "TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/exercise-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/exercise-submission/**").hasAnyAuthority("STUDENT_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/exercise-submission/**").hasAnyAuthority("STUDENT_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/exercise-submission/**").hasAnyAuthority("STUDENT_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/contest-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/contest-submission/**").hasAnyAuthority("STUDENT_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/contest-submission/**").hasAnyAuthority("STUDENT_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/contest-submission/**").hasAnyAuthority("STUDENT_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/user-grade-exercise-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-grade-exercise-submission/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-grade-exercise-submission/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-grade-exercise-submission/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.GET, "/api/v1/user-grade-contest-submission/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user-grade-contest-submission/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user-grade-contest-submission/**").hasAnyAuthority("TEACHER_USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user-grade-contest-submission/**").hasAnyAuthority("TEACHER_USER")
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