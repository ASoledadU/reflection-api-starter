package org.basecampcodingacademy.reflections.controllers;

import org.basecampcodingacademy.reflections.db.ResponseRepository;
import org.basecampcodingacademy.reflections.domain.Response;
import org.basecampcodingacademy.reflections.exceptions.ResponseForExistingReflection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.basecampcodingacademy.reflections.domain.Answer;
import org.basecampcodingacademy.reflections.domain.Response;
import org.basecampcodingacademy.reflections.domain.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/reflections/{reflectionId}/responses")
public class ResponseController {
    @Autowired
    public ResponseRepository responses;
    
    @GetMapping
    public List<Response> index(Response response, @PathVariable Integer reflectionId) {
        response.reflectionId = reflectionId;
        return (List<Response>) responses.getOne(response);
    }
    
    @Autowired
    public ReflectionRepository reflections;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody Response response, @PathVariable Integer reflectionId) throws ResponseForExistingReflection {
        response.reflectionId = reflectionId;
        if (!Objects.isNull(responses.find(response.reflectionId))) {
            return responses.create(response);
        }
        throw new ResponseForExistingReflection(response.reflectionId);
    }

    @PatchMapping("/{id}")
    public Response update(@PathVariable Integer reflectionId,@PathVariable Integer id, @RequestBody Response response) {
        response.id = id;
        response.reflectionId = reflectionId;
        return responses.update(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        responses.delete(id);
    }

    @ExceptionHandler ({ ResponseForExistingReflection.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleResponseForExistingReflectionException(ResponseForExistingReflection ex) {
        var errorMap = new HashMap<String, String>();
        errorMap.put("error", "Reflection " + ex.reflectionId.toString() + " does not exist");
        return errorMap;
    }
}
