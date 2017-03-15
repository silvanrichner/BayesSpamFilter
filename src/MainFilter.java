import java.io.File;

public class MainFilter {
	Training t;
	

	public MainFilter() {
		t = new Training(System.getProperty("user.dir") + File.separator + "res" + File.separator );
	}

	public static void main(String[] args) {
//		System.out.println(System.getProperty("user.dir"));
//		System.out.println(Reader.read(System.getProperty("user.dir") + File.separator + "res" + File.separator + "ham-train"+ File.separator + "0126.d002ec3f8a9aff31258bf03d62abdafa"));
		MainFilter mf = new MainFilter();
	}
	
	
}
