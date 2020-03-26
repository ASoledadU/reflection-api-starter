package org.basecampcodingacademy.reflections.exc;

import org.basecampcodingacademy.reflections.domain.Response;

import java.time.LocalDate;

public class ResponseForNonExistingReflection extends Exception {
    public Integer reflectionId;
    public ResponseForNonExistingReflection(Integer reflectionId) {
        this.reflectionId = reflectionId;
    }
}
