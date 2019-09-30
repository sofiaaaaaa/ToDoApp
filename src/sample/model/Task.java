package sample.model;

import java.sql.Timestamp;

public class Task {
    private Timestamp dataecreated;
    private String description;
    private String task;
    private int taskId;
    private int userId;

    public Task() {
    }

    public Task(Timestamp dataecreated, String description, String task) {
        this.dataecreated = dataecreated;
        this.description = description;
        this.task = task;
    }

    public Timestamp getDataecreated() {
        return dataecreated;
    }

    public void setDataecreated(Timestamp dataecreated) {
        this.dataecreated = dataecreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "dataecreated=" + dataecreated +
                ", description='" + description + '\'' +
                ", task='" + task + '\'' +
                ", userId=" + userId +
                '}';
    }
}
