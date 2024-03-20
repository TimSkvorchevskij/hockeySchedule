package by.bsuir.daniil.hockey_schedule.exception;


import java.util.Date;

public record ErrorMessage(int statusCode, Date timestamp, String message, String description) {
}
