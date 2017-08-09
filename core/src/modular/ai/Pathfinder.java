package modular.ai;
import java.util.*;

import com.badlogic.gdx.math.Vector3;

/**
*
* @author Group2
* @version 1.3.1
* Caterpillar A*.
*/
public class Pathfinder implements aiModule {
    
    
     class Unit {
        Float id;
        int active;                                                             // 0 for inactive, 1 for active
        Vector3 anchor;
        Vector3 next;
        ArrayList<Vector3> protocol;
		Vector3 prev;
		Vector3 prevprev;
        Unit(float id, int a, int x, int y, int z) {
            this.id = id;
            this.active = a;
            this.anchor = new Vector3(x, y, z);
            this.prev = new Vector3(x,y,z);
            this.prevprev = new Vector3(x,y,z);
            this.protocol = new ArrayList<>();
            protocol.add(anchor);
        }
    }   
    
     class Cell {
        int type = 0;                                                           // 0 for free, 1 for occupied, 2 for obstacle
        int gcost = 0;
        int hcost = 0;
        int fcost = 0;
        Vector3 anchor;
        Cell parent;
        Cell(int x, int y, int z) {
            anchor = new Vector3(x, y, z);
            this.anchor.x = x;
            this.anchor.y = y;
            this.anchor.z = z;
        }
        @Override
        public String toString() {
            return "["+this.anchor.x+", "+this.anchor.y+", "+this.anchor.z+"]";
        }
        
        public boolean equals(Cell o) {
			return type == o.type && gcost == o.gcost && hcost == o.hcost && fcost == o.fcost && anchor.epsilonEquals(o.anchor, 0.1f) && parent.equals(o.parent);
        }
    }
    
     Cell[][][] grid;
     PriorityQueue<Cell> open;
     boolean[][][] closed;
     int unitcounter = 0;
     ArrayList<Unit> units;
     ArrayList<Vector3> start;
     ArrayList<Vector3> targets;
     ArrayList<Vector3> path;
     int[][] Points;
     Vector3 top;
     Vector3 bottom;
     int xdimension;
     int ydimension;
     int zdimension;
    
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#setType(modular.ai.Pathfinder.Cell, int)
	 */
    @Override
	public  void setType(Cell a, int type){
        a.type = type;
    }
    
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#initialize(int, int, int, java.util.ArrayList)
	 */
    @Override
	public void initialize(int size, ArrayList<Vector3> blocked){
        xdimension = size;
        ydimension = size;
        zdimension = size;
        grid = new Cell[xdimension][ydimension][zdimension];
        closed = new boolean[xdimension][ydimension][zdimension];
        for(int i = 0; i < xdimension; i++) {
            for(int j = 0; j < ydimension; j++) {
                for(int k = 0; k < zdimension; k++) {
                    grid[i][j][k] = new Cell(i, j, k);
                }
            } 
        }
        start = new ArrayList<>();
        targets = new ArrayList<>();
        units = new ArrayList<>();
        path = new ArrayList<>();
        for(int i=0; i<blocked.size(); i++) {
            grid[(int) blocked.get(i).x][(int) blocked.get(i).y][(int) blocked.get(i).z].type = 2;
        }
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;

            return c1.fcost<c2.fcost?-1:c1.fcost>c2.fcost?1:0;
            });
    }
    
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#refresh()
	 */
    @Override
	public  void refresh() {
        for(int i = 0; i<xdimension; i++) {
            for(int j = 0; j<ydimension; j++) {
                for(int k = 0; k<zdimension; k++) {
                    grid[i][j][k].hcost = 0;
                    grid[i][j][k].fcost = 0;
                    grid[i][j][k].gcost = 0;
                    grid[i][j][k].parent = null;
                    closed[i][j][k] = false;
                }
            } 
        }
        open.clear();
    }
    
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#addUnit(int, int, int, int, int, int)
	 */
    @Override
	public  void addUnit(int a, int b, int c, int x, int y, int z) {
        start.add(new Vector3(a, b, c));
        targets.add(new Vector3(x, y, z));
        units.add(new Unit(unitcounter, 1, a, b, c));
        unitcounter++;
    }
    
     void checkAndUpdateCost(Cell current, Cell t, int cost) {
        if(t.type == 2 || closed[(int) t.anchor.x][(int) t.anchor.y][(int) t.anchor.z])return;
        int t_final_cost = t.hcost+cost;
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.fcost) {
            t.fcost = t_final_cost;
            t.parent = current;
            //System.out.println(t.fcost + " " + t.parent);
            if(!inOpen)open.add(t);
        }
    }
    
    class LogicResult{
    	ArrayList<Vector3> result;
    	int costResult;
    }
    
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#validMove(modular.ai.Pathfinder.Cell)
	 */
    @Override
	public LogicResult validMove(Cell a){
    	LogicResult temp = new LogicResult();
    	temp.result = new ArrayList<>();
    	temp.result.add(new Vector3());
    	temp.result.add(new Vector3());
    	temp.result.add(new Vector3());
    	temp.result.add(new Vector3());
    	
    	temp.costResult = 0;
    	if(a.anchor.y-1>=0){
            if((a.anchor.z == 0)&&(grid[(int) a.anchor.x][(int) (a.anchor.y-1)][(int) a.anchor.z].type != 2)){
                temp.result.set(3,new Vector3(0,-1.0f,0));
                temp.costResult++;
            } else if((a.anchor.z>0)&&(grid[(int) a.anchor.x][(int) (a.anchor.y-1)][(int) a.anchor.z].type != 2)&&(grid[(int) a.anchor.x][(int) (a.anchor.y-1)][(int) (a.anchor.z-1)].type == 2)){
            	temp.result.set(3,new Vector3(0,-1.0f,0));
            	temp.costResult++;
            }
        }
    	if(a.anchor.y+1<grid[0].length){
            if((a.anchor.z == 0)&&(grid[(int) a.anchor.x][(int) (a.anchor.y+1)][(int) a.anchor.z].type != 2)){
            	temp.result.set(2,new Vector3(0,1.0f,0));
            	temp.costResult++;
            } else if((a.anchor.z>0)&&(grid[(int) a.anchor.x][(int) (a.anchor.y+1)][(int) a.anchor.z].type != 2)&&(grid[(int) a.anchor.x][(int) (a.anchor.y+1)][(int) (a.anchor.z-1)].type == 2)){
            	temp.result.set(2,new Vector3(0,1.0f,0));
            	temp.costResult++;
            }
        }
    	if(a.anchor.x+1<grid.length){
            if((a.anchor.z == 0)&&(grid[(int) (a.anchor.x+1)][(int) a.anchor.y][(int) a.anchor.z].type != 2)){
            	temp.result.set(0,new Vector3(1.0f,0,0));
            	temp.costResult++;
            } else if((a.anchor.z>0)&&(grid[(int) (a.anchor.x+1)][(int) a.anchor.y][(int) a.anchor.z].type != 2)&&(grid[(int) (a.anchor.x+1)][(int) a.anchor.y][(int) (a.anchor.z-1)].type == 2)){
            	temp.result.set(0,new Vector3(1.0f,0,0));
            	temp.costResult++;
            }
        } 
    	if(a.anchor.x-1>=0){
            if((a.anchor.z == 0)&&(grid[(int) (a.anchor.x-1)][(int) a.anchor.y][(int) a.anchor.z].type != 2)){
            	temp.result.set(1,new Vector3(-1.0f,0,0));
            	temp.costResult++;
            } else if((a.anchor.z>0)&&(grid[(int) (a.anchor.x-1)][(int) a.anchor.y][(int) a.anchor.z].type != 2)&&(grid[(int) (a.anchor.x-1)][(int) a.anchor.y][(int) (a.anchor.z-1)].type == 2)){
            	temp.result.set(1,new Vector3(-1.0f,0,0));
            	temp.costResult++;
            }
        }
    	return temp;
    }
   
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#finished(int[][])
	 */
    @Override
	public  boolean finished(int[][] Points) {
        int sum = 0;
        int min = 1000;                                                       //Arbitrary limit here - make this more elegant
        for(int i = 0; i < Points[0].length; i++) {
            for(int j = 0; j < Points[1].length; j++) {
                if(Points[i][j] < min) {
                    min = Points[i][j];
                }
            }
            sum =+ min;
            min = 1000;
        }
        return(sum == 0);
    }
    
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#step(int)
	 */
    @Override
	public  void step(int current) {    	
        if((DistanceMeasurements.Manhattan(units.get(current).anchor, units.get(current).next)) > 2) {
            if(units.get(current).anchor.z == 0) {
                int neighbor = 0;
                for(int i = 0; i < units.size(); i++) {
                    if(DistanceMeasurements.Manhattan(units.get(current).anchor, units.get(i).anchor) == 1) {
                        neighbor = i;
                    }
                }

                units.get(current).prevprev.set(units.get(current).prev);
                units.get(current).prev.set(units.get(current).anchor);
                units.get(current).anchor.set(units.get(neighbor).anchor.x,units.get(neighbor).anchor.y,units.get(neighbor).anchor.z + 1);
            } else {
                int neighbor = 0;
                for(int i = 0; i < units.size(); i++) {
                    if((current != i) && (DistanceMeasurements.Manhattan(units.get(current).anchor, units.get(current).next)) - (DistanceMeasurements.Manhattan(units.get(i).anchor, units.get(current).next)) == 2) {
                        neighbor = i;
                    }
                }
                units.get(current).prevprev.set(units.get(current).prev);
                units.get(current).prev.set(units.get(current).anchor);
                units.get(current).anchor.set(units.get(neighbor).anchor.x,units.get(neighbor).anchor.y,units.get(neighbor).anchor.z + 1);
            }
        } else {
        	units.get(current).prev.set(units.get(current).anchor);
            units.get(current).anchor = units.get(current).next;
            
        }
    }
    
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#Execute()
	 */
    @Override
	public void Execute(){
    	Planner();
    	Mover();
    }
    	
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#Planner()
	 */
    @Override
	public  void Planner() {
        ArrayList<Integer> route = DistanceMeasurements.findclosest(units, targets);
        top = units.get(route.get(0)).anchor;
        bottom = targets.get(route.get(1));
        
        refresh();
        
        for(int i=0;i<xdimension;i++){
            for(int j=0;j<ydimension;j++){
                for(int k=0;k<zdimension;k++){
                    grid[i][j][k].hcost = (int) (Math.abs(i-bottom.x)+Math.abs(j-bottom.y)+Math.abs(k-bottom.z));
                }
            }
        }
        
        grid[(int) bottom.x][(int) bottom.y][(int) bottom.z].fcost = 0;
        open.add(grid[(int) top.x][(int) top.y][(int) top.z]);
        Cell current;
        while(true){
            //System.out.println("Pathfinding...");
            current = open.poll();
            if(current == null)break;
            if(current.type == 2)break;
            closed[(int) current.anchor.x][(int) current.anchor.y][(int) current.anchor.z]=true;
            //if(current.equals(grid[a.target.x][a.target.y][a.target.z])){
            //    return; 
            //}
            
            LogicResult tr = validMove(current);
            for(int i = 1; i <= tr.costResult; i++){
            	Vector3 tVec = tr.result.get(i-1);
            	Cell t = grid[(int) (current.anchor.x + tVec.x)][(int) (current.anchor.y + tVec.y)][(int) current.anchor.z];
            	checkAndUpdateCost(current, t, current.fcost+1);
            }
        }
        if(closed[(int) bottom.x][(int) bottom.y][(int) bottom.z]){
            //valid path found, write it to the units Path variable to remember it for later
            //System.out.println("Valid path found!");
            Cell temp;
            temp = grid[(int) bottom.x][(int) bottom.y][(int) bottom.z];
            //System.out.print(temp);
            path.add(new Vector3(temp.anchor.x, temp.anchor.y, temp.anchor.z));
            while(temp.parent != null){
                //System.out.print(" -> "+temp.parent);
                path.add(new Vector3(temp.parent.anchor.x, temp.parent.anchor.y, temp.parent.anchor.z));
                temp = temp.parent;
            }   
        }//else //System.out.println("No possible path");  
    }
    
    /* (non-Javadoc)
	 * @see modular.ai.aiInterface#Mover()
	 */
    @Override
	public  void Mover(){
        float timestep = 0;
        //int moved = 0;
        int current = 0;
        boolean busy = false;
        boolean reached = false;
        Vector3 next;
        int[][] Points = new int[units.size()][targets.size()];
        for(int i = 0; i < units.size(); i++) {
            for(int j = 0; j < targets.size(); j++) {
                Points[i][j] = DistanceMeasurements.Manhattan(units.get(i).anchor, targets.get(j));
            }
        }
        
        for(int i = 0; i < unitcounter; i++){
            setType(grid[(int) units.get(i).anchor.x][(int) units.get(i).anchor.y][(int) units.get(i).anchor.z], 1);
            History.addAll(timestep, units.get(i));
        }
        //System.out.println();
        for(int i = 0; i < units.size(); i++) {
            units.get(i).next = units.get(i).anchor;
        }
        path.remove(path.size()-1);
        while(!finished(Points)) {
            if(reached) {
                ArrayList<Integer> route = DistanceMeasurements.findclosest(units, targets);
                targets.remove(bottom);
                bottom = targets.get(route.get(1));
                path.add(0, bottom);
                reached = false;
            }
            timestep++;
            
            //System.out.println("timestep " +timestep+ ":");
            busy = false;
            for(int i = 0; i < units.size(); i++) {
                if(!units.get(i).anchor.equals(units.get(i).next)) {
                    busy = true;
                }
            }
            if(busy) {
            	for(int i = 0; i < units.size(); i++){
                    units.get(i).prev.set(units.get(i).anchor);
                }
                step(current);
                
                for(int i = 0; i < units.size(); i++) {
                	History.addAll(timestep, units.get(i));
                }
                //System.out.println("Unit " +current+ " moved to " +units.get(current).anchor.x+ ", " +units.get(current).anchor.y+ ", " +units.get(current).anchor.z+ ".");
                for(int i = 0; i < targets.size(); i++) {
                    Points[current][i] = DistanceMeasurements.Manhattan(units.get(current).anchor, targets.get(i));
                    if(Points[current][i] == 0) {
                        reached = true;
                    }
                }  
            } else {
                next = path.remove(path.size()-1);
                current = DistanceMeasurements.findfarthest(units, bottom);
                units.get(current).next = next;
                for(int i = 0; i < units.size(); i++){
                    units.get(i).prev.set(units.get(i).anchor);
                }
                step(current);
                
                for(int i = 0; i < units.size(); i++) {
                	History.addAll(timestep, units.get(i));
                }
                //System.out.println("Unit " +current+ " moved to " +units.get(current).anchor.x+ ", " +units.get(current).anchor.y+ ", " +units.get(current).anchor.z+ ".");
                for(int i = 0; i < targets.size(); i++) {
                    Points[current][i] = DistanceMeasurements.Manhattan(units.get(current).anchor, targets.get(i));
                    if(Points[current][i] == 0) {
                        reached = true;
                        //targets.remove(i);
                    }
                }
            }
        }
        //System.out.println("All units are on their targets!");
    }
    
    public static void main(String[] args) {
    	aiModule ai = new Pathfinder();
        ArrayList<Vector3> blocked = new ArrayList<>();
        blocked.add(new Vector3(3,0,0));
        blocked.add(new Vector3(3,0,1));
        ai.initialize(12, blocked);
        ai.addUnit(0,0,0,9,9,0);
        ai.addUnit(0,1,0,10,9,0);
        ai.addUnit(0,2,0,11,9,0);
        ai.Execute();
        //System.out.println();
        //System.out.print(ai.history);
        /*for(int i = 0; i < ai.history.size();){
        	System.out.println(ai.history.get(i++) + "," + ai.history.get(i++) + "," + ai.history.get(i++) + "," + ai.history.get(i++) + "," + ai.history.get(i++) + "," + ai.history.get(i++) + "," + ai.history.get(i++) + "," + ai.history.get(i++));
        }*/
        System.out.println(History.showHistory());
    }
    
}
