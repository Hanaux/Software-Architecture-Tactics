import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Data extends UnicastRemoteObject implements DataIF{
	
	private static final long serialVersionUID = 1L;
	protected static StudentList studentList;
	protected static CourseList courseList;
	protected static ReservationList reservList;
	
	protected Data() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		try {
			Data data = new Data();
			Naming.rebind("Data", data);
			System.out.println("Data is ready...");
			
			studentList = new StudentList("Students.txt");
			courseList = new CourseList("Courses.txt");

			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException {
		return studentList.getAllStudentRecords();
	}
	@Override
	public ArrayList<Course> getAllCourseData() throws RemoteException, NullDataException {
		return courseList.getAllCourseRecords();
	}
	@Override
	public String getStudentReservation(String studentId) throws RemoteException,NullDataException, IOException {
//		return reservList.getStudentReservation(studentId);
		this.reservList = new ReservationList(studentId);
		return this.reservList.getStudentReservation();
	}
	@Override
	public boolean addStudent(String studentInfo) throws RemoteException, IOException {
		if(studentList.addStudentRecords(studentInfo)) return true;
		else return false;
	}

	@Override
	public boolean deleteStudent(String studentId) throws RemoteException, IOException {
		if(studentList.deleteStudentRecords(studentId)) return true;
		else return false;
	}
	@Override
	public boolean deleteReservFile(String studentId) throws RemoteException, IOException {
		this.reservList = new ReservationList(studentId);
		if(this.reservList.deleteFile()) return true;
		else return false;
	}
	@Override
	public boolean addCourse(String courseInfo) throws RemoteException, IOException {
		if(courseList.addCourseRecords(courseInfo)) return true;
		else return false;
	}

	@Override
	public boolean deleteCourse(String courseId) throws RemoteException, IOException {
		if(courseList.deleteCourseRecords(courseId)) return true;
		else return false;
	}

	@Override
	public boolean checkStudentId(String studentId) throws RemoteException {
		if(studentList.isRegisteredStudent(studentId)) return true;
		else return false;
	}

	@Override
	public boolean checkCourseId(String courseId) throws RemoteException {
		if(courseList.isRegisteredCourse(courseId)) return true;
		else return false;
	}

	@Override
	public ArrayList<String> isPreCourse(String courseId) throws RemoteException {
		return courseList.getPreCourse(courseId);
	}

	@Override
	public String addReservation(String studentId, String courseId) throws FileNotFoundException, IOException {
		reservList = new ReservationList(studentId);
		String preCoursedLast = getStudentPreCourse(studentId);
		if(reservList.checkReserv(isPreCourse(courseId), preCoursedLast)) { //선수과목 이수 했는가?
			return reservList.addReserv(courseId);
			
		} else return "선수과목이 있습니다. : "+isPreCourse(courseId).toString();
	}

	@Override
	public String getStudentPreCourse(String studentId) throws RemoteException {
		return studentList.studentPrecourse(studentId);
	}	
}
