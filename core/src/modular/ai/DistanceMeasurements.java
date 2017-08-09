package modular.ai;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

import modular.ai.Pathfinder.Unit;

public class DistanceMeasurements {

	public static  int Manhattan(Vector3 a, Vector3 b) {
        return (int) (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z));
    }

	public static  ArrayList<Integer> findclosest(ArrayList<Unit> units, ArrayList<Vector3> targets) {
        int temp = 0;
        int min = 1000;                                                       //Arbitrary limit here - make this more elegant
        ArrayList<Integer> answer = new ArrayList<>();                            
        for(int i = 0; i < units.size(); i++) {
            for(int j = 0; j < targets.size(); j++) {
                temp = Manhattan(units.get(i).anchor, targets.get(j));
                if(temp < min) {
                    min = temp;
                    answer.clear();
                    answer.add(i);
                    answer.add(j);
                }
            }
        }
        return answer;
    }

    public static int findfarthest(ArrayList<Unit> units, Vector3 target) {
        int temp = 0;
        int max = 0;    
        int answer = 0;                           
        for(int i = 0; i < units.size(); i++) {
            temp = Manhattan(units.get(i).anchor, target);
            if(temp > max) {
                max = temp;
                answer = i;
            }
        }
        return answer;
    }
}
