package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateHolidayRequest;
import com.app.kidsdrawing.dto.CreateSemesterRequest;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.Class;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Holiday;
import com.app.kidsdrawing.entity.Schedule;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.SectionTemplate;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassRepository;
import com.app.kidsdrawing.repository.HolidayRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.SectionTemplateRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.TeacherTeachSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final TeacherTeachSemesterRepository teacherTeachSemesterRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final SectionTemplateRepository sectionTemplateRepository;
    private final SectionRepository sectionRepository;
    private final HolidayRepository holidayRepository;

    private static int counter = 0;
    @Override
    public ResponseEntity<Map<String, Object>> getAllSemester() {
        List<GetSemesterResponse> allSemesterResponses = new ArrayList<>();
        List<Semester> pageSemester = semesterRepository.findAll();
        pageSemester.forEach(semester -> {
            GetSemesterResponse semesterResponse = GetSemesterResponse.builder()
                    .id(semester.getId())
                    .name(semester.getName())
                    .description(semester.getDescription())
                    .start_time(semester.getStart_time())
                    .number(semester.getNumber())
                    .year(semester.getYear())
                    .create_time(semester.getCreate_time())
                    .update_time(semester.getUpdate_time())
                    .creator_id(semester.getUser().getId())
                    .build();
            allSemesterResponses.add(semesterResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semesters", allSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemesterNext() {
        List<GetSemesterResponse> allSemesterResponses = new ArrayList<>();
        List<Semester> pageSemester = semesterRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        pageSemester.forEach(semester -> {
            if (now.isBefore(semester.getStart_time())) {
                GetSemesterResponse semesterResponse = GetSemesterResponse.builder()
                    .id(semester.getId())
                    .name(semester.getName())
                    .description(semester.getDescription())
                    .start_time(semester.getStart_time())
                    .number(semester.getNumber())
                    .year(semester.getYear())
                    .create_time(semester.getCreate_time())
                    .update_time(semester.getUpdate_time())
                    .creator_id(semester.getUser().getId())
                    .build();
                allSemesterResponses.add(semesterResponse);
            }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("semesters", allSemesterResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Long setCalenderForSemester(Long id, CreateHolidayRequest createHolidayResquest) {
        // Lấy học kì
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        List<Course> allCourseResponses = new ArrayList<>();
        //List<Course> pageCourse = courseRepository.findAll();
        List<SemesterClass> allSemesterClassResponses = new ArrayList<>();
        List<Schedule> allScheduleResponses = new ArrayList<>();
        //List<Schedule> pageSchedule = scheduleRepository.findAll();
        //List<LessonTime> pageLessonTime = lessonTimeRepository.findAll();

        semester.getSemesterClass().forEach(semester_course -> {
            allSemesterClassResponses.add(semester_course);
            semester_course.getSchedules().forEach(ele -> {
                allScheduleResponses.add(ele);
            });
            allCourseResponses.add(semester_course.getCourse());
        });

        List<Class> listClass = classRepository.findAll();
        List<List<Class>> allClassOfSemesterClassResponses = new ArrayList<>();        
        allSemesterClassResponses.forEach(semester_course -> {
            List<Class> list_class = new ArrayList<>();
            listClass.forEach(ele_class -> {
                if (ele_class.getTeachSemester().getSemesterClass().getId() == semester_course.getId()){
                    list_class.add(ele_class);
                }
            });
            allClassOfSemesterClassResponses.add(list_class);
        });

        createHolidayResquest.getTime().forEach(holiday -> {
            Holiday saveHoliday = Holiday.builder()
                .day(holiday)
                .semester(semester)
                .build();
            holidayRepository.save(saveHoliday);
        });

        allClassOfSemesterClassResponses.forEach( list_class -> {
            List<SectionTemplate> listSectionTemplate = sectionTemplateRepository.findAll();
            List<SectionTemplate> allSectionTemplate = new ArrayList<>();
            listSectionTemplate.forEach(section_template -> {
                if (section_template.getCourse().getId() == list_class.get(0).getTeachSemester().getSemesterClass().getCourse().getId()){
                    allSectionTemplate.add(section_template);
                    section_template.getCourse().getNum_of_section();
                }
            });
            list_class.forEach(ele -> {
                allSectionTemplate.forEach(ele_section_tmp -> {
                    Section savedSection = Section.builder()
                        .class1(ele)
                        .name(ele_section_tmp.getName())
                        .description(ele_section_tmp.getDescription())
                        .number(ele_section_tmp.getNumber())
                        .teaching_form(ele_section_tmp.getTeaching_form())
                        .build();
                    sectionRepository.save(savedSection);
                });
            });
        }); 
        return id;
    }

    public static int[][] foo(int N, int x, int m, int M){ //ham can viet
        int numOfBasicArr = N/x;
        int numOfRemain = N - numOfBasicArr*x;
        
        if (numOfRemain == 0){ //chia het k du cai nao
            int[][] mat = new int[numOfBasicArr][x];
            for (int i = 1; i<=numOfBasicArr; i++){
                for (int j = 1; j<=x;j++){
                    mat[i-1][j-1] = (i-1)*x+j;
                }
            }
            return mat;
        }
        else if (numOfRemain == x - 1 || numOfRemain >= m){
            int[][] mat = new int[numOfBasicArr + 1][];
            for (int i = 1; i<=numOfBasicArr; i++){
                mat[i-1] = new int[x];
                for (int j = 1; j<=x;j++){
                    mat[i-1][j-1] = (i-1)*x+j;
                }
            }
                mat[numOfBasicArr] = new int[numOfRemain]; //mang cuoi
                for (int i=1;i<=numOfRemain;i++){
                    mat[numOfBasicArr][i-1]=x*numOfBasicArr+i;
                }
            return mat;
        }
        else{
            int[][] mat = new int[numOfBasicArr][];
            int sophantumoinhomdcthem = numOfRemain/numOfBasicArr;
            int sonhomdcthem1phantunua = numOfRemain - sophantumoinhomdcthem*numOfBasicArr;
            //vay la co sonhomdcthem1phantunua nhom co x+sophantumoinhomdcthem+1 phan tu, con lai thi co x+sophantumoinhomdcthem phan tu.
            //cap phat
            for (int i = 1; i<=sonhomdcthem1phantunua; i++){
                mat[i-1] = new int[x+sophantumoinhomdcthem+1];
            }
            for (int i = sonhomdcthem1phantunua+1; i<=numOfBasicArr; i++){
                mat[i-1] = new int[x+sophantumoinhomdcthem];
            }
            //them phan tu co ban
            for (int i = 1; i<=numOfBasicArr; i++){
                for (int j = 1; j<=x;j++){
                    mat[i-1][j-1] = (i-1)*x+j;
                }
            }
            //them cac phan tu du vao cac nhom
            for (int i = 1; i<=sonhomdcthem1phantunua; i++){
                for (int j = 1; j<=sophantumoinhomdcthem+1;j++){
                    mat[i-1][x+j-1] = numOfBasicArr*x+i+(j-1)*numOfBasicArr;
                }
            }
            for (int i = sonhomdcthem1phantunua+1; i<=numOfBasicArr; i++){
                for (int j = 1; j<=sophantumoinhomdcthem;j++){
                    mat[i-1][x+j-1] = numOfBasicArr*x+i+(j-1)*numOfBasicArr;
                }
            }
            //check max
            if (mat[0].length>M){
                int[][] error = {{-1}};
                return error;
            }
            
            return mat;
        }
    }

    @Override
    public Long setClassForSemester(Long id, int partion, int min, int max) {
        // Lấy học kì
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        List<SemesterClass> allSemesterClassResponses = new ArrayList<>();
        semester.getSemesterClass().forEach(semester_course -> {
            allSemesterClassResponses.add(semester_course);
        });

        List<Class> listClass = classRepository.findAll();


        Optional<User> userOpt = userRepository.findByUsernameOrEmail("admin", "admin");

        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        // Danh sách học sinh đăng kí học
        List<UserRegisterJoinSemester> pageUserRegisterJoinSemesters = userRegisterJoinSemesterRepository.findAll();
        // Danh sách giáo viên đăng kí dạy
        List<UserRegisterTeachSemester> pageUserRegisterTeachSemesters = teacherTeachSemesterRepository.findAll();
        allSemesterClassResponses.forEach(semester_course -> {
            // Danh sách học sinh đăng kí 1 khóa học trong 1 học kì
            List<UserRegisterJoinSemester> allUserRegisterJoinSemesters = new ArrayList<>();
            pageUserRegisterJoinSemesters.forEach(user_register_join_semester -> {
                if (user_register_join_semester.getSemesterClass().getId() == semester_course.getId()){
                    allUserRegisterJoinSemesters.add(user_register_join_semester);
                }
            });

            // Danh sách giáo viên đăng kí dạy 1 khóa học trong 1 học kì
            List<UserRegisterTeachSemester> allUserRegisterTeachSemesters = new ArrayList<>();
            pageUserRegisterTeachSemesters.forEach(user_register_teach_semester -> {
                if (user_register_teach_semester.getSemesterClass().getId() == semester_course.getId()){
                    allUserRegisterTeachSemesters.add(user_register_teach_semester);
                }
            });

            List<Integer> list_total_register_of_teacher = new ArrayList<>();
            allUserRegisterTeachSemesters.forEach(teacher_register_teach_semester -> {
                counter = 0;
                listClass.forEach(class_x -> {
                    if (teacher_register_teach_semester.getTeacher().getId() == class_x.getTeachSemester().getId()){
                        counter += 1;
                    }
                });
                list_total_register_of_teacher.add(counter);
            });

            for (int i = 0; i < list_total_register_of_teacher.size(); i++) {
                // Inner nested loop pointing 1 index ahead
                for (int j = i + 1; j < list_total_register_of_teacher.size(); j++) {
                    // Checking elements
                    if (list_total_register_of_teacher.get(j) < list_total_register_of_teacher.get(i)) {
                        Collections.swap(list_total_register_of_teacher, i, j);
                        Collections.swap(allUserRegisterTeachSemesters, i, j);
                    }
                }
                // Printing sorted array elements
                //System.out.print(arr[i] + " ");
            }

            // Lấy các group có thể chia được
            int[][] list_group = foo(allUserRegisterJoinSemesters.size(), partion, min, max);
            // Số lớp có thể chia
            int total_class = list_group.length;
            for (int i = 0; i < total_class; i++) {
                //Danh sách học viên lớp thứ i
                List<UserRegisterJoinSemester> validUserRegisterSemesters = new ArrayList<>(); 
                for(int j = 0; j < list_group[i].length; j++){
                    int idx = list_group[i][j] - 1;
                    validUserRegisterSemesters.add(allUserRegisterJoinSemesters.get(idx));
                }
                if (i < allUserRegisterTeachSemesters.size()){
                    String key = getSaltString();
                    Class savedClass = Class.builder()
                        .user(user)
                        .teachSemester(allUserRegisterTeachSemesters.get(i))
                        .security_code(key)
                        .name("CM" + "_" + String.valueOf(listClass.size() + i))
                        .userRegisterJoinSemesters(new HashSet<>(validUserRegisterSemesters))
                        .build();
                    classRepository.save(savedClass);
                }
            }
        });
        return id;
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
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
    public GetSemesterResponse getSemesterById(Long id){
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        return GetSemesterResponse.builder()
                .id(semester.getId())
                .name(semester.getName())
                .description(semester.getDescription())
                .start_time(semester.getStart_time())
                .number(semester.getNumber())
                .year(semester.getYear())
                .create_time(semester.getCreate_time())
                .update_time(semester.getUpdate_time())
                .creator_id(semester.getUser().getId())
                .build();
    }

    @Override
    public Long createSemester(CreateSemesterRequest createSemesterRequest) {
        Optional<User> userOpt = userRepository.findById(createSemesterRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Semester savedSemester = Semester.builder()
                .name(createSemesterRequest.getName())
                .description(createSemesterRequest.getDescription())
                .number(createSemesterRequest.getNumber())
                .year(createSemesterRequest.getYear())
                .start_time(createSemesterRequest.getStart_time())
                .user(user)
                .build();
        semesterRepository.save(savedSemester);

        return savedSemester.getId();
    }

    @Override
    public Long removeSemesterById(Long id) {
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        semesterRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterById(Long id, CreateSemesterRequest createSemesterRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        Semester updatedSemester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        Optional<User> userOpt = userRepository.findById(createSemesterRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedSemester.setName(createSemesterRequest.getName());
        updatedSemester.setDescription(createSemesterRequest.getDescription());
        updatedSemester.setNumber(createSemesterRequest.getNumber());
        updatedSemester.setYear(createSemesterRequest.getYear());
        updatedSemester.setStart_time(createSemesterRequest.getStart_time());
        updatedSemester.setUser(user);
        semesterRepository.save(updatedSemester);

        return updatedSemester.getId();
    }
}
