package com.Questionnaire.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Questionnaire.Dao.TableDataService;

@RestController
public class TableDataController {
	@Autowired
	TableDataService studentService;
	
	@RequestMapping(value="/getAllScores",method=RequestMethod.GET)
	public  Map<String,Object> getStudents(){
		/*String sql = "SELECT studentbase.studentId,studentName,department,major, score_total,submit_time " +
		        "FROM studentbase LEFT JOIN answer_sheet ON studentbase.studentId = answer_sheet.studentId  " +
		        "WHERE studentbase.studentId regexp '^[a-z][a-z]15' ORDER BY submit_time desc";
	*/
		String sql = "select answer_sheet.studentId,studentName,department,major, score_total,submit_time "
				+ "from answer_sheet left join studentbase on answer_sheet.studentId = studentbase.studentId "
				+ "where TO_DAYS(NOW()) - TO_DAYS(submit_time) <= 30 "
				+ "order by submit_time desc";
		List students = studentService.getStudents(sql);
		
		Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", "true");
        response.put("data", students);
        response.put("total",  students.size());
        return response;
	}
	
	@RequestMapping(value="/getScores",method=RequestMethod.POST)
	public  Map<String,Object> getStudents(@RequestBody Map<String, Object> req){
		Map filter = (Map) req.get("filter");
		
		/*String sql = "SELECT studentbase.studentId,studentName,department,major, score_total,submit_time " +
		        "FROM studentbase LEFT JOIN answer_sheet ON studentbase.studentId = answer_sheet.studentId " +
		        "WHERE studentbase.studentId regexp '^[a-z][a-z]15'";
		*/
		
		String sql = "select answer_sheet.studentId,studentName,department,major, score_total,submit_time "
				+ "from answer_sheet left join studentbase on answer_sheet.studentId = studentbase.studentId "
				+ "where submit_time>'"+filter.get("date_from")+" 00:00:00' "
				+ "and submit_time<='"+filter.get("date_to")+" 23:59:59'";
		
		if (!filter.get("department").equals("all")) {
	        sql = sql + " AND studentbase.department = '" + filter.get("department") + "'";
	    }

	    if (filter.get("score").equals("not_pass")) {
	        sql = sql + " AND score_total != 100";
	    }

	    if (filter.get("score").equals("unfilled")) {
	        sql = sql + " AND score_total IS NULL";
	    }
	    
	    if(filter.get("score").equals("full_mark")){
	    	sql = sql + " AND score_total = 100";
	    }
		
		List students = studentService.getStudents(sql);
		
		Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", "true");
        response.put("data", students);
        response.put("total",  students.size());
        return response;
	}
	

}
