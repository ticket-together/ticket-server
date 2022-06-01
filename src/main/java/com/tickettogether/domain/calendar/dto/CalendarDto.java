package com.tickettogether.domain.calendar.dto;

import com.tickettogether.domain.calendar.domain.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CalendarDto {

    @Getter
    @NoArgsConstructor
    public static class PostRequest{
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private String date;
    }

    @Getter
    @NoArgsConstructor
    public static class PostResponse{
        private Long id;
        private LocalDate date;
        private String url;

        public PostResponse(Calendar calendar){
            this.id = calendar.getId();
            this.date = calendar.getDate();
            this.url = calendar.getImgUrl();
        }
    }

}
