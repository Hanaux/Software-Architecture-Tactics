/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Reservation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Arrays;

import Components.Course.CourseComponent;
import Components.Student.Student;
import Components.Student.StudentComponent;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class ReservationMain {
	static File file;
	static String reservationInfo = "";
	static String studentPrecourse = "";
	static String coursePrecourse = "";

	public static void main(String args[]) throws FileNotFoundException, IOException, NotBoundException, InterruptedException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("** ReservationMain(ID:" + componentId + ") is successfully registered. \n");

		file = new File("Reservation.txt");
		if(!file.exists()) {
			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
		}

		

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
				case MakeReservation:
					printLogEvent("Get", event);
					reservationInfo = event.getMessage();
//					setText("reservationInfo", event.getMessage());
					Thread.sleep(2500);
					if(studentPrecourse.contains("Invalid") && coursePrecourse.contains("Invalid")) {
						Thread.sleep(500);
						eventBus.sendEvent(new Event(EventId.ClientOutput, "Invalid student Id and course Id"));
					} else if(studentPrecourse.contains("Invalid")) { 
						Thread.sleep(500);
						eventBus.sendEvent(new Event(EventId.ClientOutput, "Invalid student Id"));
					}else if(coursePrecourse.contains("Invalid")) {
						Thread.sleep(500);
						eventBus.sendEvent(new Event(EventId.ClientOutput, "Invalid course Id"));
					}else {
						Thread.sleep(500);
						eventBus.sendEvent(new Event(EventId.ClientOutput, checkPrecourse(studentPrecourse, coursePrecourse, reservationInfo)));}
					break;
				case getStudent :
					printLogEvent("Get", event);
					studentPrecourse = event.getMessage();
//					setText("studentPrecourse", event.getMessage());
					System.out.println("Get Student : "+studentPrecourse);
					break;
				case getCourse :
					printLogEvent("Get", event);
					coursePrecourse = event.getMessage();
//					setText("coursePrecourse", event.getMessage());
					System.out.println("Get Course : "+coursePrecourse);
					break;
				case QuitTheSystem :
					printLogEvent("Get", event);
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}

	private static void storeReservation(String reservationInfo) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		if(file.exists()) {
			writer.write(reservationInfo);
			writer.newLine();
			writer.close();
		}
	}
	private static String checkPrecourse(String studentPrecourse, String coursePrecourse,String reservationInfo) throws IOException {
		String alreadyDone = studentPrecourse;
		String[] preCourse;
		boolean flag = false;
		if(coursePrecourse!="") {
			preCourse = coursePrecourse.split(" ");
		}else preCourse = null;
		
		if(preCourse!=null && alreadyDone != "") {
			for(int i=0;i<preCourse.length;i++) {
				flag = false;
				if(alreadyDone.contains(preCourse[i]))
					flag = true;
			}
		}
		System.out.println("already"+alreadyDone+" and "+Arrays.toString(preCourse));
		if(flag) {
			storeReservation(reservationInfo);
			return "Reservation Success!";
		}
		else if(preCourse==null) {
			storeReservation(reservationInfo);
			return "Reservation Success!";
		}
		return "Check your precourse";
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
