package com.tickettogether.domain.calendar.dto;

import com.tickettogether.domain.calendar.domain.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CalendarDto {

    @Getter
    public static class PostRequest{
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private String date;
        private String imgUrl;
    }

    @Getter
    public static class PostResponse{
        private Long id;
        private LocalDate date;
        private String imgUrl;

        public PostResponse(Calendar calendar){
            this.id = calendar.getId();
            this.date = calendar.getDate();
            this.imgUrl = calendar.getImgUrl();
        }
    }

}
