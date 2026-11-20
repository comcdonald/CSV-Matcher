package Dad_Project;
import java.util.ArrayList;
public class DataTable extends Table<String,ArrayList<String>>{
	
	private static final long serialVersionUID = 1L;
	
	public DataTable() {
		super();
	}
	
	DataTable(Integer row, Integer column) {
		super(row,column);
	}
	
}
