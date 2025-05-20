package com.ecommerce.ecommerce_backend.dtos.UserDtos;

import java.time.LocalDate;
import java.util.Map;

public class UserStatsDTO {
    private long totalUsers;
    private long activeUsers;
    private long deletedUsers;
    private long todayRegistrations;
    private long todayLogins;
    private Integer predictedRegistrationsTomorrow;

    public Integer getPredictedRegistrationsTomorrow() {
        return predictedRegistrationsTomorrow;
    }

    public void setPredictedRegistrationsTomorrow(Integer predictedRegistrationsTomorrow) {
        this.predictedRegistrationsTomorrow = predictedRegistrationsTomorrow;
    }

    public UserStatsDTO(long totalUsers, long activeUsers, long deletedUsers, long todayRegistrations, long todayLogins) {
        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.deletedUsers = deletedUsers;
        this.todayRegistrations = todayRegistrations;
        this.todayLogins = todayLogins;
    }

    public UserStatsDTO() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public long getDeletedUsers() {
        return deletedUsers;
    }

    public void setDeletedUsers(long deletedUsers) {
        this.deletedUsers = deletedUsers;
    }

    public long getTodayRegistrations() {
        return todayRegistrations;
    }

    public void setTodayRegistrations(long todayRegistrations) {
        this.todayRegistrations = todayRegistrations;
    }

    public long getTodayLogins() {
        return todayLogins;
    }

    public void setTodayLogins(long todayLogins) {
        this.todayLogins = todayLogins;
    }
}

