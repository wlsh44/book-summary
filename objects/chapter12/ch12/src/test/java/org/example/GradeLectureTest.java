package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeLectureTest {

    @Test
    void evaluateTest() {
        GradeLecture gradeLecture = new GradeLecture("객체지향 프로그래밍",
                70,
                List.of(81, 95, 75, 50, 45),
                List.of(new Grade("A", 100, 95),
                        new Grade("B", 94, 80),
                        new Grade("C", 79, 70),
                        new Grade("D", 69, 50),
                        new Grade("F", 49, 0)
                ));
        System.out.println(gradeLecture.evaluate());
    }
}
