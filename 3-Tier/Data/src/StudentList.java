
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StudentList {
	protected ArrayList<Student> vStudent;
	protected BufferedWriter writer;
	protected String filePath;

	public StudentList(String sStudentFileName) throws FileNotFoundException, IOException {
		BufferedReader objStudentFile = new BufferedReader(new FileReader(sStudentFileName));
		this.vStudent = new ArrayList<Student>();
		this.filePath = sStudentFileName;
		while (objStudentFile.ready()) {
			String stuInfo = objStudentFile.readLine();
			if (!stuInfo.equals("")) {
				this.vStudent.add(new Student(stuInfo));
			}
		}
		objStudentFile.close();
	}

	public ArrayList<Student> getAllStudentRecords() throws NullDataException {
		if(this.vStudent.size() ==0) throw new NullDataException("~~~~~~ Student Data is null ~~~~~~");
		return this.vStudent;
	}
	
	private void storeStudentList() throws IOException{
		this.writer = new BufferedWriter(new FileWriter(this.filePath));
		for(int i=0;i<this.vStudent.size();i++ ) {
			this.writer.write(this.vStudent.get(i).toString());
			this.writer.newLine();
		}
		this.writer.close();
		
	}
	
	public boolean addStudentRecords(String studentInfo) throws IOException {

		if(this.vStudent.add(new Student(studentInfo))) {
			storeStudentList();
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteStudentRecords(String studentID) throws IOException {
		for (int i = 0; i < this.vStudent.size(); i++) {
			Student student = (Student) this.vStudent.get(i);
			if (student.match(studentID)) {
				if (this.vStudent.remove(student)) {
					storeStudentList();
					return true;
				}
				else return true;
			}
		}
		return false;
	}

	public boolean isRegisteredStudent(String sSID) {
		for (int i = 0; i < this.vStudent.size(); i++) {
			Student objStudent = (Student) this.vStudent.get(i);
			if (objStudent.match(sSID)) {
				return true;
			}
		}
		return false;
	}
	
	public String studentPrecourse(String sSID) {
		for(int i=0;i<this.vStudent.size();i++) {
			Student objStudent = (Student)this.vStudent.get(i);
			if(objStudent.match(sSID)) {
				return objStudent.completedCoursesList.toString();
			}
		}
		return "No Student exist";
	}
}
