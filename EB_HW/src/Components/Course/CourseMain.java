/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class CourseMain {
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("CourseMain (ID:" + componentId + ") is successfully registered...");

		CourseComponent coursesList = new CourseComponent("Courses.txt");
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = 0; i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case ListCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeCourseList(coursesList)));
					break;
				case RegisterCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerCourse(coursesList, event.getMessage())));
					break;
				case QuitTheSystem:
					eventBus.unRegister(componentId);
					done = true;
					break;
				case DeleteCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, deleteCourses(coursesList, event.getMessage())));
					break;
				case setCourse:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.getCourse, setCourse(coursesList, event.getMessage())));
					break;
				default:
					break;
				}
			}
		}
	}
	private static String setCourse(CourseComponent coursesList, String message) throws IOException {
		String preCourse = null;
		preCourse = coursesList.getPreCourseSet(message);
		
		if(!coursesList.isRegisteredCourse(message)) {
			return "Invalid Course";
		}
		return preCourse;
	}
	private static String deleteCourses(CourseComponent coursesList, String message) throws IOException {

			for(int i=0;i<coursesList.vCourse.size();i++) {
				if(((Course)coursesList.vCourse.get(i)).match(message) ) {
					coursesList.vCourse.remove(i);
					store(coursesList);
					return "This course is successfully deleted";
					}	
				} return "This course isn't exist in data";
		
	}
	private static String registerCourse(CourseComponent coursesList, String message) throws IOException{
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.add(course);
			store(coursesList);
			return "This course is successfully added.";
		} else
			return "This course is already registered.";
	}
	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = "";
		for (int j = 0; j < coursesList.vCourse.size(); j++) {
			returnString += coursesList.getCourseList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static void store(CourseComponent coursesList) throws IOException{
		coursesList.storeCourse();
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
