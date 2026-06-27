package com.github.laszekq.titlelibrary.dtos;

import com.github.laszekq.titlelibrary.entities.Status;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class EntryDTO {
    @NotBlank
    private Integer id;
    @NotBlank
    private String status;
    @NotBlank
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private String date;
    @NotBlank
    @Max(10)
    @Min(1)
    private Integer rating;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
