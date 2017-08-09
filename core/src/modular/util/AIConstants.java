package modular.util;

public class AIConstants {
	
	private static int ALGORITHM = 1;
	private static final  int rALGORITHM = ALGORITHM;
	
	public static void setAlgorithm(int i){
		if(i <= 1){
			ALGORITHM = 1;
		}else if(i == 2){
			ALGORITHM = 2;
		}else{
			ALGORITHM = 3;
		}
	}
	public static int getAlgorithm(){
		return ALGORITHM;
	}
	public static void rstAlgorithms(){
		ALGORITHM = rALGORITHM;
	}
	
}