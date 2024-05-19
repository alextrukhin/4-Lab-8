package com.labwork8.server;

import java.util.List;

/**
 * ProductBuilder class is used to create Product objects
 */
public class CourseBuilder {
    private String uid;
    private String name;
    private List<Integer> students;
    private List<Integer> teachers;
    private List<Assignment> assignments;

    /**
     * Default constructor
     */
    public CourseBuilder() {}

    /**
     * Constructor
     *
     * @param uid course id
     * @param name course name
     * @param students list of students
     * @param teachers list of teachers
     * @param assignments list of assignments
     */
    public CourseBuilder(String uid, String name, List<Integer> students, List<Integer> teachers, List<Assignment> assignments) {
        this.uid = uid;
        this.name = name;
        this.students = students;
        this.teachers = teachers;
        this.assignments = assignments;
    }

    /**
     * Constructor
     *
     * @param course Course object
     */
    public CourseBuilder(Course course) {
        this.uid = course.getUid();
        this.name = course.getName();
        this.students = course.getStudents();
        this.teachers = course.getTeachers();
        this.assignments = course.getAssignments();
    }


    /**
     * Set course id
     *
     * @param uid course id
     * @return CourseBuilder object
     */
    public CourseBuilder setUid(String uid) {
        this.uid = uid;
        return this;
    }

    /**
     * Set course name
     *
     * @param name course name
     * @return CourseBuilder object
     */
    public CourseBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set list of students
     *
     * @param students list of students
     * @return CourseBuilder object
     */
    public CourseBuilder setStudents(List<Integer> students) {
        this.students = students;
        return this;
    }

    /**
     * Set list of teachers
     *
     * @param teachers list of teachers
     * @return CourseBuilder object
     */
    public CourseBuilder setTeachers(List<Integer> teachers) {
        this.teachers = teachers;
        return this;
    }

    /**
     * Set list of assignments
     *
     * @param assignments list of assignments
     * @return CourseBuilder object
     */
    public CourseBuilder setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
        return this;
    }

    /**
     * Build Course object
     *
     * @return Course object
     */
    public Course build() {
        return new Course(uid, name, students, teachers, assignments);
    }
}
