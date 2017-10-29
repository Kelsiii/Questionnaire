package com.Questionnaire.Model;

public class Answer {
	private String answerId;
	private String studentId;
	private String questionnaireId;
	private String submit_time;
	private String answer;
	private int score_total;
	private String score_detail;
	
	public Answer() {
	    
	}
	
	public Answer(String answerId,String studentId,String questionnaireId,String submit_time,
			String answer,int score_total,String score_detail) {
		this.answerId = answerId;
		this.studentId = studentId;
		this.questionnaireId = questionnaireId;
		this.submit_time = submit_time;
		this.answer = answer;
		this.score_total = score_total;
		this.score_detail = score_detail;
	    
	}
	
	public boolean isNull(){
		if(this.answerId == null)
			return true;
		else
			return false;
	}
	
	public void setAnswerId(String s){
		this.answerId = s;
	}
	public String getAnswerId(){
		return this.answerId;
	}
	
	public void setStudentId(String s){
		this.studentId = s;
	}
	public String getStudentId(){
		return this.studentId;
	}
	
	public void setQuestionnaireId(String s){
		this.questionnaireId = s;
	}
	public String getQuestionnaireId(){
		return this.questionnaireId;
	}
	
	public void setSubmitTime(String s){
		this.submit_time = s;
	}
	public String getSubmitTime(){
		return this.submit_time;
	}
	
	public void setAnswer(String s){
		this.answer = s;
	}
	public String getAnswer(){
		return this.answer;
	}
	
	public void setScoreTotal(int i){
		this.score_total = i;
	}
	public int getScoreTotal(){
		return this.score_total;
	}
	
	public void setScoreDetail(String s){
		this.score_detail = s;
	}
	public String getScoreDetail(){
		return this.score_detail;
	}
	

}
