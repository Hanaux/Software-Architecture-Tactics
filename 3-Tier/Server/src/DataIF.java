import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DataIF extends Remote {
	ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException;
	ArrayList<Course> getAllCourseData() throws RemoteException, NullDataException;
	String getStudentReservation(String studentId)throws RemoteException, NullDataException;
	boolean addStudent(String studentInfo) throws RemoteException, IOException;
	boolean deleteStudent(String studentId) throws RemoteException, IOException;
	boolean deleteReservFile(String studentId) throws RemoteException, IOException;
	boolean addCourse(String studentInfo) throws RemoteException, IOException;
	boolean deleteCourse(String studentId) throws RemoteException, IOException;
	boolean checkStudentId(String studentId) throws RemoteException;
	boolean checkCourseId(String courseId) throws RemoteException;
	ArrayList<String> isPreCourse(String courseId) throws RemoteException;
	String addReservation(String studentId, String courseId) throws FileNotFoundException, IOException;
	String getStudentPreCourse(String studentId)throws RemoteException;


}

