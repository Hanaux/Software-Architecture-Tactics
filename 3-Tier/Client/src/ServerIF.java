import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerIF extends Remote {
	ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException;
	ArrayList<Course> getAllCourseData() throws RemoteException, NullDataException;
	String getStudentReservation(String studentId)throws RemoteException, NullDataException;
	boolean addStudent(String studentInfo) throws RemoteException, IOException;
	boolean areStudent(String studentInfo) throws RemoteException, IOException;
	boolean deleteStudent(String studentId) throws RemoteException, IOException;
	boolean addCourse(String studentInfo) throws RemoteException, IOException;
	boolean areCourse(String courseInfo) throws RemoteException, IOException;
	boolean deleteCourse(String studentId) throws RemoteException, IOException;
	String addReservation(String studentId, String courseId) throws RemoteException, NullDataException, FileNotFoundException, IOException;
	String makeReservation(String studentId, String courseId)throws RemoteException, NullDataException;
	String getStudentPreCourse(String trim)throws RemoteException;

}