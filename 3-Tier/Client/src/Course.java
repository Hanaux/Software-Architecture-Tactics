import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String courseId;
    protected String prfFName;
    protected String courseName;
    
    protected ArrayList<String> completedPreCourseList;
 
    public Course(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
    	this.courseId = stringTokenizer.nextToken();
    	this.prfFName = stringTokenizer.nextToken();   	
    	this.courseName = stringTokenizer.nextToken();
    	
    	if(this.courseName.length()<8)
    		this.courseName = this.courseName+"\t\t\t\t";
    	else if(this.courseName.length()<16)
    		this.courseName = this.courseName+"\t\t\t";
    	else if(this.courseName.length()<24)
    		this.courseName = this.courseName+"\t\t";
    	else if(this.courseName.length()<32)
    		this.courseName = this.courseName+"\t";
    	
    	this.completedPreCourseList = new ArrayList<String>();
    	while (stringTokenizer.hasMoreTokens()) {
    		this.completedPreCourseList.add(stringTokenizer.nextToken());
    	}
    }

    public boolean match(String courseId) {
        return this.courseId.equals(courseId);
    }

    public String getprfFName() {
        return this.prfFName;
    }

    public String getcourseName() {
        return this.courseName;
    }
    
    public ArrayList<String> getCompletedCourses() {
        return this.completedPreCourseList;
    }

    public String toString() {
        String stringReturn = this.courseId + "\t" + this.prfFName + "\t" + this.courseName;
        for (int i = 0; i < this.completedPreCourseList.size(); i++) {
            stringReturn = stringReturn + "\t" + this.completedPreCourseList.get(i).toString();
        }
        return stringReturn;
    }
}
