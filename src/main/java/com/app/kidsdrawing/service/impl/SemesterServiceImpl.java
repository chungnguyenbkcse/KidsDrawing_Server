package com.app.kidsdrawing.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateSemesterRequest;
import com.app.kidsdrawing.dto.GetSemesterResponse;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.entity.SemesterClass;
import com.app.kidsdrawing.entity.Teacher;
import com.app.kidsdrawing.entity.UserAttendance;
import com.app.kidsdrawing.entity.UserAttendanceKey;
import com.app.kidsdrawing.entity.UserReadNotification;
import com.app.kidsdrawing.entity.UserReadNotificationKey;
import com.app.kidsdrawing.entity.Classes;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClass;
import com.app.kidsdrawing.entity.ClassHasRegisterJoinSemesterClassKey;
import com.app.kidsdrawing.entity.Holiday;
import com.app.kidsdrawing.entity.Notification;
import com.app.kidsdrawing.entity.Section;
import com.app.kidsdrawing.entity.SectionTemplate;
import com.app.kidsdrawing.entity.UserRegisterJoinSemester;
import com.app.kidsdrawing.entity.UserRegisterTeachSemester;
import com.app.kidsdrawing.exception.ArtAgeNotDeleteException;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ClassHasRegisterJoinSemesterClassRepository;
import com.app.kidsdrawing.repository.ClassesRepository;
import com.app.kidsdrawing.repository.HolidayRepository;
import com.app.kidsdrawing.repository.NotificationRepository;
import com.app.kidsdrawing.repository.SectionRepository;
import com.app.kidsdrawing.repository.SectionTemplateRepository;
import com.app.kidsdrawing.repository.SemesterClassRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.repository.UserAttendanceRepository;
import com.app.kidsdrawing.repository.UserReadNotificationRepository;
import com.app.kidsdrawing.repository.UserRegisterJoinSemesterRepository;
import com.app.kidsdrawing.repository.UserRegisterTeachSemesterRepository;
import com.app.kidsdrawing.service.SemesterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterClassRepository semesterClassRepository;
    private final ClassesRepository classRepository;
    private final SectionRepository sectionRepository;
    private final HolidayRepository holidayRepository;
    private final UserAttendanceRepository userAttendanceRepository;
    private final UserReadNotificationRepository uuserReadNotificationRepository;
    private final NotificationRepository notificationRepository;
    private final SectionTemplateRepository sectionTemplateRepository;
    private final UserRegisterJoinSemesterRepository userRegisterJoinSemesterRepository;
    private final UserRegisterTeachSemesterRepository userRegisterTeachSemesterRepository;
    private final ClassHasRegisterJoinSemesterClassRepository classHasRegisterJoinSemesterClassRepository;

    // private static int counter = 0;
    private static int check_count = 0;
    private static int number = 1;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSemester() {
        List<GetSemesterResponse> allSemesterResponses = new ArrayList<>();
        List<Semester> pageSemester = semesterRepository.findAll();

        pageSemester.forEach(semester -> {
            List<Holiday> pageHoliday = holidayRepository.findBySemesterId(semester.getId());
            List<LocalDate> holidayTime = new ArrayList<>();
            pageHoliday.forEach(holiday -> {
                holidayTime.add(holiday.getDay());
            });
            GetSemesterResponse semesterResponse = GetSemesterResponse.builder()
                    .id(semester.getId())
                    .name(semester.getName())
                    .holiday(new HashSet<>(holidayTime))
                    .description(semester.getDescription())
                    .start_time(semester.getStart_time())
                    .end_time(semester.getEnd_time())
                    .number(semester.getNumber())
                    .year(semester.getYear())
                    .create_time(semester.getCreate_time())
                    .update_time(semester.getUpdate_time())

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
                int total_class = classRepository.findAllBySemester(semester.getId()).size();
                List<Holiday> pageHoliday = holidayRepository.findBySemesterId(semester.getId());
                List<LocalDate> holidayTime = new ArrayList<>();
                pageHoliday.forEach(holiday -> {
                    holidayTime.add(holiday.getDay());
                });
                if (total_class > 0) {

                    GetSemesterResponse semesterResponse = GetSemesterResponse.builder()
                            .id(semester.getId())
                            .name(semester.getName())
                            .checked_genaration(true)
                            .description(semester.getDescription())
                            .holiday(new HashSet<>(holidayTime))
                            .start_time(semester.getStart_time())
                            .end_time(semester.getEnd_time())
                            .number(semester.getNumber())
                            .year(semester.getYear())
                            .create_time(semester.getCreate_time())
                            .update_time(semester.getUpdate_time())
                            .build();
                    allSemesterResponses.add(semesterResponse);
                } else {
                    GetSemesterResponse semesterResponse = GetSemesterResponse.builder()
                            .id(semester.getId())
                            .name(semester.getName())
                            .checked_genaration(false)
                            .description(semester.getDescription())
                            .start_time(semester.getStart_time())
                            .end_time(semester.getEnd_time())
                            .holiday(new HashSet<>(holidayTime))
                            .number(semester.getNumber())
                            .year(semester.getYear())
                            .create_time(semester.getCreate_time())
                            .update_time(semester.getUpdate_time())
                            .build();
                    allSemesterResponses.add(semesterResponse);
                }
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

    public static void print2D(int[][] matrix) {// in mang 2 chieu
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print('\n');
        }
    }

    public static List<List<Integer>> foo1(Integer N, Integer partitionSize, Integer partitionSizeMin,
            Integer partitionSizeMax) {
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
            item_1 = partitions.get(partitions.size() - 1);
            res = partitions.subList(0, partitions.size() - 1);
            while (idx < item_1.size()) {
                for (int i = 0; i < res.size(); i++) {
                    if (res.get(0).size() + 1 <= partitionSizeMax && idx < item_1.size()) {
                        List<Integer> item_2 = new ArrayList<>();
                        item_2.addAll(res.get(0));
                        item_2.add(item_1.get(idx));
                        res.remove(0);
                        res.add(item_2);
                        idx++;
                    }

                    if (res.get(0).size() + 1 > partitionSizeMax && idx < item_1.size()) {
                        res.add(item_1.subList(idx, item_1.size()));
                        idx = item_1.size();
                        break;
                    }
                    // System.out.println(res);

                }
            }
        } else {
            res = partitions;
        }

        return res;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    @Override
    public Long setClassForSemester(Long id, int partion, int min, int max) {
        // Lấy học kì
        Optional<Semester> semesterOpt = semesterRepository.findById3(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        List<SemesterClass> allSemesterClassBySemester = semesterClassRepository.findBySemesterId1(semester.getId());

        System.out.println("Total semester class in semester: " + String.valueOf(allSemesterClassBySemester.size()));

        // Danh sách học sinh đăng kí học
        // Danh sách giáo viên đăng kí dạy
        check_count = 0;
        number = 1;
        allSemesterClassBySemester.forEach(semester_class -> {
            // Danh sách học sinh đăng kí 1 khóa học trong 1 học kì
            List<UserRegisterJoinSemester> listUserRegisterJoinSemesters = userRegisterJoinSemesterRepository
                    .findBySemesterClassId1(semester_class.getId());

            // Danh sách giáo viên đăng kí dạy 1 khóa học trong 1 học kì
            List<UserRegisterTeachSemester> allUserRegisterTeachSemesters = userRegisterTeachSemesterRepository
                    .findBySemesterClassId1(semester_class.getId());

            Map<Teacher, Integer> list_total_register_of_teacher = new HashMap<>();

            allUserRegisterTeachSemesters.forEach(teacher_register_teach_semester -> {
                List<Classes> allClassForTeacherAndSemester = classRepository
                        .findAllByTeacherAndSemester(teacher_register_teach_semester.getTeacher().getId(), id);
                list_total_register_of_teacher.put(teacher_register_teach_semester.getTeacher(),
                        allClassForTeacherAndSemester.size());
            });

            sortByValue(list_total_register_of_teacher);

            System.out.println("class_id: " + String.valueOf(semester_class.getId()));
            System.out.println(
                    "Total user_register_teach_semester: " + String.valueOf(allUserRegisterTeachSemesters.size()));
            System.out.println(
                    "Total user_register_join_semester: " + String.valueOf(listUserRegisterJoinSemesters.size()));

            // Lấy các group có thể chia được
            if (listUserRegisterJoinSemesters.size() >= min) {
                List<List<Integer>> all_group = foo1(listUserRegisterJoinSemesters.size(), partion, min, max);
                List<List<Integer>> list_group = new ArrayList<>();
                if (all_group.get(all_group.size() - 1).size() < min) {
                    list_group = all_group.subList(0, all_group.size() - 1);
                } else {
                    list_group = all_group;
                }
                System.out.println(" Ket qua chi lop: " + String.valueOf(list_group.size()));
                System.out.println(list_group);
                // Số lớp có thể chia
                int total_class = list_group.size();

                for (int i = 0; i < allUserRegisterTeachSemesters.size(); i++) {
                    // Danh sách học viên lớp thứ i
                    if (i < total_class) {
                        List<UserRegisterJoinSemester> validUserRegisterSemesters = new ArrayList<>();
                        for (int j = 0; j < list_group.get(i).size(); j++) {
                            int idx = list_group.get(i).get(j) - 1;
                            validUserRegisterSemesters.add(new ArrayList<>(listUserRegisterJoinSemesters).get(idx));
                        }
                        System.out
                                .println("So hoc sinh duoc xep: " + String.valueOf(validUserRegisterSemesters.size()));
                        String key = getSaltString();
                        System.out.println("Lop thu: " + String.valueOf(i));
                        Classes savedClass = Classes.builder()
                                .teacher(new ArrayList<>(list_total_register_of_teacher.keySet()).get(i))
                                .semesterClass(semester_class)
                                .security_code(key)
                                .name(semester_class.getName() + "-" + String.valueOf(number) + " thuộc học kì "
                                        + String.valueOf(semester.getNumber()) + " năm học "
                                        + String.valueOf(semester.getYear()))
                                .build();
                        classRepository.save(savedClass);

                        savedClass.setLink_meeting("https://jitsi.kidsdrawing.site/" + savedClass.getId().toString());
                        classRepository.save(savedClass);

                        Notification savedNotification3 = Notification.builder()
                                .name("Xếp lớp thành công!")
                                .description("Xin chào bạn!.\n Chúng tôi xin thông báo đăng kí dạy lớp mở theo kì "
                                        + semester_class.getName()
                                        + " của bạn được xếp lớp thành công!\n Chân thành cảm ơn!")
                                .build();
                        notificationRepository.save(savedNotification3);

                        UserReadNotificationKey idxz = new UserReadNotificationKey(
                                new ArrayList<>(list_total_register_of_teacher.keySet()).get(i).getId(),
                                savedNotification3.getId());

                        UserReadNotification savedUserReadNotification3 = UserReadNotification.builder()
                                .id(idxz)
                                .notification(savedNotification3)
                                .user(new ArrayList<>(list_total_register_of_teacher.keySet()).get(i).getUser())
                                .is_read(false)
                                .build();
                        uuserReadNotificationRepository.save(savedUserReadNotification3);

                        validUserRegisterSemesters.forEach(user_register_semester -> {

                            Notification savedNotification1 = Notification.builder()
                                    .name("Xếp lớp thành công!")
                                    .description("Xin chào bạn!.\n Chúng tôi xin thông báo đăng kí khóa học "
                                            + user_register_semester.getSemesterClass().getCourse().getName()
                                            + " của tài khoản con của bạn "
                                            + user_register_semester.getStudent().getUser().getUsername()
                                            + " được xếp lớp thành công!\n Chân thành cảm ơn!")
                                    .build();
                            notificationRepository.save(savedNotification1);

                            Notification savedNotification = Notification.builder()
                                    .name("Xếp lớp không thành công!")
                                    .description("Xin chào bạn!.\n Chúng tôi xin thông báo đăng kí khóa học "
                                            + new ArrayList<UserRegisterJoinSemester>(listUserRegisterJoinSemesters)
                                                    .get(0).getSemesterClass().getCourse().getName()
                                            + " của bạn không được xếp lớp thành công! Rất mong bạn thông cảm.\n Chân thành cảm ơn!")
                                    .build();
                            notificationRepository.save(savedNotification);

                            UserReadNotificationKey idxy = new UserReadNotificationKey(
                                    user_register_semester.getStudent().getId(), savedNotification.getId());

                            UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                                    .id(idxy)
                                    .notification(savedNotification)
                                    .user(user_register_semester.getStudent().getUser())
                                    .is_read(false)
                                    .build();
                            uuserReadNotificationRepository.save(savedUserReadNotification);

                            UserReadNotificationKey idxx = new UserReadNotificationKey(
                                    user_register_semester.getStudent().getParent().getId(),
                                    savedNotification1.getId());

                            UserReadNotification savedUserReadNotification1 = UserReadNotification.builder()
                                    .id(idxx)
                                    .notification(savedNotification1)
                                    .user(user_register_semester.getStudent().getParent().getUser())
                                    .is_read(false)
                                    .build();
                            uuserReadNotificationRepository.save(savedUserReadNotification1);

                            ClassHasRegisterJoinSemesterClassKey idx = new ClassHasRegisterJoinSemesterClassKey(
                                    savedClass.getId(), user_register_semester.getId());

                            ClassHasRegisterJoinSemesterClass savedClassHasRegisterJoinSemesterClass = ClassHasRegisterJoinSemesterClass
                                    .builder()
                                    .id(idx)
                                    .classes(savedClass)
                                    .student(user_register_semester.getStudent())
                                    .review_star(-1)
                                    .build();
                            classHasRegisterJoinSemesterClassRepository.save(savedClassHasRegisterJoinSemesterClass);
                        });
                        number++;
                        // listClassOfSemesterClass.add(savedClass);

                        List<SectionTemplate> sectionTemplateOpt = sectionTemplateRepository
                                .findByCourseId1(semester_class.getCourse().getId());
                        sectionTemplateOpt.forEach(section_template -> {
                            Section savedSection = Section.builder()
                                    .classes(savedClass)
                                    .name(section_template.getName())
                                    .number(section_template.getNumber())
                                    .teaching_form(section_template.getTeaching_form())
                                    .status("")
                                    .build();
                            sectionRepository.save(savedSection);

                            validUserRegisterSemesters.forEach(user_register_semester -> {
                                UserAttendanceKey idxx = new UserAttendanceKey(savedSection.getId(),
                                        user_register_semester.getStudent().getId());
                                UserAttendance savedUserAttendance = UserAttendance.builder()
                                        .id(idxx)
                                        .section(savedSection)
                                        .student(user_register_semester.getStudent())
                                        .status(false)
                                        .build();
                                userAttendanceRepository.save(savedUserAttendance);
                            });
                        });
                    } else {

                    }
                }
            } else {
                if (allUserRegisterTeachSemesters.size() > 0) {
                    allUserRegisterTeachSemesters.forEach(ele -> {
                        Notification savedNotification = Notification.builder()
                                .name("Xếp lớp không thành công!")
                                .description("Xin chào bạn!.\n Chúng tôi xin thông báo đăng kí dạy lớp mở theo kì "
                                        + ele.getSemesterClass().getName()
                                        + " của bạn không được xếp lớp thành công! Rất mong bạn thông cảm.\n Chân thành cảm ơn!")
                                .build();
                        notificationRepository.save(savedNotification);
                        UserReadNotificationKey idx = new UserReadNotificationKey(ele.getTeacher().getId(),
                                savedNotification.getId());

                        UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                                .id(idx)
                                .notification(savedNotification)
                                .user(ele.getTeacher().getUser())
                                .is_read(false)
                                .build();
                        uuserReadNotificationRepository.save(savedUserReadNotification);
                    });
                }
                if (listUserRegisterJoinSemesters.size() > 0) {
                    Notification savedNotification = Notification.builder()
                            .name("Xếp lớp không thành công!")
                            .description("Xin chào bạn!.\n Chúng tôi xin thông báo đăng kí khóa học "
                                    + new ArrayList<UserRegisterJoinSemester>(listUserRegisterJoinSemesters).get(0)
                                            .getSemesterClass().getCourse().getName()
                                    + " của bạn không được xếp lớp thành công! Rất mong bạn thông cảm.\n Chân thành cảm ơn!")
                            .build();
                    notificationRepository.save(savedNotification);
                    listUserRegisterJoinSemesters.forEach(ele -> {
                        Notification savedNotification1 = Notification.builder()
                                .name("Xếp lớp không thành công!")
                                .description("Xin chào bạn!.\n Chúng tôi xin thông báo đăng kí khóa học "
                                        + ele.getSemesterClass().getCourse().getName() + " của tài khoản con của bạn "
                                        + ele.getStudent().getUser().getUsername()
                                        + " không được xếp lớp thành công! Rất mong bạn thông cảm.\n Chân thành cảm ơn!")
                                .build();
                        notificationRepository.save(savedNotification1);

                        UserReadNotificationKey idx = new UserReadNotificationKey(ele.getStudent().getId(),
                                savedNotification.getId());

                        UserReadNotification savedUserReadNotification = UserReadNotification.builder()
                                .id(idx)
                                .notification(savedNotification)
                                .user(ele.getStudent().getUser())
                                .is_read(false)
                                .build();
                        uuserReadNotificationRepository.save(savedUserReadNotification);

                        UserReadNotificationKey idxx = new UserReadNotificationKey(ele.getStudent().getParent().getId(),
                                savedNotification1.getId());

                        UserReadNotification savedUserReadNotification1 = UserReadNotification.builder()
                                .id(idxx)
                                .notification(savedNotification1)
                                .user(ele.getStudent().getParent().getUser())
                                .is_read(false)
                                .build();
                        uuserReadNotificationRepository.save(savedUserReadNotification1);
                    });
                }

            }
            check_count++;
            System.out.println("Loop: " + String.valueOf(check_count));
            // throw new EntityNotFoundException("exception.end.end");
        });
        return (long) 1;
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
    public GetSemesterResponse getSemesterById(Long id) {
        Optional<Semester> semesterOpt = semesterRepository.findById2(id);
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        List<Holiday> pageHoliday = holidayRepository.findBySemesterId(semester.getId());
        List<LocalDate> holidayTime = new ArrayList<>();
        pageHoliday.forEach(holiday -> {
            holidayTime.add(holiday.getDay());
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
                .holiday(new HashSet<>(holidayTime))
                .build();
    }

    @Override
    public Long createSemester(CreateSemesterRequest createSemesterRequest) {
        List<Semester> semesters = semesterRepository.findAll2();

        if (semesters.size() > 0) {
            if (createSemesterRequest.getStart_time().isBefore(semesters.get(0).getEnd_time())) {
                throw new EntityNotFoundException("exception.Semester.start_time");
            } else {
                Semester savedSemester = Semester.builder()
                        .name(createSemesterRequest.getName())
                        .description(createSemesterRequest.getDescription())
                        .number(createSemesterRequest.getNumber())
                        .year(createSemesterRequest.getYear())
                        .start_time(createSemesterRequest.getStart_time())
                        .end_time(createSemesterRequest.getStart_time().plusMonths(3))
                        .build();
                semesterRepository.save(savedSemester);

                createSemesterRequest.getTime().forEach(holiday -> {
                    Holiday saveHoliday = Holiday.builder()
                            .day(holiday)
                            .semester(savedSemester)
                            .build();
                    holidayRepository.save(saveHoliday);
                });

                return savedSemester.getId();
            }

        } else {
            Semester savedSemester = Semester.builder()
                    .name(createSemesterRequest.getName())
                    .description(createSemesterRequest.getDescription())
                    .number(createSemesterRequest.getNumber())
                    .year(createSemesterRequest.getYear())
                    .start_time(createSemesterRequest.getStart_time())
                    .end_time(createSemesterRequest.getStart_time().plusMonths(3))
                    .build();
            semesterRepository.save(savedSemester);

            createSemesterRequest.getTime().forEach(holiday -> {
                Holiday saveHoliday = Holiday.builder()
                        .day(holiday)
                        .semester(savedSemester)
                        .build();
                holidayRepository.save(saveHoliday);
            });

            return savedSemester.getId();
        }

    }

    @Override
    public Long removeSemesterById(Long id) {
        Optional<Semester> semesterOpt = semesterRepository.findById1(id);
        semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        List<Classes> listClass = classRepository.findAllBySemester(id);
        LocalDateTime time_now = LocalDateTime.now();

        for (int i = 0; i < listClass.size(); i++) {
            if (time_now.isBefore(listClass.get(i).getSemesterClass().getSemester().getEnd_time())) {
                throw new ArtAgeNotDeleteException("exception.Course_Classes.not_delete");
            }
        }

        semesterRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateSemesterById(Long id, CreateSemesterRequest createSemesterRequest) {
        Optional<Semester> semesterOpt = semesterRepository.findById1(id);
        Semester updatedSemester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        List<SemesterClass> semesterClasses = semesterClassRepository.findBySemesterId1(id);
        if (semesterClasses.size() > 0) {
            updatedSemester.setName(createSemesterRequest.getName());
            updatedSemester.setDescription(createSemesterRequest.getDescription());
            updatedSemester.setNumber(createSemesterRequest.getNumber());
            updatedSemester.setYear(createSemesterRequest.getYear());
            semesterRepository.save(updatedSemester);
            return updatedSemester.getId();
        } else {
            List<Semester> semesters = semesterRepository.findAll3(updatedSemester.getId());

            if (semesters.size() > 0) {
                if (createSemesterRequest.getStart_time().isBefore(semesters.get(0).getEnd_time())) {
                    throw new EntityNotFoundException("exception.Semester.start_time");
                } else {
                    List<Holiday> pageHoliday = holidayRepository.findBySemesterId(id);
                    List<LocalDate> holidayTime = new ArrayList<>();
                    pageHoliday.forEach(holiday -> {
                        holidayTime.add(holiday.getDay());
                    });
                
                    holidayRepository.deleteAll(pageHoliday);
                
                    createSemesterRequest.getTime().forEach(holiday -> {
                        Holiday saveHoliday = Holiday.builder()
                                .day(holiday)
                                .semester(updatedSemester)
                                .build();
                        holidayRepository.save(saveHoliday);
                    });
                
                    updatedSemester.setName(createSemesterRequest.getName());
                    updatedSemester.setDescription(createSemesterRequest.getDescription());
                    updatedSemester.setNumber(createSemesterRequest.getNumber());
                    updatedSemester.setYear(createSemesterRequest.getYear());
                    updatedSemester.setStart_time(createSemesterRequest.getStart_time());
                    updatedSemester.setEnd_time(createSemesterRequest.getStart_time().plusMonths(3));
                    semesterRepository.save(updatedSemester);
                
                    return updatedSemester.getId();
                }
            }
            else {
                List<Holiday> pageHoliday = holidayRepository.findBySemesterId(id);
                List<LocalDate> holidayTime = new ArrayList<>();
                pageHoliday.forEach(holiday -> {
                    holidayTime.add(holiday.getDay());
                });

                holidayRepository.deleteAll(pageHoliday);

                createSemesterRequest.getTime().forEach(holiday -> {
                    Holiday saveHoliday = Holiday.builder()
                            .day(holiday)
                            .semester(updatedSemester)
                            .build();
                    holidayRepository.save(saveHoliday);
                });

                updatedSemester.setName(createSemesterRequest.getName());
                updatedSemester.setDescription(createSemesterRequest.getDescription());
                updatedSemester.setNumber(createSemesterRequest.getNumber());
                updatedSemester.setYear(createSemesterRequest.getYear());
                updatedSemester.setStart_time(createSemesterRequest.getStart_time());
                updatedSemester.setEnd_time(createSemesterRequest.getStart_time().plusMonths(3));
                semesterRepository.save(updatedSemester);

                return updatedSemester.getId();
            }

        }
    }
}
