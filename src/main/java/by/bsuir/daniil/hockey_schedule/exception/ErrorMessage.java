package by.bsuir.daniil.hockey_schedule.exception;

import lombok.Data;

import java.util.Date;

public record ErrorMessage(int statusCode, Date timestamp, String message, String description) {
}
//
//    public int getStatusCode() {
//        return statusCode;
//    }
//
//    public Date getTimestamp() {
//        return timestamp;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//}
