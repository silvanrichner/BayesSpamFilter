import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Reader {

	
	public static String read(String url){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(url)));
			String line;
			StringBuilder content = new StringBuilder();
			while((line = br.readLine()) != null){
				content.append(line);
				content.append(" ");
			}
			
			return content.toString();
		} catch (Exception e) {
			System.out.println("Failed to read file");
			e.printStackTrace();
		}
		return null;
	}

}
