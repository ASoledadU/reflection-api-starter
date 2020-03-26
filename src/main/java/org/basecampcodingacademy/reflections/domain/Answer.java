package org.basecampcodingacademy.reflections.domain;

public class Answer {
    public Integer id;
    public Integer responseId;
    public Integer questionId;
    public String content;

    public Answer() {}

    public Answer(Integer id, Integer responseId, Integer questionId, String content) {
        this.id = id;
        this.responseId = responseId;
        this.questionId = questionId;
        this.content = content;
    }
}
