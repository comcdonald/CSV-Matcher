package Dad_Project;

import java.util.ArrayList;

public class Sorter {
	private ArrayList<EntryList> tables;
	private int biggestSize = 0;
	
	public Sorter(ArrayList<EntryList> tables) {
		this.tables = tables;
		this.biggestSize = getBiggestSize();
	}
	
	public String[][] compare() {
		ArrayList<String[]> sortedList = new ArrayList<String[]>();
		EntryList anchor = tables.getFirst();
		String[][] anchorTable = anchor.getTableofData();
		for(int i =0;i<anchorTable.length;i++) {
			String[] anchorLine = anchorTable[i];
			anchorLine[0] = "test";
			sortedList.addLast(anchorLine);
			int linesThatAreSimilar=0;
			for(int v =1;v<tables.size();v++) {
				EntryList tableToCompare = tables.get(v);
				String[] tableToCompareLines = tableToCompare.retrieveDataBase();
				for(String line: tableToCompareLines) {
					int similarityScore = 0;
					for(int u = 0;u<anchorLine.length;u++) {
						if(anchorLine[u].length()>2) {
							if(line.contains(anchorLine[u])) {
								
								similarityScore++;
							
								System.out.println(anchorLine[u].length()+" "+similarityScore);
							}
						}
					}
					if(similarityScore>1) {
						
						sortedList.add(tableToCompare.splitLine(line));
						linesThatAreSimilar++;
					}
				}
			}
			if(linesThatAreSimilar==0) {
				sortedList.removeLast();
			}
		}
		String[][] convertedList = sortedList.toArray(new String[sortedList.size()][biggestSize]);
		return convertedList;
	}
	
	private int getBiggestSize() {
		int biggest = 0;
		for(EntryList table:tables) {
			if(table.getNumColumns()>biggest) {
				biggest = table.getNumColumns();
			}
		}
		return biggest;
	}
}
