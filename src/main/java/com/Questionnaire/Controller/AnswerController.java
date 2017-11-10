package com.Questionnaire.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Questionnaire.Dao.AnswerService;
import com.Questionnaire.Model.Answer;

@RestController
public class AnswerController {
	
	@Autowired
	AnswerService answerService;

	@RequestMapping(value="/submit",method=RequestMethod.POST)
	public  Map<String,Object> handleAnswerSubmit(@RequestBody Map<String, Object> req){
		
		List part1 = (List) req.get("single");
		List part2 = (List) req.get("multiple");
		List part3 = (List) req.get("judgement");
		
		String answers = StringUtils.collectionToDelimitedString(part1, ",")
				+ ',' + StringUtils.collectionToDelimitedString(part2, ",") 
				+ ',' + StringUtils.collectionToDelimitedString(part3, ",");
		
		
		String[] answer_part1 = {"C", "A", "A", "C", "C", "C", "B", "C", "A", "A"};
		String[] answer_part2 = {"ABCD", "ABCDE", "ABCEFG", "ABD", "BCD", "ACD", "BCD", "ABCDEF",
				"ABCD", "ABD"};
        String[] answer_part3 ={"T", "F", "F", "T", "F", "F", "T", "T", "T", "F",
        		"F", "T", "T", "F", "T", "F", "F", "T", "T", "F"};
        
        /*calculate score*/
		ArrayList<Integer> scores = new ArrayList<Integer>();
		int score_total = 0;
		
		for (int i = 0; i < answer_part1.length; i++) {
	        if (answer_part1[i].equals(part1.get(i).toString())) {
	            scores.add(2);
	            score_total = score_total + 2;
	        } else {
	            scores.add(0);
	        }
	    }
		for (int i = 0; i < answer_part2.length; i++) {
	        if (answer_part2[i].equals(part2.get(i).toString())) {
	            scores.add(4);
	            score_total = score_total + 4;
	        } else {
	            scores.add(0);
	        }
	    }
		for (int i = 0; i < answer_part3.length; i++) {
	        if (answer_part3[i].equals(part3.get(i).toString())) {
	            scores.add(2);
	            score_total = score_total + 2;
	        } else {
	            scores.add(0);
	        }
	    }
		String score_detail = StringUtils.collectionToDelimitedString(scores, ",");
		
		
		String now = Long.toString(new Date().getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		
		String student_id = (String) req.get("student");
		//System.out.println(student_id);
		
		/*check if exist record from same person*/
		Answer validAnswer = answerService.getValidAnswer(student_id);
		
		
		Answer answerRecord = new Answer();
		answerRecord.setAnswerId(now);
		answerRecord.setStudentId(student_id);
		answerRecord.setQuestionnaireId("1");
		answerRecord.setAnswer(answers);
		answerRecord.setScoreDetail(score_detail);
		answerRecord.setScoreTotal(score_total);
		answerRecord.setSubmitTime(time);
		
		if(validAnswer.isNull()){
			answerService.insertAnswerRecord(answerRecord);
		}
		else if(validAnswer.getScoreTotal() < score_total){
			answerService.updateAnswerRecord(answerRecord);
		}
		
		Map<String, Object> response = new LinkedHashMap<>();
        response.put("score_total", score_total);
        response.put("scores",scores);
        return response;

	}
}
