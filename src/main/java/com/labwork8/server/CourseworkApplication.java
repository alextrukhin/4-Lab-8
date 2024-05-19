package com.labwork8.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CourseworkApplication {
    private final String ORIGIN = "http://localhost:5174";

    /**
     * ProductsStore instance
     */
    private final CoursesStore coursesStore = new CoursesStore();

    /**
     * Index page
     *
     * @return greetings
     */
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /**
     * Get list of courses for student
     *
     * @return list of courses
     */
    @PostMapping(path = "/iAmEnrolledIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> iAmEnrolledIn(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return new ResponseEntity<Object>(gson.toJson(coursesStore.getCoursesForStudent(Integer.parseInt(datamap.get("id").toString()))), HttpStatus.OK);
    }

    /**
     * Get list of courses for teacher
     *
     * @return list of courses
     */
    @PostMapping(path = "/iAmTeaching", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> iAmTeaching(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return new ResponseEntity<Object>(gson.toJson(coursesStore.getCoursesForTeacher(Integer.parseInt(datamap.get("id").toString()))), HttpStatus.OK);
    }

    /**
     * Create course
     *
     * @param datamap { name: string, teacher_id: number }
     * @return product
     */
    @PostMapping(path = "/createCourse", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> createCourse(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        CourseBuilder courseBuilder = new CourseBuilder();


        courseBuilder.setUid("")
                .setName(datamap.get("name").toString())
                .setAssignments(new ArrayList<>())
                .setStudents(new ArrayList<>())
                .setTeachers(List.of(Integer.parseInt(datamap.get("teacher_id").toString())));

        Course course = coursesStore.addCourse(courseBuilder.build());
        return new ResponseEntity<Object>(gson.toJson(course), HttpStatus.OK);
    }

    /**
     * Enroll to course as student
     *
     * @param datamap { student_id: number, course_id: string }
     * @return course
     */
    @PostMapping(path = "/enrollCourse", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> enrollCourse(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Course courseToUpdate = coursesStore.getCourseByUid(datamap.get("course_id").toString());
        CourseBuilder courseBuilder = new CourseBuilder(courseToUpdate);

        if (courseToUpdate.getStudents().contains(Integer.parseInt(datamap.get("student_id").toString()))) {
            return new ResponseEntity<Object>(HttpStatus.OK);
        }

        List<Integer> students = courseToUpdate.getStudents();
        students.add(Integer.parseInt(datamap.get("student_id").toString()));
        courseBuilder.setStudents(students);

        Course course = coursesStore.updateCourse(courseBuilder.build());
        return new ResponseEntity<Object>(gson.toJson(course), HttpStatus.OK);
    }

    /**
     * Enroll to course as teacher
     *
     * @param datamap { teacher_id: number, course_id: string }
     * @return course
     */
    @PostMapping(path = "/teachCourse", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> teachCourse(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Course courseToUpdate = coursesStore.getCourseByUid(datamap.get("course_id").toString());
        CourseBuilder courseBuilder = new CourseBuilder(courseToUpdate);

        if (courseToUpdate.getTeachers().contains(Integer.parseInt(datamap.get("teacher_id").toString()))) {
            return new ResponseEntity<Object>(HttpStatus.OK);
        }

        List<Integer> teachers = courseToUpdate.getTeachers();
        teachers.add(Integer.parseInt(datamap.get("teacher_id").toString()));
        courseBuilder.setTeachers(teachers);

        Course course = coursesStore.updateCourse(courseBuilder.build());
        return new ResponseEntity<Object>(gson.toJson(course), HttpStatus.OK);
    }

    /**
     * Create task
     *
     * @param datamap task object
     * @return task
     */
    @PostMapping(path = "/createTask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> createTask(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String taskType = datamap.get("type").toString();
        switch (taskType) {
            case "assignment":
                coursesStore.addTask(
                        new Assignment(0, (ArrayList<Integer>) datamap.get("assignees"), new ArrayList<>(), datamap.get("name").toString(), datamap.get("description").toString()),
                        datamap.get("course_id").toString()
                );
                break;
        }

        return new ResponseEntity<Object>(gson.toJson(coursesStore.getCourseByUid(datamap.get("course_id").toString())), HttpStatus.OK);
    }

    /**
     * Complete task
     *
     * @param datamap { student_id: number, task_id: number, course_uid: string }
     * @return task
     */
    @PostMapping(path = "/completeTask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = ORIGIN)
    public ResponseEntity<Object> completeTask(@RequestBody Map<String, Object> datamap) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        coursesStore.markTaskAsDone(datamap.get("course_uid").toString(),
                Integer.parseInt(datamap.get("task_id").toString()),
                Integer.parseInt(datamap.get("student_id").toString())
        );

        return new ResponseEntity<Object>(gson.toJson(coursesStore.getCourseByUid(datamap.get("course_uid").toString())), HttpStatus.OK);
    }
}
