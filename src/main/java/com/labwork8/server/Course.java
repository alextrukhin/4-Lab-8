package com.labwork8.server;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Course {
    private String uid;
    private String name;
    private List<Integer> students;
    private List<Integer> teachers;
    private List<Assignment> assignments;

    /**
     * Default constructor
     */
    public Course() {
    }

    /**
     * Constructor
     *
     * @param uid         course id
     * @param name        course name
     * @param students    list of students
     * @param teachers    list of teachers
     * @param assignments list of assignments
     */
    public Course(String uid, String name, List<Integer> students, List<Integer> teachers, List<Assignment> assignments) {
        this.uid = uid;
        this.name = name;
        this.students = students;
        this.teachers = teachers;
        this.assignments = assignments;
    }

    /**
     * Get course id
     *
     * @return course id
     */
    @SerializedName("uid")
    public String getUid() {
        return uid;
    }

    @SerializedName("name")
    public String getName() {
        return name;
    }
    @SerializedName("students")
    public List<Integer> getStudents() {
        return students;
    }
    @SerializedName("teachers")
    public List<Integer> getTeachers() {
        return teachers;
    }
    @SerializedName("assignments")
    public List<Assignment> getAssignments() {
        return assignments;
    }
}
