package com.bfs.logindemo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerCard {
    private int quizId;
    private int questionId;
    private Integer answerId;
}
