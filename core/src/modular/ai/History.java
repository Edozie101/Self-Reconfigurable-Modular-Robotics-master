package modular.ai;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;

import modular.ai.Pathfinder.Unit;

public class History {
	public static Array<Float> history = new Array<>();
	
	public static void addAll(float timestep, Unit unit){
		history.add(timestep);
        history.add(unit.id);

        history.add(unit.prev.x);
        history.add(unit.prev.z);
        history.add(unit.prev.y);	
        history.add(unit.anchor.x);
        history.add(unit.anchor.z);
        history.add(unit.anchor.y);
	}
	
	public static void add() throws IOException{
		throw new IOException("Not supported.");
	}
	
	public static String showHistory(){
		String string = "";
		for(int i = 0; i < history.size;){
			string+=history.get(i++) + "," + history.get(i++) + "," + history.get(i++) + "," + history.get(i++) + "," + history.get(i++) + "," + history.get(i++) + "," + history.get(i++) + "," + history.get(i++) + "\n";
		}
		return string;
	}
}
