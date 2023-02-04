package Utility;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.Arrays;

public class CourseCheckInsert {
	public boolean checkCourse(String major,int flag, PipedOutputStream out, byte[] buffer, int startIndex, int endIndex, int idx) throws IOException{
		System.out.println(flag);
		switch(major) {
		case "CS" :
			if(flag==0) checkCSCourseInsrt(out, buffer, startIndex, endIndex, idx);
			else if(flag==1){
				checkCSCourseDlt(out, buffer, startIndex, endIndex, idx);
			}
			break;
		case "EE" :
			checkEECourse(out, buffer, startIndex, endIndex, idx);
			break;
		default :
			break;
		}
		
		return false;
	}

	private void checkCSCourseDlt(PipedOutputStream out, byte[] buffer, int startIndex, int endIndex, int idx) throws IOException {
		int check = startIndex;
		boolean fFlag = false; 
		boolean sFlag = false; 
		byte[] tempBuffer = new byte[64];
		
		for(int i=0;i<startIndex;i++) {
     	   out.write((char)buffer[i]);   
        }
		for(int i=0;i<startIndex-endIndex;i++) {
			tempBuffer[i] = buffer[startIndex+i];
		}
		tempBuffer = deleteCode("17651", tempBuffer);
		tempBuffer = deleteCode("17652", tempBuffer);
		
		for(int i=0;tempBuffer[i]!=(byte)0;i++) {
			out.write((char)tempBuffer[i]);
		}
		
	}
	private byte[] deleteCode(String code, byte[] buffer) {
		String temp = "";
		
		for(int i=0;buffer[i]!=(byte)0;i++) {
			temp+=(char)buffer[i];
		}
		Arrays.fill(buffer, (byte)0);
		
		temp = temp.replace(" "+code, "");
		
		for(int i=0;i<temp.length();i++) {
			buffer[i] = (byte) temp.charAt(i);
		}
		
		return buffer;
	}

	private void checkCSCourseInsrt(PipedOutputStream out, byte[] buffer, int startIndex, int endIndex, int idx) throws IOException {
		int check = startIndex;
		boolean fFlag = false;
		boolean sFlag = false;
		
		for(int i=0;i<idx-2;i++) {
     	   out.write((char)buffer[i]);   
        }
		while(check<=endIndex-4) {
			if(fFlag==false) fFlag = checkCode("12345", check, buffer);
			if(sFlag==false) sFlag = checkCode("23456", check, buffer);
			check++;
		}
		if(fFlag && sFlag) finishLine(out, buffer, idx);
		else if(fFlag) {
			insertCode("23456", out);
			finishLine(out, buffer, idx);
		}else if(sFlag) {
			insertCode("12345", out);
			finishLine(out, buffer, idx);
		}else {
			insertCode("12345", out);
			insertCode("23456", out);
			finishLine(out, buffer, idx);
		}
		
	}
	private void checkEECourse(PipedOutputStream out, byte[] buffer, int startIndex, int endIndex, int idx) throws IOException {
		int check = startIndex;
		boolean fFlag = false;
		
		for(int i=0;i<idx-2;i++) {
     	   out.write((char)buffer[i]);   
        }
		while(check<=endIndex-4) {
			if(fFlag==false) fFlag = checkCode("23456", check, buffer);
			check++;
		}
		if(fFlag) finishLine(out, buffer, idx);
		else {
			insertCode("23456", out);
			finishLine(out, buffer, idx);
		}
		
	}
	private void finishLine(PipedOutputStream out, byte[] buffer, int idx) throws IOException{
		out.write((char)buffer[idx-2]);
		out.write((char)buffer[idx-1]);
	}
	private void insertCode(String code, PipedOutputStream out) throws IOException {
		out.write((char)' ');
		for(int i=0;i<5;i++)
			out.write((char)code.charAt(i));
	}
	
	private boolean checkCode(String code, int check, byte[] buffer) {
		char[] codeByte = new char[5];
		for(int i=0;i<codeByte.length;i++) {
			codeByte[i] = code.charAt(i);
		}
		if(buffer[check]==codeByte[0] && buffer[check+1]==codeByte[1] && buffer[check+2]==codeByte[2]
				&&buffer[check+3]==codeByte[3] && buffer[check+4]==codeByte[4]) {
			return true;
		}
		return false;
	}
	

}
