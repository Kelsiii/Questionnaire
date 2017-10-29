package com.Questionnaire.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;

import com.Questionnaire.Model.Answer;

@Service
public class AnswerService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insertAnswerRecord(Answer answer) {
		
		final String answer_id = answer.getAnswerId();
		final String student_id = answer.getStudentId();
		final String answer_detail = answer.getAnswer();
		final String submit_time = answer.getSubmitTime();
		final int score_total = answer.getScoreTotal();
		final String score_detail = answer.getScoreDetail();
		
		jdbcTemplate.update("INSERT INTO answer_sheet"
				+ "(answerId,studentId,questionnaireId,answer,submit_time,score_total,score_detail)" +
		        "VALUES(?,?,1,?,?,?,?)",  
                new PreparedStatementSetter() {  
            public void setValues(PreparedStatement ps) throws SQLException { 
            	ps.setString(1, answer_id);  
                ps.setString(2, student_id);            
                ps.setString(3, answer_detail);  
                ps.setString(4, submit_time);  
                ps.setInt(5, score_total);
                ps.setString(6, score_detail); 
            }
        }); 
	}
	
	public Answer getValidAnswer(String id){
		String sql = "SELECT * FROM answer_sheet WHERE studentId=?";  
	    final Object[] params = new Object[] { id };  
	    List list = jdbcTemplate.query(sql, params, new RowMapperResultSetExtractor(new AnswerRowMapper()));  
	  
	    if(list.size()>0){
		    return (Answer) list.get(0);  
	    }
	    else{
	    	return new Answer();
	    }
	}
	
	public void updateAnswerRecord(Answer answer){
		String sql = "UPDATE answer_sheet SET answerId=?,questionnaireId=?,answer=?,submit_time=?,score_total=?,score_detail=? "
				+ "WHERE studentId = ?";
		final Object[] params = new Object[] {
				answer.getAnswerId(),
				answer.getQuestionnaireId(),
				answer.getAnswer(),
				answer.getSubmitTime(),
				answer.getScoreTotal(),
				answer.getScoreDetail(),
				answer.getStudentId()
				}; 
		jdbcTemplate.update(sql, params); 
	}
}

class AnswerRowMapper implements RowMapper {  
    public Object mapRow(ResultSet rs, int index) throws SQLException {  
        Answer answer = new Answer();  
  
        answer.setAnswerId(rs.getString("answerId"));  
        answer.setStudentId(rs.getString("studentId"));
        answer.setQuestionnaireId(rs.getString("questionnaireId"));
        answer.setAnswer(rs.getString("answer"));
        answer.setScoreDetail(rs.getString("score_detail"));
        answer.setSubmitTime(rs.getString("submit_time"));
        answer.setScoreTotal(rs.getInt("score_total"));
  
        return answer;  
    }

}
