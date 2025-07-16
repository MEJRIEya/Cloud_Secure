package com.cloudsecure.backend.dtos;

public class UserResponseDTO {
    private Long questionId;
    private String response;

    public Long getQuestionId() {
        return questionId;
    }

    public String getResponse() {
        return response;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    // getters/setters
}

