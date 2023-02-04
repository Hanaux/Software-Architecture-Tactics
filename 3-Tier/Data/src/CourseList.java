import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CourseList {
	protected ArrayList<Course> vCourse;
	protected BufferedWriter writer;
	protected String filePath;

	public CourseList(String sCourseFileName) throws FileNotFoundException, IOException {
		BufferedReader objCourseFile = new BufferedReader(new FileReader(sCourseFileName));
		this.vCourse = new ArrayList<Course>();
		this.filePath = sCourseFileName;
		while (objCourseFile.ready()) {
			String crsInfo = objCourseFile.readLine();
			if (!crsInfo.equals("")) {
				this.vCourse.add(new Course(crsInfo));
			}
		}
		objCourseFile.close();
	}

	public ArrayList<Course> getAllCourseRecords() throws NullDataException{
		if(this.vCourse.size() ==0) throw new NullDataException("~~~~~~ Course Data is null ~~~~~~");
		return this.vCourse;
	}
	private void storeCourseList() throws IOException {
		this.writer = new BufferedWriter(new FileWriter(this.filePath));
		for(int i=0;i<vCourse.size();i++) {
			this.writer.write(this.vCourse.get(i).toString());
			this.writer.newLine();
		}
		this.writer.close();
	}
	public boolean addCourseRecords(String courseInfo) throws IOException{ //file���� ����������. ����. �׷��� Data �׿��� �ٽ� �� �� �ְ�, �ٵ� File write�� �ϰ� �Ǹ� ������. Data Server�� �װų� ���������� ���� �־������. Array �� ������ �ִٰ�, Distruction�̶��� �ѹ� ���� ��
		if(this.vCourse.add(new Course(courseInfo))) {
			storeCourseList();
			return true;
		}
		else return false;
	}
	
	public boolean deleteCourseRecords(String courseID) throws IOException {
		for (int i = 0; i < this.vCourse.size(); i++) {
			Course course = (Course) this.vCourse.get(i);
			if (course.match(courseID)) {
				if (this.vCourse.remove(course)) {
					storeCourseList();
					return true;
				}
				else return true;
			}
		}
		return false;
	}

	public ArrayList<String> getPreCourse(String courseId) {
		for(int i=0;i<this.vCourse.size();i++) {
			Course course = (Course)this.vCourse.get(i);
			if(course.match(courseId)) {
				return course.completedPreCourseList;
			}
		}
		return null;
	}
	public boolean isRegisteredCourse(String sCID) {
		for (int i = 0; i < this.vCourse.size(); i++) {
			Course objCourse = (Course) this.vCourse.get(i);
			if (objCourse.match(sCID)) {
				return true;
			}
		}
		return false;
	}
}
