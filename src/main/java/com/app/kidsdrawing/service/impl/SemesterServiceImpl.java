package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateHolidayRequest;
import com.app.kidsdrawing.dto.CreateSemesterRequest;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.Tutorial;
import com.app.kidsdrawing.entity.TutorialPage;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserAttendance;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;
import com.app.kidsdrawing.entity.Course;
import com.app.kidsdrawing.entity.Holiday;
import com.app.kidsdrawing.entity.Schedule;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.SectionTemplate;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.HolidayRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.SectionTemplateRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.repository.TutorialPageRepository;
import com.app.kidsdrawing.repository.TutorialRepository;
import com.app.kidsdrawing.repository.UserAttendanceRepository;
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
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final ClassesRepository classRepository;
    private final UserRepository userRepository;
    private final SectionTemplateRepository sectionTemplateRepository;
    private final SectionRepository sectionRepository;
    private final HolidayRepository holidayRepository;
    private final UserAttendanceRepository userAttendanceRepository;
    //private final EmailService emailService;
    //private final FCMService fcmService;
    private final TutorialRepository tutorialRepository;
    private final TutorialPageRepository tutorialPageRepository;
    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;

    private static int counter = 0;
    private static int check_count = 0;
    private static int number = 1;
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
                    .end_time(semester.getEnd_time())
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
                    .end_time(semester.getEnd_time())
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
        //List<Course> pageCourse = classRepository.findAll();
        List<SemesterClass> allSemesterClassResponses = new ArrayList<>();
        List<Schedule> allScheduleResponses = new ArrayList<>();
        //List<Schedule> pageSchedule = scheduleRepository.findAll();
        //List<LessonTime> pageLessonTime = lessonTimeRepository.findAll();

        semester.getSemesterClass().forEach(semester_class -> {
            allSemesterClassResponses.add(semester_class);
            semester_class.getSchedules().forEach(ele -> {
                allScheduleResponses.add(ele);
            });
            allCourseResponses.add(semester_class.getCourse());
        });

        List<Classes> listClass = classRepository.findAll();
        List<List<Classes>> allClassOfSemesterClassResponses = new ArrayList<>();        
        allSemesterClassResponses.forEach(semester_class -> {
            List<Classes> list_class = new ArrayList<>();
            listClass.forEach(ele_class -> {
                if (ele_class.getUserRegisterTeachSemester().getSemesterClass().getId() == semester_class.getId()){
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

        Optional <User> userOpt = userRepository.findById((long) 1);
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        });

        allClassOfSemesterClassResponses.forEach( list_class -> {
            List<SectionTemplate> listSectionTemplate = sectionTemplateRepository.findAll();
            List<SectionTemplate> allSectionTemplate = new ArrayList<>();
            listSectionTemplate.forEach(section_template -> {
                if (section_template.getCourse().getId() == list_class.get(0).getUserRegisterTeachSemester().getSemesterClass().getCourse().getId()){
                    allSectionTemplate.add(section_template);
                    section_template.getCourse().getNum_of_section();
                }
            });
            list_class.forEach(ele -> {
                allSectionTemplate.forEach(ele_section_tmp -> {
                    Section savedSection = Section.builder()
                        .classes(ele)
                        .name(ele_section_tmp.getName())
                        .number(ele_section_tmp.getNumber())
                        .teaching_form(ele_section_tmp.getTeaching_form())
                        .build();
                    sectionRepository.save(savedSection);

                    Tutorial savedTutorial = Tutorial.builder()
                        .section(savedSection)
                        .creator(creator)
                        .name("Giáo trình " + ele_section_tmp.getTutorialTemplates().getName())
                        .build();
                    tutorialRepository.save(savedTutorial);

                    ele_section_tmp.getTutorialTemplates().getTutorialTemplatePages().forEach(tutorial_page -> {
                        TutorialPage savedTutorialPage = TutorialPage.builder()
                            .tutorial(savedTutorial)
                            .name(tutorial_page.getName())
                            .description(tutorial_page.getDescription())
                            .number(tutorial_page.getNumber())
                            .build();
                        tutorialPageRepository.save(savedTutorialPage);
                    });
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

    public static void print2D(int[][] matrix){//in mang 2 chieu
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print('\n');
        }
    }

    @Override
    public Long setClassForSemester(Long id, int partion, int min, int max, CreateHolidayRequest createHolidayResquest) {
        // Lấy học kì
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        Set<SemesterClass> allSemesterClassResponses = semester.getSemesterClass();

        System.out.println("Số lớp mở: " + String.valueOf(allSemesterClassResponses.size()));

        List<Classes> listClassOfSemesterClass = new ArrayList<>();
        
        createHolidayResquest.getTime().forEach(holiday -> {
            Holiday saveHoliday = Holiday.builder()
                .day(holiday)
                .semester(semester)
                .build();
            holidayRepository.save(saveHoliday);
        }); 

        Optional <User> userOpt = userRepository.findById((long) 1);
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        }); 

        // Danh sách học sinh đăng kí học
        // Danh sách giáo viên đăng kí dạy
        check_count = 0;
        number = 1;
        allSemesterClassResponses.forEach(semester_class -> {
            // Danh sách học sinh đăng kí 1 khóa học trong 1 học kì
            List<UserRegisterJoinSemester> allUserRegisterJoinSemesters = userRegisterJoinSemesterRepository.findBySemesterClassId(semester_class.getId());

            List<UserRegisterJoinSemester> listUserRegisterJoinSemesters = new ArrayList<>();


            allUserRegisterJoinSemesters.forEach(ele -> {
                if (ele.getStatus().equals("Completed")) {
                    listUserRegisterJoinSemesters.add(ele);
                }
            });

            // Danh sách giáo viên đăng kí dạy 1 khóa học trong 1 học kì
            List<UserRegisterTeachSemester> allUserRegisterTeachSemesters = userRegisterTeachSemesterRepository.findBySemesterClassId(semester_class.getId());

            List<Integer> list_total_register_of_teacher = new ArrayList<>();
            if (check_count == 0){
                allUserRegisterTeachSemesters.forEach(teacher_register_teach_semester -> {
                    list_total_register_of_teacher.add(0);
                });
            }
            else {
                allUserRegisterTeachSemesters.forEach(teacher_register_teach_semester -> {
                    counter = 0;
                    listClassOfSemesterClass.forEach(class_x -> {
                        if (teacher_register_teach_semester.getTeacher().getId() == class_x.getUserRegisterTeachSemester().getTeacher().getId()){
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
                }
            }

            System.out.println("Id lop mo: " + String.valueOf(semester_class.getId()));
            System.out.println("So luong giao vien dang ki: " + String.valueOf(allUserRegisterTeachSemesters.size()));
            System.out.println("So hoc sinh dang ki mo lop: " + String.valueOf(listUserRegisterJoinSemesters.size()));

            // Lấy các group có thể chia được
            if (listUserRegisterJoinSemesters.size() >= min) {
                int[][] list_group = foo(listUserRegisterJoinSemesters.size(), partion, min, max);
                System.out.println(" Ket qua chi lop: " + String.valueOf(list_group.length));
                print2D(list_group);
                // Số lớp có thể chia
                int total_class = list_group.length;
                for (int i = 0; i < allUserRegisterTeachSemesters.size(); i++) {
                    //Danh sách học viên lớp thứ i
                    if (i < total_class){
                        List<UserRegisterJoinSemester> validUserRegisterSemesters = new ArrayList<>(); 
                        for(int j = 0; j < list_group[i].length; j++){
                            int idx = list_group[i][j] - 1;
                            validUserRegisterSemesters.add(listUserRegisterJoinSemesters.get(idx));
                        }
                        System.out.println("Số học sinh đc xếp: " + String.valueOf(validUserRegisterSemesters.size()));
                        String key = getSaltString();
                        System.out.println("Lớp thứ: " + String.valueOf(i));
                        Classes savedClass = Classes.builder()
                            .user(creator)
                            .userRegisterTeachSemester(allUserRegisterTeachSemesters.get(i))
                            .security_code(key)
                            .link_meeting("https://meet.jit.si/" + String.valueOf(key))
                            .name(semester_class.getName() + "-" +  String.valueOf(number) + " thuộc học kì " + String.valueOf(semester.getNumber()) + " năm học " + String.valueOf(semester.getYear()))
                            .build();
                        classRepository.save(savedClass);
    
                        validUserRegisterSemesters.forEach(user_register_semester -> {
                            ClassHasRegisterJoinSemesterClassKey idx = new ClassHasRegisterJoinSemesterClassKey(savedClass.getId(),user_register_semester.getId());
                            
                            ClassHasRegisterJoinSemesterClass savedClassHasRegisterJoinSemesterClass = ClassHasRegisterJoinSemesterClass.builder()
                                .id(idx)
                                .classes(savedClass)
                                .userRegisterJoinSemester(user_register_semester)
                                .review_star(0)
                                .build();
                            classHasRegisterJoinSemesterClassRepository.save(savedClassHasRegisterJoinSemesterClass);
                        });
                        number ++;
                        listClassOfSemesterClass.add(savedClass);
    
                        List<SectionTemplate> sectionTemplateOpt = sectionTemplateRepository.findByCourseId(semester_class.getCourse().getId());
                        sectionTemplateOpt.forEach(section_template -> {
                            Section savedSection = Section.builder()
                                .classes(savedClass)
                                .name(section_template.getName())
                                .number(section_template.getNumber())
                                .teaching_form(section_template.getTeaching_form())
                                .build();
                            sectionRepository.save(savedSection);
    
                            validUserRegisterSemesters.forEach(user_register_semester -> {
                                UserAttendance savedUserAttendance = UserAttendance.builder()
                                    .section(savedSection)
                                    .student(user_register_semester.getStudent())
                                    .status(false)
                                    .build();
                                userAttendanceRepository.save(savedUserAttendance);
                            });
                            
                            Tutorial savedTutorial = Tutorial.builder()
                                .section(savedSection)
                                .creator(creator)
                                .name("Giáo trình " + section_template.getTutorialTemplates().getName())
                                .build();
                            tutorialRepository.save(savedTutorial);
                            
                            section_template.getTutorialTemplates().getTutorialTemplatePages().forEach(tutorial_page -> {
                                TutorialPage savedTutorialPage = TutorialPage.builder()
                                    .tutorial(savedTutorial)
                                    .name(tutorial_page.getName())
                                    .description(tutorial_page.getDescription())
                                    .number(tutorial_page.getNumber())
                                    .build();
                                tutorialPageRepository.save(savedTutorialPage);
                            });
                        });
                    }
                    else {
                        
                    }
                } 
            } 
            check_count ++; 
            System.out.println("Lan: " + String.valueOf(check_count));
            //throw new EntityNotFoundException("exception.end.end"); 
        });
        return (long) check_count; 
    }



    @Override
    public Long setClassForSemesterHeroku(Long id, int partion, int min, int max) {
        // Lấy học kì
        Optional<Semester> semesterOpt = semesterRepository.findById(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        Set<SemesterClass> allSemesterClassResponses = semester.getSemesterClass();

        System.out.println("Số lớp mở: " + String.valueOf(allSemesterClassResponses.size()));

        List<Classes> listClassOfSemesterClass = new ArrayList<>();
        

        Optional <User> userOpt = userRepository.findById((long) 1);
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        }); 

        // Danh sách học sinh đăng kí học
        // Danh sách giáo viên đăng kí dạy
        check_count = 0;
        number = 1;
        allSemesterClassResponses.forEach(semester_class -> {
            // Danh sách học sinh đăng kí 1 khóa học trong 1 học kì
            List<UserRegisterJoinSemester> allUserRegisterJoinSemesters = userRegisterJoinSemesterRepository.findBySemesterClassId(semester_class.getId());

            List<UserRegisterJoinSemester> listUserRegisterJoinSemesters = new ArrayList<>();


            allUserRegisterJoinSemesters.forEach(ele -> {
                if (ele.getStatus().equals("Completed")) {
                    listUserRegisterJoinSemesters.add(ele);
                }
            });

            // Danh sách giáo viên đăng kí dạy 1 khóa học trong 1 học kì
            List<UserRegisterTeachSemester> allUserRegisterTeachSemesters = userRegisterTeachSemesterRepository.findBySemesterClassId(semester_class.getId());

            List<Integer> list_total_register_of_teacher = new ArrayList<>();
            if (check_count == 0){
                allUserRegisterTeachSemesters.forEach(teacher_register_teach_semester -> {
                    list_total_register_of_teacher.add(0);
                });
            }
            else {
                allUserRegisterTeachSemesters.forEach(teacher_register_teach_semester -> {
                    counter = 0;
                    listClassOfSemesterClass.forEach(class_x -> {
                        if (teacher_register_teach_semester.getTeacher().getId() == class_x.getUserRegisterTeachSemester().getTeacher().getId()){
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
                }
            }

            System.out.println("Id lop mo: " + String.valueOf(semester_class.getId()));
            System.out.println("So luong giao vien dang ki: " + String.valueOf(allUserRegisterTeachSemesters.size()));
            System.out.println("So hoc sinh dang ki mo lop: " + String.valueOf(listUserRegisterJoinSemesters.size()));

            // Lấy các group có thể chia được
            if (listUserRegisterJoinSemesters.size() >= min) {
                int[][] list_group = foo(listUserRegisterJoinSemesters.size(), partion, min, max);
                System.out.println(" Ket qua chi lop: " + String.valueOf(list_group.length));
                print2D(list_group);
                // Số lớp có thể chia
                int total_class = list_group.length;
                for (int i = 0; i < allUserRegisterTeachSemesters.size(); i++) {
                    //Danh sách học viên lớp thứ i
                    if (i < total_class){
                        List<UserRegisterJoinSemester> validUserRegisterSemesters = new ArrayList<>(); 
                        for(int j = 0; j < list_group[i].length; j++){
                            int idx = list_group[i][j] - 1;
                            validUserRegisterSemesters.add(listUserRegisterJoinSemesters.get(idx));
                        }
                        System.out.println("Số học sinh đc xếp: " + String.valueOf(validUserRegisterSemesters.size()));
                        String key = getSaltString();
                        System.out.println("Lớp thứ: " + String.valueOf(i));
                        Classes savedClass = Classes.builder()
                            .user(creator)
                            .userRegisterTeachSemester(allUserRegisterTeachSemesters.get(i))
                            .security_code(key)
                            .link_meeting("https://meet.jit.si/" + String.valueOf(key))
                            .name(semester_class.getName() + "-" +  String.valueOf(number) + " thuộc học kì " + String.valueOf(semester.getNumber()) + " năm học " + String.valueOf(semester.getYear()))
                            .build();
                        classRepository.save(savedClass);
    
                        validUserRegisterSemesters.forEach(user_register_semester -> {
                            ClassHasRegisterJoinSemesterClassKey idx = new ClassHasRegisterJoinSemesterClassKey(savedClass.getId(),user_register_semester.getId());
                            
                            ClassHasRegisterJoinSemesterClass savedClassHasRegisterJoinSemesterClass = ClassHasRegisterJoinSemesterClass.builder()
                                .id(idx)
                                .classes(savedClass)
                                .userRegisterJoinSemester(user_register_semester)
                                .review_star(0)
                                .build();
                            classHasRegisterJoinSemesterClassRepository.save(savedClassHasRegisterJoinSemesterClass);
                        });
                        number ++;
                        listClassOfSemesterClass.add(savedClass);
    
                        List<SectionTemplate> sectionTemplateOpt = sectionTemplateRepository.findByCourseId(semester_class.getCourse().getId());
                        sectionTemplateOpt.forEach(section_template -> {
                            Section savedSection = Section.builder()
                                .classes(savedClass)
                                .name(section_template.getName())
                                .number(section_template.getNumber())
                                .teaching_form(section_template.getTeaching_form())
                                .build();
                            sectionRepository.save(savedSection);
    
                            validUserRegisterSemesters.forEach(user_register_semester -> {
                                UserAttendance savedUserAttendance = UserAttendance.builder()
                                    .section(savedSection)
                                    .student(user_register_semester.getStudent())
                                    .status(false)
                                    .build();
                                userAttendanceRepository.save(savedUserAttendance);
                            });
                            
                            Tutorial savedTutorial = Tutorial.builder()
                                .section(savedSection)
                                .creator(creator)
                                .name("Giáo trình " + section_template.getTutorialTemplates().getName())
                                .build();
                            tutorialRepository.save(savedTutorial);
                            
                            section_template.getTutorialTemplates().getTutorialTemplatePages().forEach(tutorial_page -> {
                                TutorialPage savedTutorialPage = TutorialPage.builder()
                                    .tutorial(savedTutorial)
                                    .name(tutorial_page.getName())
                                    .description(tutorial_page.getDescription())
                                    .number(tutorial_page.getNumber())
                                    .build();
                                tutorialPageRepository.save(savedTutorialPage);
                            });
                        });
                    }
                    else {
                        
                    }
                } 
            } 
            check_count ++; 
            System.out.println("Lan: " + String.valueOf(check_count));
            //throw new EntityNotFoundException("exception.end.end"); 
        });
        return (long) check_count; 
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
                .end_time(semester.getEnd_time())
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
                .end_time(createSemesterRequest.getEnd_time())
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
        updatedSemester.setEnd_time(createSemesterRequest.getEnd_time());
        updatedSemester.setUser(user);
        semesterRepository.save(updatedSemester);

        return updatedSemester.getId();
    }
}
