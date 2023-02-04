package Components.Add;

import java.io.IOException;

import Framework.CommonFilterImpl;
import Utility.CourseCheckInsert;

public class AddFilter extends CommonFilterImpl{ //utility.java를 만들어서 공통적으로 쓰는 코드를 몰아넣고 불러쓰자. 값만 주고 바꿔쓰는 방식으로. "중복코드 없애는게 중요함" 최대한 줄이자.
	String major;
	int flag;
	
	public AddFilter(String major, int flag) {
    	this.major = major;
    	this.flag = flag;
    }
	@Override 
    public boolean specificComputationForFilter() throws IOException { 
    	int checkBlank = 4; 
        int numOfBlank = 0;
        int idx = 0;
        int startIndex = 0;
        int endIndex = 0;
        byte[] buffer = new byte[64];
        int byte_read = 0;
        
        while(true) {          
           while(byte_read != '\n' && byte_read != -1) { //temp
        	   byte_read = in.read();
        	   if(byte_read == ' ') numOfBlank++;
        	   if(byte_read != -1) buffer[idx++] = (byte)byte_read;
        	   if(numOfBlank==checkBlank) startIndex = idx-5;
        	   if(byte_read =='\n') endIndex = idx-3;
           }

           checkMajorCourse(buffer, startIndex, endIndex, idx);
        	
            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
        }
    }

	private void checkMajorCourse(byte[] buffer, int startIndex, int endIndex, int idx) throws IOException {
		CourseCheckInsert courseCheck = new CourseCheckInsert();
		courseCheck.checkCourse(major,this.flag, out, buffer, startIndex, endIndex, idx);
		
	}

}
