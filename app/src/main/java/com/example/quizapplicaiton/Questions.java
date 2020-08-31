package com.example.quizapplicaiton;

public class Questions {
    String question;
    String optnA;
    String optnB;
    String optnC;
    String optnD;
    int CorrectAnswer;

    public Questions(String question, String optnA, String optnB, String optnC, String optnD, int correctAnswer) {
        this.question = question;
        this.optnA = optnA;
        this.optnB = optnB;
        this.optnC = optnC;
        this.optnD = optnD;
        CorrectAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptnA() {
        return optnA;
    }

    public void setOptnA(String optnA) {
        this.optnA = optnA;
    }

    public String getOptnB() {
        return optnB;
    }

    public void setOptnB(String optnB) {
        this.optnB = optnB;
    }

    public String getOptnC() {
        return optnC;
    }

    public void setOptnC(String optnC) {
        this.optnC = optnC;
    }

    public String getOptnD() {
        return optnD;
    }

    public void setOptnD(String optnD) {
        this.optnD = optnD;
    }

    public int getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        CorrectAnswer = correctAnswer;
    }
}
