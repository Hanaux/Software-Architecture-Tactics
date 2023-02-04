import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements ServerIF {

	private static DataIF data;
	private static final long serialVersionUID = 1L;
	
	protected Server() throws RemoteException{
		super();
	}

	public static void main(String[] args) throws NotBoundException {
		try {
			Server server = new Server();
			Naming.rebind("Server", server);
			System.out.println("Server is ready...");
			
			// server�� rmi�� ȣ���ؾ��ϴϱ�.
			data = (DataIF)Naming.lookup("Data");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException { //����Ÿ�� �ִ��� ������ Ȯ�����ִ°� �ʿ���. Exceptionó��.. 
		// ������ ���� ��© �� �ֱ� ����. �׷��� ������ ��� �ȵȰ��� ��������� 
		return data.getAllStudentData();
	}
	@Override
	public ArrayList<Course> getAllCourseData() throws RemoteException, NullDataException {
		return data.getAllCourseData();
	}
	@Override
	public String getStudentReservation(String studentId) throws RemoteException, NullDataException {
		// TODO Auto-generated method stub
		return data.getStudentReservation(studentId);
	}
	@Override
	public boolean addStudent(String studentInfo) throws RemoteException, IOException {
		if(data.addStudent(studentInfo)) return true;
		else return false;
	}
	@Override
	public boolean deleteStudent(String studentId) throws RemoteException, IOException {
		if(data.deleteStudent(studentId)) {
			data.deleteReservFile(studentId);
			return true;
		}
		else return false;
	}
	@Override
	public boolean addCourse(String courseInfo) throws RemoteException, IOException {
		if(data.addCourse(courseInfo)) return true;
		else return false;
	}

	@Override
	public boolean deleteCourse(String courseId) throws RemoteException, IOException {
		if(data.deleteCourse(courseId)) return true;
		else return false;
	}

	@Override
	public String makeReservation(String studentId, String courseId) throws RemoteException, NullDataException {
		if(!data.checkStudentId(studentId)) return "No Student Exist";
		else if(!data.checkCourseId(courseId)) return "No Course Exist";
		else return "Ready to Reservation";
	}

	@Override
	public String addReservation(String studentId, String courseId) throws NullDataException, IOException {
		return data.addReservation(studentId, courseId);
	}

	@Override
	public String getStudentPreCourse(String studentId) throws RemoteException {
		
		return data.getStudentPreCourse(studentId);
	}

	@Override
	public boolean areStudent(String studentInfo) throws RemoteException, IOException {
		if(data.checkStudentId(studentInfo)) return true;
		else return false;
	}
	@Override
	public boolean areCourse(String courseInfo) throws RemoteException, IOException {
		if(data.checkCourseId(courseInfo)) return true;
		else return false;
	}
}
// server�� ������ ������ �� �־����. �ֳĸ� ���� �ٽ� �����Ű�� �� �������ݾ�
//return name �ع����� null�� �� �� ������, üũ�ϴ� �ڵ带 ������ �� if else�� exception ó���ϼ� (if ���� try catch��)
//naming �ƹ����Գ� ���� �� ��. �ڵ� �T �� �� �ְ� �� ��.