package com.Questionnaire.Controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Questionnaire.Dao.DepartmentService;

@RestController
public class DepartmentController {
	@Autowired
	DepartmentService departmentSerivce;
	
	@RequestMapping(value="/getDepartments",method=RequestMethod.GET)
	public  Map<String,Object> getDepartments(){
		List departments = departmentSerivce.getAllDepartments();
		
		Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", "true");
        response.put("departments_list", departments);
        return response;

	}
}
