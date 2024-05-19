package com.labwork8.server;

import java.util.List;

public class Assignment {
    private int id;
    private String name;
    private String description;
    private String type = "assignment";
    private List<Integer> assignees;
    private List<Integer> finishedAssignees;

    public Assignment() {
    }

    public Assignment(int id, List<Integer> assignees, List<Integer> finishedAssignees, String name, String description) {
        this.id = id;
        this.assignees = assignees;
        this.finishedAssignees = finishedAssignees;
        this.name = name;
        this.description = description;
    }

    public int getTaskId() {
        return id;
    }
    public void setTaskId(int taskId) {
        this.id = taskId;
    }

    public List<Integer> getAssignees() {
        return assignees;
    }
    public List<Integer> setAssignees(List<Integer> assignees) {
        this.assignees = assignees;
        return assignees;
    }
    public List<Integer> addAssignee(int assigneeId) {
        assignees.add(assigneeId);
        return assignees;
    }

    public List<Integer> getFinishedAssignees() {
        return finishedAssignees;
    }
    public void setFinishedAssignees(List<Integer> finishedAssignees) {
        this.finishedAssignees = finishedAssignees;
    }
    public List<Integer> addFinishedAssignee(int assigneeId) {
        finishedAssignees.add(assigneeId);
        return finishedAssignees;
    }

    public String getTaskName() {
        return name;
    }
    public void setTaskName(String taskName) {
        this.name = taskName;
    }

    public String getTaskDescription() {
        return description;
    }
    public void setTaskDescription(String taskDescription) {
        this.description = taskDescription;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
