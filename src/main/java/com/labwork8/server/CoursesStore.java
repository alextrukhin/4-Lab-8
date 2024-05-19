package com.labwork8.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * ProductsStore class is used to store information about products
 */
public class CoursesStore {
    public static final String FILE_NAME = "courses.json";

    /**
     * List of products
     */
    List<Course> data = new ArrayList<Course>();

    /**
     * Default constructor
     */
    public CoursesStore() {
        data = readFromFile(FILE_NAME);
    }

    /**
     * Get list of products
     *
     * @return list of products
     */
    public List<Course> getCourses() {
        return data;
    }

    /**
     * Get product by id
     *
     * @param id product id
     * @return product
     */
    public Course getCourseByUid(String id) {
        for (Course course : data) {
            if (Objects.equals(course.getUid(), id)) {
                return course;
            }
        }
        return null;
    }

    /**
     * Get courses for student
     *
     * @param id student id
     * @return product
     */
    public List<Course> getCoursesForStudent(Integer id) {
        List<Course> courses = new ArrayList<>();
        for (Course course : data) {
            if (course.getStudents().contains(id)) {
                courses.add(course);
            }
        }
        return courses;
    }

    /**
     * Get courses for teacher
     *
     * @param id teacher id
     * @return product
     */
    public List<Course> getCoursesForTeacher(Integer id) {
        List<Course> courses = new ArrayList<>();
        for (Course course : data) {
            if (course.getTeachers().contains(id)) {
                courses.add(course);
            }
        }
        return courses;
    }


    /**
     * Add course
     *
     * @param course course
     * @return added course
     */
    public Course addCourse(Course course) {
        Course newCourse = new CourseBuilder(course)
                .setUid(UUID.randomUUID().toString())
                .build();
        data.add(newCourse);
        saveListToFile(data, FILE_NAME);
        return newCourse;
    }

    /**
     * Add new task
     *
     * @param task     task object
     * @param courseId course uid
     * @return course
     */
    public Course addTask(Assignment task, String courseId) {
        Course courseToUpdate = getCourseByUid(courseId);
        CourseBuilder courseBuilder = new CourseBuilder(courseToUpdate);
        int highestId = 0;
        for (Assignment t : courseToUpdate.getAssignments()) {
            if (t.getTaskId() > highestId) {
                highestId = t.getTaskId();
            }
        }
        task.setTaskId(highestId + 1);
        List<Assignment> tasks = courseToUpdate.getAssignments();
        tasks.add(task);
        courseBuilder.setAssignments(tasks);
        return updateCourse(courseBuilder.build());
    }

    /**
     * Mark task as done
     *
     * @param courseUid course uid
     * @param taskId    task id
     * @param studentId student id
     * @return course
     */
    public Course markTaskAsDone(String courseUid, int taskId, int studentId) {
        Course courseToUpdate = getCourseByUid(courseUid);
        CourseBuilder courseBuilder = new CourseBuilder(courseToUpdate);
        List<Assignment> tasks = courseToUpdate.getAssignments();
        for (Assignment task : tasks) {
            if (task.getTaskId() == taskId) {
                task.getFinishedAssignees().add(studentId);
            }
        }
        courseBuilder.setAssignments(tasks);
        return updateCourse(courseBuilder.build());
    }


    /**
     * Update course
     *
     * @param course course
     * @return updated course
     */
    public Course updateCourse(Course course) {
        Integer index = null;
        for (int i = 0; i < data.size(); i++) {
            if (Objects.equals(data.get(i).getUid(), course.getUid())) {
                index = i;
                break;
            }
        }
        if (index == null) {
            throw new RuntimeException("Product not found");
        }
        data.set(index, course);
        saveListToFile(data, FILE_NAME);
        return course;
    }

    /**
     * Remove course
     *
     * @param uid     course uid
     * @param courses list of courses
     */
    public void removeCourse(String uid, List<Course> courses) {
        data.removeIf(course -> Objects.equals(course.getUid(), uid));
        saveListToFile(data, FILE_NAME);
    }

    /**
     * Read list of courses from file
     *
     * @param fileName file name
     * @return list of courses
     */
    private List<Course> readFromFile(String fileName) {
        Type REVIEW_TYPE = new TypeToken<List<Course>>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            saveListToFile(data, fileName);
            return data;
        }
        return gson.fromJson(reader, REVIEW_TYPE);
    }

    /**
     * Save list of products to file
     *
     * @param list     list of products
     * @param fileName file name
     */
    private void saveListToFile(List<Course> list, String fileName) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(list, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
