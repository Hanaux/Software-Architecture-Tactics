package Utility;

public class StudentCheck {
	
	public boolean majorCheck(byte[] buffer, int idx, String major){
		char first = major.charAt(0);
		char second = major.charAt(1);
		
		if(buffer[idx-3] == first && buffer[idx-2] == second) {
			return true;
		}
		return false;
	}
	
	public boolean yearCheck(byte[] buffer, int idx, String year) {
		String studentYear = "";
		
		for(int i=0;i<4;i++) 
			studentYear += (char)buffer[i];
		
		if(year.matches(studentYear)) return true;
		else return false;

	}

}
