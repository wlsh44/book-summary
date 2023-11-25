package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LectureTest {

    @Test
    void evaluateTest() throws Exception {
        //given
        Lecture lecture = new Lecture("객체지향 프로그래밍",
                70,
                List.of(81, 95, 75, 50, 45));

        //when
        System.out.println(lecture.evaluate());

        //then

    }
}
