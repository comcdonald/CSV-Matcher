package Dad_Project;

import java.util.*;
import java.util.function.BiConsumer;

public class EntryList {
	private int numColumns;
	private int numRows;
	private HashMap<Integer,String> dataTypes;
	private String name;
	private boolean selectedForSort=false;
	private boolean compressed=false;
	
	public EntryList(String inputFileName) {
		this.name = inputFileName;
		setNumRows();
		setNumColumns();
		//createEntrys();
		initializeDataTypes();
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean getSortState() {
		return selectedForSort;
	}
	public void toggleSortState() {
		selectedForSort=!selectedForSort;
	}
//	private void createEntrys() {
//		int numRows = lineList.size();
//		ListofEntrys = new Entry[numRows];
//		for(int i=0;i<numRows;i++) {
//			String line = lineList.get(i);
//			Entry entry = new Entry(line);
//			ListofEntrys[i] = entry;
//		}
//	}
	
	private void initializeDataTypes() {
		dataTypes = new HashMap<>();
		for(int i=0;i<getNumColumns();i++) {
			dataTypes.put(i,"empty");
		}
	}
	
	public String[] getArrayOfDataTypes() {
		String[] array = new String[dataTypes.size()];
		for(int i =0;i<dataTypes.size();i++) {
			array[i] = dataTypes.get(i);
		}
		return array;
	}
	
	public void setDataType(int column,String type) {
		dataTypes.put(column, type);
	}
	
	public String[][] getTableofData() {
		if(compressed) {
			return compressed();
		}else {
			return uncompressed();
		}
	}
	
	private String[][] uncompressed(){
		String[] lineList = retrieveDataBase();
		String[][] tableToReturn = new String[this.getNumRows()][this.getNumColumns()];
		for(int i = 0;i<tableToReturn.length;i++) {
			String[] lineArray = splitLine(lineList[i]);
			//System.out.println(i+" length: "+ lineArray.length);
			for(int v = 0;v<this.getNumColumns();v++) {
				//System.out.print(" "+lineArray[v]+",");
				tableToReturn[i][v] = lineArray[v];
			}
			//System.out.print("\n");
		}
		lineList = null;
		return tableToReturn;
	}
	
	private String[][] compressed(){
		String[] lineList = retrieveDataBase();
		ArrayList<String[]> tableToReturn = new ArrayList<String[]>();
		Integer count = 0;
		tableToReturn.add(splitLine(lineList[0]));
		String[] lastLine = tableToReturn.getFirst();
		for(String line:lineList) {
			String[] lineArray = splitLine(line);
			if(count!=0) {
				System.out.println(count+" new line "+lineArray.length);
				int disimilarityCount =0;
				for(int i =0;i<lineArray.length;i++) {
					if(lastLine[i].equals(lineArray[i])==false) {
						disimilarityCount++;
					}
					if(disimilarityCount>4) {
						tableToReturn.add(lineArray);
						lastLine = lineArray;
						break;
					}
				}
//				if(lastLine[2].equals(lineArray[2])==false){
//					System.out.println(lastLine[2]+" != "+ lineArray[2]);
//					tableToReturn.add(lineArray);
//					lastLine = lineArray;
//				}
			}
			count++;
		}
		String[][]converted = tableToReturn.toArray(new String[tableToReturn.size()][this.getNumColumns()]);
		return converted;
	}
	
	public void toggleCompressed() {
		compressed =!compressed;
	}
	public boolean getCompressedState() {
		return compressed;
	}
	public String[] splitLine(String line) {
		String theLine = line;
		
		ArrayList<String> stuff = new ArrayList<String>();
		boolean notInsideComma = true;
		int start = 0;
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
		String[] array = stuff.toArray(new String[stuff.size()]);
		stuff = null;
		return array;
	}
	private void setNumColumns() {
		String[] lineList = retrieveDataBase();
		this.numColumns = splitLine(lineList[0]).length;
		lineList = null;
	}
	private void setNumRows() {
		String[] lineList = retrieveDataBase();
		this.numRows = lineList.length;
		lineList = null;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public String[] retrieveDataBase() {
		FileManager inputfm =  new FileManager();
		String inputDirectory = inputfm.createDirectory("InputFiles","");
		inputfm.setBaseDirectory(inputDirectory);
		
		return inputfm.getFileContentByLine(inputDirectory+name);
	}
	public String toString() {
		String[] lineList = retrieveDataBase();
		String msg = "";
		String[] headers = getArrayOfDataTypes();
			String headermsg = String.join(",", headers);
		msg = msg +headermsg;
		for(int i =0; i<lineList.length;i++) {
			String tempmsg = lineList[i];
			msg = msg +"\n"+tempmsg;
		}
		lineList = null;
		headers = null;
		return msg;
	}
	
}
