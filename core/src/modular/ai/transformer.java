package modular.ai;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

public class transformer {

	
	/*public static Configuration assemble (Configuration start, Configuration target) {
		int n = start.unitno;
		int[] closest = findclosest(start, target);
		updateManhattan(start, target.units.get(closest[1]).anchor);
		updateAdj(start);
		ArrayList<Unit> order = new ArrayList<>();
		int bottomrow;
		if (start.units.size() % 2 == 0){
			bottomrow = start.units.size() / 2;
		} else {
			bottomrow = (start.units.size() / 2)+1;
		}
		int adj = 1;
		while (start.units.size() > 0) {
			int distance = -1;
			int next = -1;
			for (int i = 0; i < start.units.size(); i++) {
				if (start.units.get(i).manhattan > distance) {
					next = i;
				}
			}
			if (next != -1) {
				order.add(start.units.get(next));
				start.units.remove(next);
				updateAdj(start);
			} else {
				adj++;
			}
		}
		start.units.clear();
		for(int i = 0; i < order.size(); i++) {
			start.units.add(order.get(i));
			order.remove(i);
		}
	}

	public static Configuration assemble (Configuration start) {

		
	//should be in a correctly ordered ArrayList now; everything I did after this point didn't work.
		
				
	}*/
		
		
		
	
	
	//public static Configuration disassemble (Configuration current, Configuration target) {
		
	//}
	
	//public static Configuration crossridge (Configuration current, Configuration ridge) {
		
	//}
	
	//public static Configuration crossgap (Configuration current, Configuration gap) {
		
	//}
	
	
	
	
	
	public static void updateManhattan(Configuration current, Vector3 target) {
		for (int i = 0; i < current.units.size(); i++) {
			current.units.get(i).manhattan = Manhattan (current.units.get(i).anchor, target);
		}
	}
	
	public static void updateAdj (Configuration current) {
		for(int i = 0; i < current.units.size(); i++) {
			Vector3 temp1 = current.units.get(i).anchor;
			current.units.get(i).adj = 0;
			for(int k = 0; k < current.units.size(); k++) {
				Vector3 temp2 = current.units.get(k).anchor;
				int temp = 0;
				if(Math.abs(temp1.x - temp2.x) >1.001){
					break;
				} else if(Math.abs(temp1.x - temp2.x) <0.001){
					temp=+1;
				}
				if(Math.abs(temp1.y - temp2.y) >1.001){
					break;
				} else if(Math.abs(temp1.y - temp2.y) <0.001){
					temp=+1;
				}
				if (temp==1){
					current.units.get(i).adj++;
				}
			}
		}
	}

	public static float Manhattan(Vector3 a, Vector3 b) {
        return (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z));
    }

	public static int[] findclosest (Configuration current, Configuration target) {
        float temp = 0;
        float min = 1000;                                                      
        int[] answer = new int[2];                           
        for(int i = 0; i < current.units.size(); i++) {
            for(int j = 0; j < target.units.size(); j++) {
                temp = Manhattan(current.units.get(i).anchor, target.units.get(j).anchor);
                if(temp < min) {
                    min = temp;
                    answer[0] = i;
                    answer[1] = j;
                }
            }
        }
        return answer;
    }

    public static int findfarthest(ArrayList<Unit> units, Vector3 target) {
        float temp = 0;
        float max = 0;    
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
	
	public static void main (String[] args) {
		ArrayList<Unit>start = new ArrayList<>();
		ArrayList<Unit>target = new ArrayList<>();
		ArrayList<Obstacle>obs = new ArrayList<>();
		start.add(new Unit(0, 2f, 0f, 0f));
		start.add(new Unit(1, 3f, 0f, 0f));
		start.add(new Unit(2, 4f, 0f, 0f));
		start.add(new Unit(3, 5f, 0f, 0f));
		start.add(new Unit(4, 6f, 0f, 0f));
		start.add(new Unit(5, 7f, 0f, 0f));
		obs.add(new Obstacle(0, 6f, 1f, 0f));
		obs.add(new Obstacle(1, 7f, 1f, 0f));
		obs.add(new Obstacle(2, 6f, 2f, 0f));
		obs.add(new Obstacle(3, 7f, 2f, 0f));
		target.add(new Unit(0, 10f, 2f, 0f));
		target.add(new Unit(1, 10f, 3f, 0f));
		target.add(new Unit(2, 10f, 4f, 0f));
		target.add(new Unit(3, 10f, 5f, 0f));
		target.add(new Unit(4, 10f, 6f, 0f));
		target.add(new Unit(5, 10f, 7f, 0f));
		Configuration alpha = new Configuration (14, 14, 14, start, obs);
		Configuration omega = new Configuration (14, 14, 14, target, obs);
	}

	
	
}
