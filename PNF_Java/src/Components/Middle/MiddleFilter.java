/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Components.Middle;

import java.io.IOException;
import java.util.Arrays;

import Framework.CommonFilterImpl;
import Utility.StudentCheck;

public class MiddleFilter extends CommonFilterImpl{
	String data;
	int flag;
	
	public MiddleFilter(String data, int flag) {
		this.data = data;
		this.flag = flag;
	}
	
    @Override
    public boolean specificComputationForFilter() throws IOException {
    	int checkBlank = 4; 
        int numOfBlank = 0;
        int idx = 0;
        byte[] buffer = new byte[64];
        boolean isMajor = false;    
        int byte_read = 0;
        StudentCheck studentCheck = new StudentCheck();
        
        while(true) {          
            while(byte_read != '\n' && byte_read != -1) { 
            	byte_read = in.read();
                if(byte_read == ' ') numOfBlank++;
                if(byte_read != -1) buffer[idx++] = (byte)byte_read;
                if(data.length()<3) {
                	if(numOfBlank==checkBlank && studentCheck.majorCheck(buffer, idx, data))
                        isMajor = true; 
                	
                }else {
                	if(numOfBlank==0 && studentCheck.yearCheck(buffer, idx, data))
                		isMajor = true;
                }

            }
            if(this.flag==1) {
                if(isMajor == true) {
                    for(int i = 0; i<idx; i++) 
                        out.write((char)buffer[i]); 
                    isMajor = false;
                }
            } else {
                if(isMajor != true) {
                    for(int i = 0; i<idx; i++) 
                        out.write((char)buffer[i]); 

                }
                isMajor = false;
            }

            if (byte_read == -1) return true;
            idx = 0;
            numOfBlank = 0;
            byte_read = '\0';
            Arrays.fill(buffer, (byte)0);
        }
    }  
}
