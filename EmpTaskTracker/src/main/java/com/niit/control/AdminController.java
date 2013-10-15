package com.niit.control;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.niit.pojo.Person;

@Controller
public class AdminController  {


	@RequestMapping(value = "/redirect.login", method = RequestMethod.GET)
	public ModelAndView redirectLoginActivity(HttpServletResponse response) {
		ModelAndView mvc=new ModelAndView("login");
		writeJSON();
		return mvc;
	}

	
	
	
	@RequestMapping(value = "/user.login", method = RequestMethod.POST)
	public ModelAndView loginActivity(HttpServletResponse response,Person person){
		ModelAndView mvc=null;
		
		JSONParser parser=new JSONParser();
				try {
					 
					Object obj = parser.parse(new FileReader("C:\\Users\\sandeep.1.srivastava\\EmpTaskTracker\\data\\"+person.getEmpID()+".json"));
			 
					JSONObject jsonObject = (JSONObject) obj;
			 
					String password = (String) jsonObject.get("password");
					if(password.equals(person.getPassword())){
					
					System.out.println("Password is OK");
					} else{
						mvc=new ModelAndView("notcorrect");
					}
				} catch (FileNotFoundException e) {
					mvc=new ModelAndView("notadded");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (org.json.simple.parser.ParseException e) {
					e.printStackTrace();
				}
		return mvc;
	}

	
	@SuppressWarnings("unchecked")
	public void writeJSON(){
		JSONObject obj = new JSONObject();
		obj.put("password", "JSON");
		
		try {
			 
			FileWriter file = new FileWriter("C:\\Users\\sandeep.1.srivastava\\EmpTaskTracker\\data\\53694.json");
			file.write(obj.toJSONString());
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}

		

	}
	
	
}
