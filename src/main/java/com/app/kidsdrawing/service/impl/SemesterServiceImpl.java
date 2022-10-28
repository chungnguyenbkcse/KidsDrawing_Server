package com.app.kidsdrawing.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import com.app.kidsdrawing.entity.TutorialTemplate;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.entity.UserAttendance;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;
import com.app.kidsdrawing.entity.Holiday;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.SectionTemplate;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.HolidayRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.TutorialPageRepository;
import com.app.kidsdrawing.repository.TutorialRepository;
import com.app.kidsdrawing.repository.UserAttendanceRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final ClassesRepository classRepository;
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final HolidayRepository holidayRepository;
    private final UserAttendanceRepository userAttendanceRepository;
    //private final EmailService emailService;
    //private final FCMService fcmService;
    private final TutorialRepository tutorialRepository;
    private final TutorialPageRepository tutorialPageRepository;
    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;

    //private static int counter = 0;
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


    public static int[][] foo(int N, int x, int m, int M) { // ham can viet
        int numOfBasicArr = N / x;
        int numOfRemain = N - numOfBasicArr * x;

        if (numOfRemain == 0) { // chia het k du cai nao
            int[][] mat = new int[numOfBasicArr][x];
            for (int i = 1; i <= numOfBasicArr; i++) {
                for (int j = 1; j <= x; j++) {
                    mat[i - 1][j - 1] = (i - 1) * x + j;
                }
            }
            return mat;
        } else if (numOfRemain == x - 1 || numOfRemain >= m) {
            int[][] mat = new int[numOfBasicArr + 1][];
            for (int i = 1; i <= numOfBasicArr; i++) {
                mat[i - 1] = new int[x];
                for (int j = 1; j <= x; j++) {
                    mat[i - 1][j - 1] = (i - 1) * x + j;
                }
            }
            mat[numOfBasicArr] = new int[numOfRemain]; // mang cuoi
            for (int i = 1; i <= numOfRemain; i++) {
                mat[numOfBasicArr][i - 1] = x * numOfBasicArr + i;
            }
            return mat;
        } else {
            int[][] mat = new int[numOfBasicArr][];
            int sophantumoinhomdcthem = numOfRemain / numOfBasicArr;
            int sonhomdcthem1phantunua = numOfRemain - sophantumoinhomdcthem * numOfBasicArr;
            // vay la co sonhomdcthem1phantunua nhom co x+sophantumoinhomdcthem+1 phan tu,
            // con lai thi co x+sophantumoinhomdcthem phan tu.
            // cap phat
            for (int i = 1; i <= sonhomdcthem1phantunua; i++) {
                mat[i - 1] = new int[x + sophantumoinhomdcthem + 1];
            }
            for (int i = sonhomdcthem1phantunua + 1; i <= numOfBasicArr; i++) {
                mat[i - 1] = new int[x + sophantumoinhomdcthem];
            }
            // them phan tu co ban
            for (int i = 1; i <= numOfBasicArr; i++) {
                for (int j = 1; j <= x; j++) {
                    mat[i - 1][j - 1] = (i - 1) * x + j;
                }
            }
            // them cac phan tu du vao cac nhom
            for (int i = 1; i <= sonhomdcthem1phantunua; i++) {
                for (int j = 1; j <= sophantumoinhomdcthem + 1; j++) {
                    mat[i - 1][x + j - 1] = numOfBasicArr * x + i + (j - 1) * numOfBasicArr;
                }
            }
            for (int i = sonhomdcthem1phantunua + 1; i <= numOfBasicArr; i++) {
                for (int j = 1; j <= sophantumoinhomdcthem; j++) {
                    mat[i - 1][x + j - 1] = numOfBasicArr * x + i + (j - 1) * numOfBasicArr;
                }
            }
            // check max
            if (mat[0].length > M) {
                ////// cho moi vua sua xong
                for (int i = m; i <= M; i++) {// bat dau cho so phan tu tang dan tu m den M
                    int[][] tempMat = new int[numOfBasicArr][];
                    tempMat = foo(N, i, m, M);
                    if (tempMat.length >= 2 && tempMat[tempMat.length - 1].length >= m) {// kiem tra nhom cuoi co so
                                                                                         // phan tu >= m khong?
                        return tempMat;
                    }
                }
                int[][] error = { { -1 } };
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

    public static List<List<Integer>> foo1(Integer N, Integer partitionSize, Integer partitionSizeMin, Integer partitionSizeMax) {
        List<Integer> collection = IntStream.rangeClosed(1, N)
                .boxed().collect(Collectors.toList());

        List<List<Integer>> partitions = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
 
        for (int i = 0; i < collection.size(); i += partitionSize) {
            partitions.add(collection.subList(i, Math.min(i + partitionSize,
                    collection.size())));
        }

        List<Integer> item_1 = new ArrayList<>();
        int idx = 0;
        if (partitions.get(partitions.size() - 1).size() < partitionSizeMin) {
            item_1 = partitions.get(partitions.size()-1);
            res = partitions.subList(0, partitions.size()-1);
            for (int i = 0; i < res.size(); i++) {
                if (res.get(0).size() + 1 <= partitionSizeMax && idx < item_1.size()) {
                    List<Integer> item_2 = new ArrayList<>();
                    item_2.addAll(res.get(0));
                    item_2.add(item_1.get(idx));
                    res.remove(0);
                    res.add(item_2);
                    idx ++;
                }

                if (res.get(0).size() + 1 > partitionSizeMax && idx < item_1.size()) {
                    res.add(item_1.subList(idx, item_1.size()));
                    break;
                }
            }       
        }
        else {
            res = partitions;
        }

        return res;
    }

    @Override
    public Long setClassForSemester(Long id, int partion, int min, int max, CreateHolidayRequest createHolidayResquest) {
        // Lấy học kì
        Optional<Semester> semesterOpt = semesterRepository.findById3(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        Set<SemesterClass> allSemesterClassBySemester = semester.getSemesterClass();

        System.out.println("Total semester class in semester: " + String.valueOf(allSemesterClassBySemester.size()));
        
        createHolidayResquest.getTime().forEach(holiday -> {
            Holiday saveHoliday = Holiday.builder()
                .day(holiday)
                .semester(semester)
                .build();
            holidayRepository.save(saveHoliday);
        }); 

        Optional <User> userOpt = userRepository.findByUsername1("admin");
        User creator = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user_creator.not_found");
        }); 

        // Danh sách học sinh đăng kí học
        // Danh sách giáo viên đăng kí dạy
        check_count = 0;
        number = 1;
        allSemesterClassBySemester.forEach(semester_class -> {
            // Danh sách học sinh đăng kí 1 khóa học trong 1 học kì
            Set<UserRegisterJoinSemester> listUserRegisterJoinSemesters = semester_class.getUserRegisterJoinSemesters();

            // Danh sách giáo viên đăng kí dạy 1 khóa học trong 1 học kì
            Set<UserRegisterTeachSemester> allUserRegisterTeachSemesters = semester_class.getUserRegisterTeachSemesters();

            /* List<Integer> list_total_register_of_teacher = new ArrayList<>();

            if (check_count == 0){
                allUserRegisterTeachSemesters.forEach(teacher_register_teach_semester -> {
                    list_total_register_of_teacher.add(0);
                });
            }
            else {
                allUserRegisterTeachSemesters.forEach(teacher_register_teach_semester -> {
                    counter = 0;
                    listClassOfSemesterClass.forEach(class_x -> {
                        if (teacher_register_teach_semester.getTeacher().getId().compareTo(class_x.getUserRegisterTeachSemester().getTeacher().getId()) == 0){
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
                            Collections.swap(new ArrayList<>(allUserRegisterTeachSemesters), i, j);
                        }
                    }
                }
            } */

            System.out.println("class_id: " + String.valueOf(semester_class.getId()));
            System.out.println("Total user_register_teach_semester: " + String.valueOf(allUserRegisterTeachSemesters.size()));
            System.out.println("Total user_register_join_semester: " + String.valueOf(listUserRegisterJoinSemesters.size()));

            // Lấy các group có thể chia được
            if (listUserRegisterJoinSemesters.size() >= min) {
                List<List<Integer>> all_group = foo1(listUserRegisterJoinSemesters.size(), partion, min, max);
                List<List<Integer>> list_group = new ArrayList<>();
                if (all_group.get(all_group.size() - 1).size() < min) {
                    list_group = all_group.subList(0, all_group.size() - 1);
                }
                else {
                    list_group = all_group;
                }
                System.out.println(" Ket qua chi lop: " + String.valueOf(list_group.size()));
                System.out.println(list_group);
                // Số lớp có thể chia
                int total_class = list_group.size();
                
                for (int i = 0; i < allUserRegisterTeachSemesters.size(); i++) {
                    //Danh sách học viên lớp thứ i
                    if (i < total_class){
                        List<UserRegisterJoinSemester> validUserRegisterSemesters = new ArrayList<>(); 
                        for(int j = 0; j < list_group.get(i).size(); j++){
                            int idx = list_group.get(i).get(j) - 1;
                            validUserRegisterSemesters.add(new ArrayList<>(listUserRegisterJoinSemesters).get(idx));
                        }
                        System.out.println("So hoc sinh duoc xep: " + String.valueOf(validUserRegisterSemesters.size()));
                        String key = getSaltString();
                        System.out.println("Lop thu: " + String.valueOf(i));
                        Classes savedClass = Classes.builder()
                            .user(creator)
                            .userRegisterTeachSemester(new ArrayList<>(allUserRegisterTeachSemesters).get(i))
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
                        //listClassOfSemesterClass.add(savedClass);
    
                        Set<SectionTemplate> sectionTemplateOpt = semester_class.getCourse().getSectionTemplates();
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
                                .name("Giáo trình " + section_template.getName())
                                .build();
                            tutorialRepository.save(savedTutorial);

                            TutorialTemplate tutorialTemplate = section_template.getTutorialTemplate();

                            tutorialTemplate.getTutorialTemplatePages().forEach(tutorial_page -> {
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
            System.out.println("Loop: " + String.valueOf(check_count));
            //throw new EntityNotFoundException("exception.end.end"); 
        });
        return (long)1; 
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
        Optional<Semester> semesterOpt = semesterRepository.findById2(id);
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
        Optional<User> userOpt = userRepository.findById1(createSemesterRequest.getCreator_id());
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
        Optional<Semester> semesterOpt = semesterRepository.findById1(id);
        semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        semesterRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterById(Long id, CreateSemesterRequest createSemesterRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById1(id);
        Semester updatedSemester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        Optional<User> userOpt = userRepository.findById1(createSemesterRequest.getCreator_id());
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
