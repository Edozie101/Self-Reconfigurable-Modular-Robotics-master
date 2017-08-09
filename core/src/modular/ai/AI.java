package modular.ai;

import java.io.IOException;

public class AI {
	
	public aiModule ai = null;
	public AI(int algorithm) throws IOException {
		if(algorithm == 1){
			ai = new Pathfinder();
		} //else if(){
			
		//}
		else {
			throw new IOException("AI DOES NOT EXIST");
		}
		// TODO Auto-generated constructor stub
	}
	
	public aiModule getAI(){
		return ai;
	}

}
