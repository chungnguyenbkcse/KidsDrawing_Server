package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.app.kidsdrawing.dto.CreateStudentRequest;
import com.app.kidsdrawing.dto.CreateTeacherRequest;
import com.app.kidsdrawing.dto.CreateUserRequest;
import com.app.kidsdrawing.dto.CreateUserStatusRequest;
import com.app.kidsdrawing.dto.GetStudentResponse;
import com.app.kidsdrawing.dto.GetTeacherRegisterQualificationResponse;
import com.app.kidsdrawing.dto.GetTeacherResponse;
import com.app.kidsdrawing.dto.GetUserInfoResponse;
import com.app.kidsdrawing.dto.GetUserResponse;
import com.app.kidsdrawing.entity.Role;
import com.app.kidsdrawing.entity.TeacherRegisterQualification;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.UserAlreadyRegisteredException;
import com.app.kidsdrawing.repository.RoleRepository;
import com.app.kidsdrawing.repository.TeacherRegisterQualificationRepository;
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
    private final TeacherRegisterQualificationRepository teacherRegisterQualificationRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllStudents(Long role_id) {
        List<GetStudentResponse> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findAll();
        Optional<Role> roleOpt = roleRepository.findById(role_id);
        Role role = roleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.role.not_found");
        });
        pageUser.forEach(user -> {
            if (user.getRoles().contains(role) == true){
                GetStudentResponse userResponse = GetStudentResponse.builder()
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
                    .parent(user.getParent().getUsername())
                    .build();
                allUserResponses.add(userResponse);
            }
        });
        Map<String, Object> response = new HashMap<>();
        response.put("students", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Long updateUserStatus(Long id, CreateUserStatusRequest createUserStatusRequest) {
        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        user.setStatus(createUserStatusRequest.getStatus());
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllParents(Long role_id) {
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
        response.put("parents", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacher() {
        List<GetTeacherResponse> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findAll();
        Optional<Role> roleOpt = roleRepository.findById((long) 4);
        Role role = roleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.role.not_found");
        });
        List<List<GetTeacherRegisterQualificationResponse>> allTeacherRegisterQualificationResponses = new ArrayList<>();
        List<List<GetTeacherRegisterQualificationResponse>> allTeacherRegisterQualificationDoingResponses = new ArrayList<>();
        List<List<GetTeacherRegisterQualificationResponse>> allTeacherRegisterQualificationNotAcceptResponses = new ArrayList<>();
        List<TeacherRegisterQualification> pageTeacherRegisterQualification = teacherRegisterQualificationRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        pageUser.forEach(user -> {
            if (user.getRoles().contains(role) == true){
                GetTeacherResponse userResponse = GetTeacherResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
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
                List<GetTeacherRegisterQualificationResponse> teacherRegisterQualificationResponses = new ArrayList<>();
                List<GetTeacherRegisterQualificationResponse> teacherRegisterQualificationDoingResponses = new ArrayList<>();
                List<GetTeacherRegisterQualificationResponse> teacherRegisterQualificationNotAcceptResponses = new ArrayList<>();
                pageTeacherRegisterQualification.forEach(content -> {
                    if (content.getTeacher().getId() == user.getId() && content.getStatus() == "Accept"){
                        GetTeacherRegisterQualificationResponse teacherRegisterQualificationResponse = GetTeacherRegisterQualificationResponse.builder()
                            .id(content.getId())
                            .teacher_id(content.getTeacher().getId())
                            .reviewer_id(content.getReviewer().getId())
                            .course_id(content.getCourse().getId())
                            .degree_photo_url(content.getDegree_photo_url())
                            .status(content.getStatus())
                            .build();
                        teacherRegisterQualificationResponses.add(teacherRegisterQualificationResponse);
                    }
                    else if (content.getTeacher().getId() == user.getId() && content.getStatus() == "Doing"){
                        GetTeacherRegisterQualificationResponse teacherRegisterQualificationResponse = GetTeacherRegisterQualificationResponse.builder()
                            .id(content.getId())
                            .teacher_id(content.getTeacher().getId())
                            .reviewer_id(content.getReviewer().getId())
                            .course_id(content.getCourse().getId())
                            .degree_photo_url(content.getDegree_photo_url())
                            .status(content.getStatus())
                            .build();
                        teacherRegisterQualificationDoingResponses.add(teacherRegisterQualificationResponse);
                    }
                    else {
                        GetTeacherRegisterQualificationResponse teacherRegisterQualificationResponse = GetTeacherRegisterQualificationResponse.builder()
                            .id(content.getId())
                            .teacher_id(content.getTeacher().getId())
                            .reviewer_id(content.getReviewer().getId())
                            .course_id(content.getCourse().getId())
                            .degree_photo_url(content.getDegree_photo_url())
                            .status(content.getStatus())
                            .build();
                        teacherRegisterQualificationNotAcceptResponses.add(teacherRegisterQualificationResponse);
                    }
                });
                allTeacherRegisterQualificationResponses.add(teacherRegisterQualificationResponses);
            }
        });
        response.put("teachers", allUserResponses);
        response.put("teacher_register_qualifications", allTeacherRegisterQualificationResponses);
        response.put("teacher_register_doing_qualifications", allTeacherRegisterQualificationDoingResponses);
        response.put("teacher_register_not_accept_qualifications", allTeacherRegisterQualificationNotAcceptResponses);
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

        Optional <User> userOpt = userRepository.findById(createUserRequest.getParent_id());
        User parent = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.parent.not_found");
        });

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
                .parent(parent)
                .roles(new HashSet<>(validRoles))
                .build();
        userRepository.save(savedUser);

        return savedUser.getId();
    }


    @Override
    public Long createStudent(CreateStudentRequest createStudentOrParentRequest) {
        String encodedPassword = passwordEncoder.encode(createStudentOrParentRequest.getPassword());

        if (userRepository.existsByUsername(createStudentOrParentRequest.getUsername())) {
            throw new UserAlreadyRegisteredException("exception.user.user_taken");
        }
        if (userRepository.existsByEmail(createStudentOrParentRequest.getEmail())) {
            throw new UserAlreadyRegisteredException("exception.user.email_taken");
        }

        List<Role> validRoles = new ArrayList<>();
        createStudentOrParentRequest.getRoleNames().forEach(roleName -> {
            roleRepository.findByName(roleName).<Runnable>map(role -> () -> validRoles.add(role))
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(String.format("exception.role.invalid", roleName));
                    })
                    .run();
        });



        User savedUser = User.builder()
                .username(createStudentOrParentRequest.getUsername())
                .email(createStudentOrParentRequest.getEmail())
                .password(encodedPassword)
                .firstName(createStudentOrParentRequest.getFirstName())
                .lastName(createStudentOrParentRequest.getLastName())
                .dateOfBirth(createStudentOrParentRequest.getDateOfBirth())
                .sex(createStudentOrParentRequest.getSex())
                .phone(createStudentOrParentRequest.getPhone())
                .address(createStudentOrParentRequest.getAddress())
                .roles(new HashSet<>(validRoles))
                .build();
        userRepository.save(savedUser);

        return savedUser.getId();
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz@#$%*1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @Override
    public Long createTeacher(CreateTeacherRequest createTeacherRequest) {
        String encodedPassword = passwordEncoder.encode(getSaltString());

        if (userRepository.existsByUsername(createTeacherRequest.getUsername())) {
            throw new UserAlreadyRegisteredException("exception.user.user_taken");
        }
        if (userRepository.existsByEmail(createTeacherRequest.getEmail())) {
            throw new UserAlreadyRegisteredException("exception.user.email_taken");
        }

        List<Role> validRoles = new ArrayList<>();
        createTeacherRequest.getRoleNames().forEach(roleName -> {
            roleRepository.findByName(roleName).<Runnable>map(role -> () -> validRoles.add(role))
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(String.format("exception.role.invalid", roleName));
                    })
                    .run();
        });



        User savedUser = User.builder()
                .username(createTeacherRequest.getUsername())
                .email(createTeacherRequest.getEmail())
                .password(encodedPassword)
                .firstName(createTeacherRequest.getFirstName())
                .lastName(createTeacherRequest.getLastName())
                .dateOfBirth(createTeacherRequest.getDateOfBirth())
                .sex(createTeacherRequest.getSex())
                .phone(createTeacherRequest.getPhone())
                .address(createTeacherRequest.getAddress())
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

        Optional<User> parentOpt = userRepository.findById(createUserRequest.getParent_id());
        User parent = parentOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.parent.not_found");
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
        user.setParent(parent);
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