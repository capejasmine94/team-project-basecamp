package com.bulmeong.basecamp.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtil {
    // 시간 변경
    // 사용법 : 태그에 다음의 타임리프 구문을 추가합니다. 
    // th:text="${T(com.coffeeprincess.palace.util.TimeUtil).timeAgo(바꾸고 싶은 시간)}"
    // 예를 들어 바꾸고 싶은 시간에 session.sessionUserInfo.userDto.created_at을 넣으면 유저 가입일이 보입니다.
                  
    static public String timeAgo(Date date) {
        Date currentDate = new Date();
        LocalDateTime pastDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime currentDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        long years = ChronoUnit.YEARS.between(pastDateTime, currentDateTime);
        long weeks = ChronoUnit.WEEKS.between(pastDateTime, currentDateTime);
        long days = ChronoUnit.DAYS.between(pastDateTime, currentDateTime);
        long hours = ChronoUnit.HOURS.between(pastDateTime, currentDateTime);
        long minutes = ChronoUnit.MINUTES.between(pastDateTime, currentDateTime);

        if (years > 0) {
            return years + "년 전";
        } else if (weeks > 0) {
            return weeks + "주 전";
        } else if (days > 0) {
            return days + "일 전";
        } else if (hours > 0) {
            return hours + "시간 전";
        } else if (minutes > 0) {
            return minutes + "분 전";
        } else {
            return "방금 전";
        }
    }

}
