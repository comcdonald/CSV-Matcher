package Dad_Project;

import java.util.*;

public class Entry {
	
	public final ArrayList<String> data;
	private String theLine;
	
	public Entry(String line) {
		theLine = line;
		data = split();
		
	}
	
	/**
	 * splits the line of a csv file into individual elements
	 * also sets the default hashmap as empty
	 * @return
	 */
	private ArrayList<String> split(){
		ArrayList<String> stuff = new ArrayList<String>();
		boolean notInsideComma = true;
		int start = 0, end = 0;
		for (int i = 0;i<theLine.length();i++) {
			
			if(theLine.charAt(i)==',' && notInsideComma) {
				String content = theLine.substring(start, i);
				stuff.add(content);
				start = i+1;
			}
			else if(theLine.charAt(i)=='"')
				notInsideComma=!notInsideComma;
		}
		stuff.add(theLine.substring(start));//to include the final thing
		
		return stuff;
	}
	
	
	
	

	
	
}
