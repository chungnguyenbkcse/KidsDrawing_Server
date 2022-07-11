package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.app.kidsdrawing.dto.CreateUserRequest;
import com.app.kidsdrawing.dto.GetUserInfoResponse;
import com.app.kidsdrawing.dto.GetUserResponse;
import com.app.kidsdrawing.entity.Role;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.UserAlreadyRegisteredException;
import com.app.kidsdrawing.repository.RoleRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.UserService;
import com.app.kidsdrawing.util.AuthUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    @Override
    public ResponseEntity<Map<String, Object>> getAllUsers(Long role_id) {
        List<GetUserResponse> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findAll();
        Optional<Role> roleOpt = roleRepository.findById(role_id);
        Role role = roleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.role.not_found");
        });
        pageUser.forEach(user -> {
            if (user.getRoles().contains(role) == true){
                GetUserResponse userResponse = GetUserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .profile_image_url(user.getProfileImageUrl())
                    .sex(user.getSex())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .createTime(user.getCreateTime())
                    .build();
                allUserResponses.add(userResponse);
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("users", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserInfoResponse getUserInfoByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        return GetUserInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profile_image_url(user.getProfileImageUrl())
                .dateOfBirth(user.getDateOfBirth())
                .sex(user.getSex())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createTime(user.getCreateTime())
                .build();
    }

    @Override
    public GetUserInfoResponse getUserInfoById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        return GetUserInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .sex(user.getSex())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createTime(user.getCreateTime())
                .build();
    }

    @Override
    public GetUserInfoResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        GetUserInfoResponse getUserInfoResponse = getUserInfoByUsername(username);
        return getUserInfoResponse;
    }

    @Override
    public Long createUser(CreateUserRequest createUserRequest) {
        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());

        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new UserAlreadyRegisteredException("exception.user.user_taken");
        }
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new UserAlreadyRegisteredException("exception.user.email_taken");
        }

        List<Role> validRoles = new ArrayList<>();
        createUserRequest.getRoleNames().forEach(roleName -> {
            roleRepository.findByName(roleName).<Runnable>map(role -> () -> validRoles.add(role))
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(String.format("exception.role.invalid", roleName));
                    })
                    .run();
        });

        User savedUser = User.builder()
                .username(createUserRequest.getUsername())
                .email(createUserRequest.getEmail())
                .password(encodedPassword)
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .dateOfBirth(createUserRequest.getDateOfBirth())
                .sex(createUserRequest.getSex())
                .phone(createUserRequest.getPhone())
                .address(createUserRequest.getAddress())
                .roles(new HashSet<>(validRoles))
                .build();
        userRepository.save(savedUser);

        return savedUser.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(username, username);

        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Collection<SimpleGrantedAuthority> authorities = authUtil.parseAuthoritiesFromRoles(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    @Override
    public Long updateUser(Long id, CreateUserRequest createUserRequest) {
        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        List<Role> validRoles = new ArrayList<>();
        createUserRequest.getRoleNames().forEach(roleName -> {
            roleRepository.findByName(roleName).<Runnable>map(role -> () -> validRoles.add(role))
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(String.format("exception.role.invalid", roleName));
                    })
                    .run();
        });
        
        user.setUsername(createUserRequest.getUsername());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setAddress(createUserRequest.getAddress());
        user.setDateOfBirth(createUserRequest.getDateOfBirth());
        user.setProfileImageUrl(createUserRequest.getProfile_image_url());
        user.setSex(createUserRequest.getSex());
        user.setPhone(createUserRequest.getPhone());
        user.setRoles(new HashSet<>(validRoles));

        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Long removeUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        userRepository.deleteById(id);
        return id;
    }
}