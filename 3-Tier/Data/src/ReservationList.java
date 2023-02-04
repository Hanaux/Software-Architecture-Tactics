import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReservationList {
	protected File file;
	protected BufferedWriter writer;
	protected BufferedReader reader;
	
	public ReservationList(String fileName) throws FileNotFoundException, IOException {
		String filePath = fileName;
		this.file = new File(filePath);
		if(!file.exists()) {
		FileOutputStream fileOutputStream = new FileOutputStream(file, true);
		}
		this.writer = new BufferedWriter(new FileWriter(file, true));
	}
	public boolean checkReserv(ArrayList<String> reservList, String preCoursed)throws IOException {
		if(file.exists()) {
			String preList = "";
			this.reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = this.reader.readLine())!=null) {preList += line;}
			for(int i=0;i<reservList.size();i++) {
				if (!(preList.contains(reservList.get(i)) 
						|| preCoursed.contains(reservList.get(i)))) { 
					return false;
				}
			}
			this.reader.close();
			return true;
		}
		return true;
	}
	public String getStudentReservation() throws NullDataException, IOException {
		this.reader = new BufferedReader(new FileReader(this.file));
		if(this.file==null) throw new NullDataException("~~~~~Student's ReservData is null~~~~~");
		ArrayList<String> reservList = new ArrayList<String>();
		String line = null;
		while((line=this.reader.readLine())!=null) {
			reservList.add(line);
		}
		return reservList.toString();
	}
	public String addReserv(String courseId) throws IOException {
		if(file.exists()) {
			String preList = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine())!=null) {preList += line;}
			if(!preList.contains(courseId)) {
				this.writer.write(courseId);
				this.writer.newLine();
				this.writer.close();
				return "성공적으로 등록하였습니다.";
			} else {
				return "이미 신청된 과목입니다.";
			}
		}return "FAIL";
	}	
	public boolean deleteFile() throws IOException {
		if(file.exists()) {
			file.delete();
			return true;
		} else {
			return false;
		}
	}
} 
