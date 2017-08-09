package modular.util.fileHandler;
import java.util.*;
import com.badlogic.gdx.math.*;
import java.io.*;
import java.text.*;

/**
 * @author Group2
 * @version 1.1
 *  Basic file reader which converts the information from text files into HashMaps in other to create an initial board state, or into an ArrayList of HashMaps, used for the replay system.
 */
public class Reader {	 
	/** 
	 * This method reads a text file with modules or obstacles positions.
	 * @param inputfile This is the file which will be read.
	 * @return map This is a HashMap which maps module ID to Vector3 indicating its position.
	 * */
	public Map<Float,Vector3> positionReader(File inputfile){
		Map<Float,Vector3> map = new HashMap<Float,Vector3>();
		
		try { 
			FileReader fr = new FileReader(inputfile);
	        BufferedReader br = new BufferedReader(fr);
	        Scanner in = null;

	        String record = new String();
	        int i = 0;
	        while ((record = br.readLine()) != null){
	        	if( record.startsWith("//") ) continue;
	        	in = new Scanner(record);
	        	if( record.startsWith("X")) {
	        		String[] input = record.split(",");
		            float ID = 1000+i;
		            i++;
		            in.next();
		            float x = Float.parseFloat(input[1]);
		            float y = Float.parseFloat(input[2]);
		            float z = Float.parseFloat(input[3]);
		            map.put(ID, new Vector3(x,y,z));
		            continue;
	            } else{
	        	String[] input = record.split(",");
	        	//System.out.println(record);
	            float ID = Float.parseFloat(input[0]);
	            float x = Float.parseFloat(input[1]);
	            float y = Float.parseFloat(input[2]);
	            float z = Float.parseFloat(input[3]);
	          
	            map.put(ID, new Vector3(x,y,z));    
	            }
	        }
	        //System.out.println(map);
	        fr.close();
	        br.close();
	        in.close();
		}
		 catch (IOException ex) { 
        // catch possible io errors from readLine()
        System.out.println("Error! Problem reading file ");
        //System.exit(0);
        }
		return map;
	}
	/** 
	 * This method reads a text file with moves of modules in each time step.
	 * @param inputfile This is the file which will be read.
	 * @return arr This is an ArrayList of HashMaps, one for each time step. 
	 * which map module ID's to an array of two Vector3, indicating the start and end position of this module.
	 * */
	public ArrayList<Map<Float,Vector3[]>> movesReader(File inputfile){
		ArrayList<Map<Float,Vector3[]>> arr = new ArrayList<>();
		try 	{ 
			FileReader fr = new FileReader(inputfile);
	        BufferedReader br = new BufferedReader(fr);
	        Scanner in = null;

	        String record = new String();
	        
	        int i = 0, j = 0;
	        boolean first = true;
	        
	        while ((record = br.readLine()) != null){
	        	arr.add(new HashMap<Float,Vector3[]>());
	        	String[] inputs = record.split(",");
	        	if( record.startsWith("//") ) continue;
	        		in = new Scanner(record);
	        	i = (int) Float.parseFloat(inputs[0]);
	        	float ID = Float.parseFloat(inputs[1]);
		            
	        	float startX = Float.parseFloat(inputs[2]);
	        	float startY = Float.parseFloat(inputs[3]);
	        	float startZ = Float.parseFloat(inputs[4]);
		            
	        	float endX = Float.parseFloat(inputs[5]);
	        	float endY = Float.parseFloat(inputs[6]);
	        	float endZ = Float.parseFloat(inputs[7]);
		            
	        	Vector3[] vct = new Vector3[2];
	        	vct[0] = new Vector3(startX,startY,startZ);
	        	vct[1] = new Vector3(endX,endY,endZ);
		        
	        	if(first && i > 0){
	        		first = false;
	        		j = i;
	        	}
	        	arr.get(i-j).put(ID, vct);
	        	/*	        
	        	String string = ""+i;
	        	if( record.startsWith(string)){
	        		in.next();
		            
	        		float ID = in.nextFloat();
		            
		            float startX = in.nextFloat();
		            float startY = in.nextFloat();
		            float startZ = in.nextFloat();
		            
		            float endX = in.nextFloat();
		            float endY = in.nextFloat();
		            float endZ = in.nextFloat();
		            
		            Vector3[] vct = new Vector3[2];
		            vct[0] = new Vector3(startX,startY,startZ);
		            vct[1] = new Vector3(endX,endY,endZ);
		            
		            arr.get(i-1).put(ID, vct);
		            continue;
	            }
	        	else {
	            i++;
	        	arr.add(new HashMap<Float,Vector3[]>());
	            in.next();
	            
	            float ID = in.nextFloat();
	            
	            float startX = in.nextFloat();
	            float startY = in.nextFloat();
	            float startZ = in.nextFloat();
	            
	            float endX = in.nextFloat();
	            float endY = in.nextFloat();
	            float endZ = in.nextFloat();
	            
	            Vector3[] vct = new Vector3[2];
	            vct[0] = new Vector3(startX,startY,startZ);
	            vct[1] = new Vector3(endX,endY,endZ);
	            
	            arr.get(i-1).put(ID, vct);
	            continue; 
	        	}*/
	        }
	        //System.out.println(arr);
	        
	        fr.close();
	        br.close();
	        in.close();
	    }catch (IOException ex){ 
        // catch possible io errors from readLine()
        System.out.println("Error! Problem reading file ");
        //System.exit(0);
        }
		return arr;
	}
	/** 
	 * This method takes modules or obstacles positions and saves them in a text file which it creates.
	 * @param map This is a HashMap of ID's and Vector3's which will be saved in the text file
	 * */
	public void positionWriter(Map<Float,Vector3> map) {
	        BufferedWriter writer = null;
	        try  {
	            //create a file
	            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())+"position";
	            File saveFile = new File(timeLog);

	            // This will output the full path
	            System.out.println(saveFile.getCanonicalPath());
	            
	            writer = new BufferedWriter(new FileWriter(saveFile));
	            Set<Float> set = map.keySet();
				Iterator<Float> iterator = set.iterator();
	            while(iterator.hasNext())  {
	            	float ID = (Float) iterator.next();
	            	String save = ""+ID+" "+map.get(ID).toString();
	            	save = save.replaceAll("[{}+()=,]"," ");
		            writer.write(save);
		            writer.newLine();
	            }
	            
	        }
	        catch (Exception e)  {
	            System.out.println("Couldn't save.");	           
	        } 
	        finally  {
	            try  {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } 
	            catch (Exception e)  {
	            }
	        }
	    }
	/** 
	 * This method takes moves made by modules in each times step and saves them in a text file which it creates.
	 * @param arr This is an ArrayList of Maps, one for each time step, which map an ID to an array of two Vector3,
	 * they indicate the start and end position of the module in this time step.
	 * */
	@SuppressWarnings("rawtypes")
	public void movesWriter(ArrayList<Map<Float,Vector3[]>> arr){
	        BufferedWriter writer = null;
	        try {
	            //create a file
	            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())+"moves";
	            File saveFile = new File(timeLog);

	            // This will output the full path
	            //System.out.println(saveFile.getCanonicalPath());
	            
	            writer = new BufferedWriter(new FileWriter(saveFile));
	            int i = 0;
	            while(i < arr.size()){
		            Set set = arr.get(i).keySet();
		            Iterator iterator = set.iterator();
		            while(iterator.hasNext()){
		            	float ID = (Float) iterator.next();
		            	int z = i+1;
		            	String save = ""+z+" "+ID+" "+arr.get(i).get(ID)[0]+" "+arr.get(i).get(ID)[1];
		            	save = save.replaceAll("[{}+()=,]"," ");
			            writer.write(save);
			            writer.newLine();
		            }
		            i++;
		            continue;
	            }
	            
	        }
	        catch (Exception e) {
	            System.out.println("Couldn't save.");	           
	        } 
	        finally  {
	            try  {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } 
	            catch (Exception e)  {
	            }
	        }
	    }
	
	// main tests all the methods inside the class when run
		public static void main(String[] args)
		{
			Reader reader = new Reader();
			
			Map<Float,Vector3> test1 = new HashMap<Float,Vector3>();
			ArrayList<Map<Float,Vector3[]>> test2 = new ArrayList<Map<Float,Vector3[]>>();
			
			File file1 = new File("/Users/Albert/Documents/Self-Reconfigurable-Modular-Robotics/core/src/modular/filereader/start.txt");
			File file2 = new File("/Users/Albert/Documents/Self-Reconfigurable-Modular-Robotics/core/src/modular/filereader/obstacles.txt");
			File file3 = new File("/Users/Albert/Documents/Self-Reconfigurable-Modular-Robotics/core/src/modular/filereader/end.txt");
			
			test1 = reader.positionReader(file1);
			reader.positionReader(file2);
			reader.positionReader(file3);
			
			File file4 = new File("/Users/Albert/Documents/Self-Reconfigurable-Modular-Robotics/core/src/modular/filereader/moves.txt");
			
			test2 = reader.movesReader(file4); 
			
			reader.positionWriter(test1);
			reader.movesWriter(test2);
		}
}
