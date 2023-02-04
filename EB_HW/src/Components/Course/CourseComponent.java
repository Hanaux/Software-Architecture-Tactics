/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University
 */
package Components.Course;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CourseComponent {
    public ArrayList<Course> vCourse;
    protected String filePath;
    protected BufferedWriter writer;

    public CourseComponent(String sCourseFileName) throws FileNotFoundException, IOException { 	
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(sCourseFileName));       
        this.vCourse  = new ArrayList<Course>();
        this.filePath = sCourseFileName;
        while (bufferedReader.ready()) {
            String courseInfo = bufferedReader.readLine();
            if(!courseInfo.equals("")) this.vCourse.add(new Course(courseInfo));
        }    
        bufferedReader.close();
    }
    public ArrayList<Course> getCourseList() {
        return this.vCourse;
    }
    public boolean isRegisteredCourse(String courseId) {
        for (int i = 0; i < this.vCourse.size(); i++) {
            if(((Course) this.vCourse.get(i)).match(courseId)) return true;
        }
        return false;
    }
    public String getPreCourseSet(String courseId) {
    	ArrayList<String> prerequisite;
    	String sendPreQ = "";
    	
    	 for (int i = 0; i < this.vCourse.size(); i++) {
             if(((Course) this.vCourse.get(i)).match(courseId)) {
            	 prerequisite = this.vCourse.get(i).getPrerequisite();
            	 for(int j=0;j<prerequisite.size();j++) {
            		sendPreQ += (prerequisite.get(j)+" "); 
            	 }
             }
         }
    	 return sendPreQ;
    }
    public void storeCourse() throws IOException{
    	this.writer = new BufferedWriter(new FileWriter(this.filePath));
    	for(int i=0;i<vCourse.size();i++) {
    		this.writer.write((this.vCourse.get(i)).getString());
    		this.writer.newLine();
    	}
    	this.writer.close();
    }
}
