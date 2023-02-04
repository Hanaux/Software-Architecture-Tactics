import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {

	public static void main(String[] args)throws NotBoundException, IOException {
		ServerIF server;
		
		BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			server = (ServerIF)Naming.lookup("Server");
			while(true) {
			printMethod();
			String sChoice = objReader.readLine().trim();
			switch(sChoice) {
			case "1" :
				showList(server.getAllStudentData());
				break;
			case "2" :
				showList(server.getAllCourseData());
				break;
			case "3" :
				addStudent(server, objReader);
				break;
			case "4" :
				deleteStudent(server, objReader);
				break;
			case "5" :
				addCourse(server, objReader);
				break;
			case "6" :
				deleteCourse(server, objReader);
				break;
			case "7" :
				getStudentPreCourse(server, objReader);
				
				break;
			case "8" :
				//������û (studentId, courseId) => �������� �� ��
				//�л��� �ִ� �л����� üũ, ���� ��������. �������� üũ exception ����.
				makeReservation(server, objReader);
				break;
			case "9" :
				getStudentReservation(server, objReader);
				break;
			case "x" :
				return;
			default :
				System.out.println("Invalid Choice !!!");	
			}
			
		} 
		}
		catch (RemoteException e) {
			e.printStackTrace();
		} catch (NullDataException e) {
			e.printStackTrace();
		} 
	}
	private static void getStudentReservation(ServerIF server, BufferedReader objReader) throws RemoteException, IOException, NullDataException {
		System.out.println("Enter Student Id :");
		String studentId = objReader.readLine().trim();
		if(areStudent(server, studentId)) {
			System.out.println(studentId+"�� ������û ����ID�� ������ �����ϴ�\n");
			System.out.println(server.getStudentReservation(studentId));
		} else {
			System.out.println(studentId+"�� ���� �л� �����Դϴ�.");
		}
	}
	private static void getStudentPreCourse(ServerIF server, BufferedReader objReader) throws RemoteException, IOException, NullDataException {
		System.out.println("Enter Student Id :");
		String studentId = objReader.readLine().trim();
		if(areStudent(server, studentId)) {
			System.out.println("["+studentId+"]�� �̹� �̼��� Course Id�� "+server.getStudentPreCourse(studentId)+"�Դϴ�.");
		} else {
			System.out.println(studentId+"�� ���� �л� �����Դϴ�.");
		}		
	}
	private static void makeReservation(ServerIF server, BufferedReader objReader) throws RemoteException, IOException, NullDataException {
		System.out.println("----------Make Reservation----------");
		System.out.println("StudentID:"); String studentId = objReader.readLine().trim();
		System.out.println("CourseID:"); String courseId = objReader.readLine().trim();
		System.out.println(server.makeReservation(studentId, courseId));
		if(!server.makeReservation(studentId, courseId).contains("No")) {
		System.out.println("���� ��û �Ͻðڽ��ϱ�? (Y/N)");
		String choice = objReader.readLine().trim();
		switch(choice) {
		case "Y" : case "y" :
			addReservation(server, studentId, courseId);
			break;
		case "N" : case "n" :
			System.out.println("���� ȭ������ ���ư��ϴ�...");
			break;
		default :
			System.out.println("Invalid Choice !!!");
		}
		}
	
	}
	private static void addReservation(ServerIF server, String studentId, String courseId) throws NullDataException, FileNotFoundException, IOException {
		System.out.println("----------checkReservation----------");
		System.out.println(server.addReservation(studentId, courseId));
	}
	private static void addStudent(ServerIF server, BufferedReader objReader) throws RemoteException, IOException {
		System.out.println("----------Student Information----------");
		System.out.println("StudentID:"); String studentId = objReader.readLine().trim();
		if(areStudent(server, studentId)) {
			System.out.println("�̹� ��ϵ� �л��Դϴ�.");
		} else {
			System.out.println("Student Last Name:"); String studentlName = objReader.readLine().trim();
			System.out.println("Student First Name:"); String studentfName = objReader.readLine().trim();
			System.out.println("StudentDepartment:"); String studentDept = objReader.readLine().trim();
			System.out.println("Student Completed Course List:"); String completedCourses = objReader.readLine().trim();


			if(server.addStudent(studentId+" "+studentlName+" "+studentfName+" "+studentDept+" "+completedCourses)) System.out.println("SUCCESS");
			else System.out.println("FAIL");
		}

	}
	private static boolean areStudent(ServerIF server, String studentId) throws RemoteException, IOException{
		if(server.areStudent(studentId)) {return true;} 
		else {return false;}
	}
	private static void addCourse(ServerIF server, BufferedReader objReader) throws RemoteException, IOException {
		System.out.println("----------Course Information----------");
		System.out.println("CourseID:"); String courseId = objReader.readLine().trim();
		if(areCourse(server, courseId)) {
			System.out.println("�̹� ��ϵ� �����Դϴ�.");
		}else {
			System.out.println("ProfessorName:"); String prfName = objReader.readLine().trim();
			System.out.println("CourseName:"); String courseName = objReader.readLine().trim();
			System.out.println("PreCourseID:"); String preCourseId = objReader.readLine().trim();
			
			if(server.addCourse(courseId+" "+prfName+" "+courseName+" "+preCourseId)) System.out.println("SUCCESS");
			else System.out.println("FAIL");
		}

	}
	private static boolean areCourse(ServerIF server, String courseId) throws RemoteException, IOException{
		if(server.areCourse(courseId)) {
			return true;
		} else {
			return false;
		}
	}
	private static void deleteStudent(ServerIF server, BufferedReader objReader) throws RemoteException, IOException {
		System.out.print("Student ID: ");
		String studentId = objReader.readLine().trim();
		if(areStudent(server, studentId)) {
			if(server.deleteStudent(studentId)) {
				System.out.println("SUCCESS");
			}else System.out.println("FAIL");
		} else {
			 System.out.println("�ش� Id�� �л��� �������� �ʽ��ϴ�.");
		}

	}
	private static void deleteCourse(ServerIF server, BufferedReader objReader) throws RemoteException, IOException {
		System.out.print("Course ID: ");
		String courseId = objReader.readLine().trim();
		if(areCourse(server,courseId)) {
			if(server.deleteCourse(courseId)) {
				System.out.println("SUCCESS");
			}else System.out.println("FAIL");
		} else {
			System.out.println("�ش� Id�� ���°� �������� �ʽ��ϴ�.");
		}
	}
	private static void showList(ArrayList<?> dataList) throws RemoteException { // �� ��� �˾Ƴ���
		String list = "";
		for(int i=0;i<dataList.size();i++) {
//			list += dataList.get(i) + "\n";
			System.out.println(dataList.get(i).toString());
		}
//		System.out.println(list);
	}

	private static void printMethod() { // code refactoring �ҽ��ڵ��� ������ ���̱� ����. 
		System.out.println("��������������M��E��N��U������������������");
		System.out.println("1.List Students"); //��
		System.out.println("2.List Courses");
		System.out.println("3.Add Student");
		System.out.println("4.Delete Student");
		System.out.println("5.Add Course");
		System.out.println("6.Delete Course"); 
		System.out.println("7.What Student Done");
		System.out.println("8.Make Reservation");// student ID�� ���� ID�� �ְ� ó���ذ�����?
		System.out.println("9.Check Reservation");
		System.out.println("x.Exit");
	}

}
