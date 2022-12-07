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

import com.app.kidsdrawing.dto.CreateChangePassowrdRequest;
import com.app.kidsdrawing.dto.CreateStudentRequest;
import com.app.kidsdrawing.dto.CreateTeacherRequest;
import com.app.kidsdrawing.dto.CreateTeacherUserRequest;
import com.app.kidsdrawing.dto.CreateUserRequest;
import com.app.kidsdrawing.dto.CreateUserStatusRequest;
import com.app.kidsdrawing.dto.GetStudentForParentResponse;
import com.app.kidsdrawing.dto.GetStudentResponse;
import com.app.kidsdrawing.dto.GetTeacherRegisterQualificationResponse;
import com.app.kidsdrawing.dto.GetTeacherResponse;
import com.app.kidsdrawing.dto.GetUserInfoResponse;
import com.app.kidsdrawing.dto.GetUserResponse;
import com.app.kidsdrawing.entity.EmailDetails;
import com.app.kidsdrawing.entity.TeacherRegisterQualification;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.UserAlreadyRegisteredException;
import com.app.kidsdrawing.repository.CourseRepository;
import com.app.kidsdrawing.repository.TeacherRegisterQualificationRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinContestRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.EmailService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegisterJoinContestRepository userRegisterJoinContestRepository;
    private final CourseRepository courseRepository;
    private final EmailService emailService;
    private final AuthUtil authUtil;
    private final TeacherRegisterQualificationRepository teacherRegisterQualificationRepository;
    private static int total_user_of_jan = 0;
    private static int total_user_of_feb = 0;
    private static int total_user_of_mar = 0;
    private static int total_user_of_apr = 0;
    private static int total_user_of_may = 0;
    private static int total_user_of_jun = 0;
    private static int total_user_of_jul = 0;
    private static int total_user_of_aug = 0;
    private static int total_user_of_sep = 0;
    private static int total_user_of_oct = 0;
    private static int total_user_of_nov = 0;
    private static int total_user_of_dec = 0;

    @Override
    public ResponseEntity<Map<String, Object>> getAllStudents(String role_name) {
        List<GetStudentResponse> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findAllStudent();
        pageUser.forEach(user -> {
                GetStudentResponse userResponse = GetStudentResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .status(user.getStatus())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .profile_image_url(user.getProfileImageUrl())
                    .sex(user.getSex())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .createTime(user.getCreateTime())
                    .parent(user.getParent().getUsername())
                    .parents(user.getParent().getId())
                    .build();
                allUserResponses.add(userResponse);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("students", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTotalStudents() {
        int listUserRegisterJoinSemester = userRepository.findAll1();
        Map<String, Object> response = new HashMap<>();
        response.put("user", listUserRegisterJoinSemester);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTotalParents() {
        int listUserRegisterJoinSemester = userRepository.findAll2();
        Map<String, Object> response = new HashMap<>();
        response.put("user", listUserRegisterJoinSemester);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTotalTeachers() {
        int listUserRegisterJoinSemester = userRepository.findAll3();
        Map<String, Object> response = new HashMap<>();
        response.put("user", listUserRegisterJoinSemester);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUser() {
        List<GetStudentResponse> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findAll();
        pageUser.forEach(user -> {
                GetStudentResponse userResponse = GetStudentResponse.builder()
                    .id(user.getId())
                    .build();
                allUserResponses.add(userResponse);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("students", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllChildForParentId(Long id) {
        List<GetStudentForParentResponse> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findByParentId(id);
        pageUser.forEach(user -> {
                Integer total_course = courseRepository.findTotalCourseForStudent(user.getId()).size();
                Integer total_contest = userRegisterJoinContestRepository.findByStudentId1(user.getId()).size();
                GetStudentForParentResponse userResponse = GetStudentForParentResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .total_course(total_course)
                    .total_contest(total_contest)
                    .status(user.getStatus())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .dateOfBirth(user.getDateOfBirth())
                    .profile_image_url(user.getProfileImageUrl())
                    .sex(user.getSex())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .createTime(user.getCreateTime())
                    .parent(user.getParent().getUsername())
                    .parents(user.getParent().getId())
                    .build();
                allUserResponses.add(userResponse);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("childs", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override 
    public ResponseEntity<Map<String, Object>> getReportUserNew(int year, String role_name) {
        List<Integer> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findAllUserByRole(role_name);
        
        total_user_of_jan = 0;
        total_user_of_feb = 0;
        total_user_of_mar = 0;
        total_user_of_apr = 0;
        total_user_of_may = 0;
        total_user_of_jun = 0;
        total_user_of_jul = 0;
        total_user_of_aug = 0;
        total_user_of_sep = 0;
        total_user_of_oct = 0;
        total_user_of_nov = 0;
        total_user_of_dec = 0;
        pageUser.forEach(user -> {
            if (user.getCreateTime().getYear() == year){
                if (user.getCreateTime().getMonth().toString().equals("JANUARY")){
                    total_user_of_jan += 1;
                }
                else if (user.getCreateTime().getMonth().toString().equals("FEBRUARY")){
                    total_user_of_feb += 1;
                } 
                else if (user.getCreateTime().getMonth().toString().equals("MARCH")){
                    total_user_of_mar += 1;
                } 
                else if (user.getCreateTime().getMonth().toString().equals("APRIL")){
                    total_user_of_apr += 1;
                } 
                else if (user.getCreateTime().getMonth().toString().equals("MAY")){
                    total_user_of_may += 1;
                } 
                else if (user.getCreateTime().getMonth().toString().equals("JUNE")){
                    total_user_of_jun += 1;
                } 
                else if (user.getCreateTime().getMonth().toString().equals("JULY")){
                    total_user_of_jul += 1;
                } 
                else if (user.getCreateTime().getMonth().toString().equals("AUGUST")){
                    total_user_of_aug += 1;
                } 
                else if (user.getCreateTime().getMonth().toString().equals("SEPTEMBER")){
                    total_user_of_sep += 1;
                } 
                else if (user.getCreateTime().getMonth().toString().equals("OCTOBER")){
                    total_user_of_oct += 1;
                }
                else if (user.getCreateTime().getMonth().toString().equals("NOVEMBER")){
                    total_user_of_nov += 1;
                }
                else {
                    total_user_of_dec += 1;
                }
            }   
        });
        allUserResponses.add(total_user_of_jan);
        allUserResponses.add(total_user_of_feb);
        allUserResponses.add(total_user_of_mar);
        allUserResponses.add(total_user_of_apr);
        allUserResponses.add(total_user_of_may);
        allUserResponses.add(total_user_of_jun);
        allUserResponses.add(total_user_of_jul);
        allUserResponses.add(total_user_of_aug);
        allUserResponses.add(total_user_of_sep);
        allUserResponses.add(total_user_of_oct);
        allUserResponses.add(total_user_of_nov);
        allUserResponses.add(total_user_of_dec);

        Map<String, Object> response = new HashMap<>();
        response.put("report_user", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Long updateUserStatus(Long id, CreateUserStatusRequest createUserStatusRequest) {
        Optional<User> userOpt = userRepository.findById1(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        user.setStatus(createUserStatusRequest.getStatus());
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllParents(String role_name) {
        List<GetUserResponse> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findAllUserByRole(role_name);

        pageUser.forEach(user -> {
                GetUserResponse userResponse = GetUserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .status(user.getStatus())
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
        });
        Map<String, Object> response = new HashMap<>();
        response.put("parents", allUserResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTeacher() {
        List<GetTeacherResponse> allUserResponses = new ArrayList<>();
        List<User> pageUser = userRepository.findAllUserByRole("TEACHER_USER");
        
        List<List<GetTeacherRegisterQualificationResponse>> allTeacherRegisterQualificationResponses = new ArrayList<>();
        List<List<GetTeacherRegisterQualificationResponse>> allTeacherRegisterQualificationDoingResponses = new ArrayList<>();
        List<List<GetTeacherRegisterQualificationResponse>> allTeacherRegisterQualificationNotAcceptResponses = new ArrayList<>();
        List<TeacherRegisterQualification> pageTeacherRegisterQualification = teacherRegisterQualificationRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        pageUser.forEach(user -> {
                GetTeacherResponse userResponse = GetTeacherResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .status(user.getStatus())
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
                    if (content.getTeacher().getId().compareTo(user.getId()) == 0 && content.getStatus().equals("Accept")){
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
                    else if (content.getTeacher().getId().compareTo(user.getId()) == 0 && content.getStatus().equals("Doing")){
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
        });
        response.put("teachers", allUserResponses);
        response.put("teacher_register_qualifications", allTeacherRegisterQualificationResponses);
        response.put("teacher_register_doing_qualifications", allTeacherRegisterQualificationDoingResponses);
        response.put("teacher_register_not_accept_qualifications", allTeacherRegisterQualificationNotAcceptResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetUserInfoResponse getUserInfoByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername2(username);
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
                .parents(user.getParent().getId())
                .address(user.getAddress())
                .createTime(user.getCreateTime())
                .build();
    }

    @Override
    public GetUserInfoResponse getUserInfoById(Long id) {
        Optional<User> userOpt = userRepository.findById2(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        Long a = (long)1;
        if (user.getParent() != null) {
            a = user.getParent().getId();
        }

        return GetUserInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .sex(user.getSex())
                .phone(user.getPhone())
                .address(user.getAddress())
                .parents(a)
                .profile_image_url(user.getProfileImageUrl())
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

        Optional <User> userOpt = userRepository.findById1(createUserRequest.getParent_id());
        User parent = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.parent.not_found");
        });

        String validRoles = createUserRequest.getRoleName();



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
                .authorization(validRoles)
                .parent(parent)
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

        String validRoles = createStudentOrParentRequest.getRoleName();



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
                .authorization(validRoles)
                .build();
        userRepository.save(savedUser);
        /* String msgBody = "Chúc mừng bạn đã tạo thành công tài khoản trên KidsDrawing.\n" + "Thông tin đăng nhập của bạn: \n" + "Tên đăng nhập: " + savedUser.getUsername() + "\n" + "Mật khẩu: " + createStudentOrParentRequest.getPassword() + "\n";
        EmailDetails details = new EmailDetails(savedUser.getEmail(), msgBody, "Thông báo tạo tài khoản thành công", "");
        emailService.sendSimpleMail(details); */

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
        String password = getSaltString();
        String encodedPassword = passwordEncoder.encode(password);

        if (userRepository.existsByUsername(createTeacherRequest.getUsername())) {
            throw new UserAlreadyRegisteredException("exception.user.user_taken");
        }
        if (userRepository.existsByEmail(createTeacherRequest.getEmail())) {
            throw new UserAlreadyRegisteredException("exception.user.email_taken");
        }

        String validRoles = createTeacherRequest.getRoleName();



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
                .authorization(validRoles)
                .build();
        userRepository.save(savedUser);

        String msgBody = "Chúc mưng bạn đã tạo thành công tài khoản trên KidsDrawing.\n" + "Thông tin đăng nhập của bạn: \n" + "Tên đăng nhập: " + savedUser.getUsername() + "\n" + "Mật khẩu: " + password + "\n";
        EmailDetails details = new EmailDetails(savedUser.getEmail(), msgBody, "Thông báo tạo tài khoản thành công", "");
        emailService.sendSimpleMail(details);

        return savedUser.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername1(username);

        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        List<String> roles = new ArrayList<>();
        roles.add(user.getAuthorization());

        Collection<SimpleGrantedAuthority> authorities = authUtil.parseAuthoritiesFromRoles( new HashSet<>(roles));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    @Override
    public Long updateUser(Long id, CreateUserRequest createUserRequest) {
        Optional<User> userOpt = userRepository.findById1(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        if (createUserRequest.getPassword().equals("")){
            user.setUsername(createUserRequest.getUsername());
            user.setEmail(createUserRequest.getEmail());
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
            user.setAddress(createUserRequest.getAddress());
            user.setDateOfBirth(createUserRequest.getDateOfBirth());
            user.setProfileImageUrl(createUserRequest.getProfile_image_url());
            user.setSex(createUserRequest.getSex());
            user.setPhone(createUserRequest.getPhone());
    
            userRepository.save(user);
        }
        else {
            String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
            user.setPassword(encodedPassword);

            userRepository.save(user);
        }
        return user.getId();
    }

    @Override
    public Long updateTeacher(Long id, CreateTeacherUserRequest createTeacherUserRequest) {
        Optional<User> userOpt = userRepository.findById1(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        user.setUsername(createTeacherUserRequest.getUsername());
        user.setEmail(createTeacherUserRequest.getEmail());
        userRepository.save(user);
        return user.getId();
    }


    @Override
    public Long updatePassword(Long id, CreateChangePassowrdRequest createChangePassowrdRequest) {
        Optional<User> userOpt = userRepository.findById1(id);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        if (passwordEncoder.matches(createChangePassowrdRequest.getPre_password(), user.getPassword()) ){
            String encodedPassword = passwordEncoder.encode(createChangePassowrdRequest.getNew_password());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
        else {
            throw new EntityNotFoundException("exception.user.not_found");
        }
        return user.getId();
    }

    @Override
    public Long removeUser(Long id) {
        Optional<User> userOpt = userRepository.findById1(id);
        userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        userRepository.deleteById(id);
        return id;
    }
}