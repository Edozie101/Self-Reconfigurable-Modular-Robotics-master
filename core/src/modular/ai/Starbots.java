package modular.ai;

import java.util.ArrayList;
import java.util.PriorityQueue;

import modular.util.Constants;

/**
 *
 * @author Group2
 * @version 1.1
 * Multi-agent A* search based on the algorithm described in https://www.cs.bgu.ac.il/~raznis/mafs.pdf
 */
public class Starbots {
    public  class Unit{
        int id;
        Point start;
        Point target;
        ArrayList<Point> protocol;
        public ArrayList<Point> path;
        
        Unit(int id, int x, int y, int z){
            this.id = id;
            start = new Point(x, y, z);
            protocol = new ArrayList<>();
            path = new ArrayList<>();
            protocol.add(start);
        }
    }
    public static class Point{
        int x;
        int y;
        int z;
        
        public Point(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString(){
            return "["+this.x+", "+this.y+", "+this.z+"]";
        }
    }
     class Cell{
        int type = 0; //0 for free, 1 for occupied, 2 for obstacle
        int gcost = 0;
        int hcost = 0;
        int fcost = 0;
        Point location;
        Cell parent;
        
        Cell(int x, int y, int z){
            location = new Point(x, y, z);
            this.location.x = x;
            this.location.y = y;
            this.location.z = z;
        }
        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString(){
            return "["+this.location.x+", "+this.location.y+", "+this.location.z+"]";
        }
    }
     Cell[][][] grid;
     PriorityQueue<Cell> open;
     boolean closed[][][];
     int unitcounter = 0;
    public  ArrayList<Unit> units;
     ArrayList<Point> start;
     ArrayList<Point> targets;
    public  ArrayList<Integer> history;
     int xdimension;
     int ydimension;
     int zdimension;
    public  void setBlocked(Cell a){
        a.type = 2;
    }
    public  void setOccupied(Cell a){
        a.type = 1;
    }
    public  void setFree(Cell a){
        a.type = 0;
    }
    public  void initialize(ArrayList<Point> blocked){
        xdimension = (int) Constants.SIZE;
        ydimension = (int) Constants.SIZE;
        zdimension = (int) Constants.SIZE;
        grid = new Cell[(int) Constants.SIZE][(int) Constants.SIZE][(int) Constants.SIZE];
        closed = new boolean[(int) Constants.SIZE][(int) Constants.SIZE][(int) Constants.SIZE];
        for(int i = 0; i<(int) Constants.SIZE; i++){
            for(int j = 0; j<(int) Constants.SIZE; j++){
                for(int k = 0; k<(int) Constants.SIZE; k++){
                    grid[i][j][k] = new Cell(i, j, k);
                }
            }
        }
        history = new ArrayList<>();
        start = new ArrayList<>();
        targets = new ArrayList<>();
        units = new ArrayList<>();
        if(blocked == null)
        	blocked = new ArrayList<>();
	    for(Point p: blocked){
	       	grid[p.x][p.y][p.z].type = 2;
		}
        //priority queue based on heuristic cost
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;

            return c1.fcost<c2.fcost?-1:c1.fcost>c2.fcost?1:0;
            });
    }
    public  void refresh(){
        for(int i = 0; i<xdimension; i++){
            for(int j = 0; j<ydimension; j++){
                for(int k = 0; k<zdimension; k++){
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
    public  void addUnit(int a, int b, int c, int x, int y, int z){
        start.add(new Point(a, b, c));
        targets.add(new Point(x, y, z));
        units.add(new Unit(unitcounter, a, b, c));
        unitcounter++;
    }
     void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t.type == 2 || closed[t.location.x][t.location.y][t.location.z])return;
        int t_final_cost = t.hcost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.fcost){
            t.fcost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
    public  boolean frontValid(Cell a){
        if(a.location.x+1<grid.length){
            if((a.location.z == 0)&&(grid[a.location.x+1][a.location.y][a.location.z].type != 2)){
                return true;
            } else if((a.location.z>0)&&(grid[a.location.x+1][a.location.y][a.location.z].type != 2)&&(grid[a.location.x+1][a.location.y][a.location.z-1].type == 2)){
                return true;
            }
        } 
        return false;
    }
    public  boolean backValid(Cell a){
        if(a.location.x-1>=0){
            if((a.location.z == 0)&&(grid[a.location.x-1][a.location.y][a.location.z].type != 2)){
                return true;
            } else if((a.location.z>0)&&(grid[a.location.x-1][a.location.y][a.location.z].type != 2)&&(grid[a.location.x-1][a.location.y][a.location.z-1].type == 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean rightValid(Cell a){
        if(a.location.y+1<grid[0].length){
            if((a.location.z == 0)&&(grid[a.location.x][a.location.y+1][a.location.z].type != 2)){
                return true;
            } else if((a.location.z>0)&&(grid[a.location.x][a.location.y+1][a.location.z].type != 2)&&(grid[a.location.x][a.location.y+1][a.location.z-1].type == 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean leftValid(Cell a){
        if(a.location.y-1>=0){
            if((a.location.z == 0)&&(grid[a.location.x][a.location.y-1][a.location.z].type != 2)){
                return true;
            } else if((a.location.z>0)&&(grid[a.location.x][a.location.y-1][a.location.z].type != 2)&&(grid[a.location.x][a.location.y-1][a.location.z-1].type == 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean frontUpValid(Cell a){
        if(a.location.x+1<grid.length){
            if((grid[a.location.x+1][a.location.y][a.location.z].type == 2)&&(grid[a.location.x+1][a.location.y][a.location.z+1].type != 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean backUpValid(Cell a){
        if(a.location.x-1>=0){
            if((grid[a.location.x-1][a.location.y][a.location.z].type == 2)&&(grid[a.location.x-1][a.location.y][a.location.z+1].type != 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean rightUpValid(Cell a){
        if(a.location.y+1<grid[0].length){
            if((grid[a.location.x][a.location.y+1][a.location.z].type == 2)&&(grid[a.location.x][a.location.y+1][a.location.z+1].type != 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean leftUpValid(Cell a){
        if(a.location.y-1>=0){
            if((grid[a.location.x][a.location.y-1][a.location.z].type == 2)&&(grid[a.location.x][a.location.y-1][a.location.z+1].type != 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean frontDownValid(Cell a){
        if(a.location.x+1<grid.length){
            if((a.location.z>=1)&&(grid[a.location.x+1][a.location.y][a.location.z-1].type != 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean backDownValid(Cell a){
        if(a.location.x-1>=0){
            if((a.location.z>=1)&&(grid[a.location.x-1][a.location.y][a.location.z-1].type != 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean rightDownValid(Cell a){
        if(a.location.y+1<grid[0].length){
            if((a.location.z>=1)&&(grid[a.location.x][a.location.y+1][a.location.z-1].type != 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean leftDownValid(Cell a){
        if(a.location.y-1>=0){
            if((a.location.z>=1)&&(grid[a.location.x][a.location.y-1][a.location.z-1].type != 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean UpValid(Cell a){
        if(a.location.x+1<grid.length){
            if((grid[a.location.x+1][a.location.y][a.location.z].type == 2)&&(grid[a.location.x+1][a.location.y][a.location.z+1].type == 2)){
                return true;
            }
        }
        if(a.location.x-1>=0){
            if((grid[a.location.x-1][a.location.y][a.location.z].type == 2)&&(grid[a.location.x-1][a.location.y][a.location.z+1].type == 2)){
                return true;
            }
        }
        if(a.location.y+1<grid[0].length){
            if((grid[a.location.x][a.location.y+1][a.location.z].type == 2)&&(grid[a.location.x][a.location.y+1][a.location.z+1].type == 2)){
                return true;
            }
        }
        if(a.location.y-1>=0){
            if((grid[a.location.x][a.location.y-1][a.location.z].type == 2)&&(grid[a.location.x][a.location.y-1][a.location.z+1].type == 2)){
                return true;
            }
        }
        return false;
    }
    public  boolean DownValid(Cell a){
        if(a.location.z>=1){
            if(grid[a.location.x][a.location.y][a.location.z-1].type != 2){
                return true;
            }
        }
        return false;
    }
    public  int Manhattan (Point a, Point b){
        return ((Math.abs(a.x-b.x))+(Math.abs(a.y-b.y))+(Math.abs(a.z-b.z)));
    }
    public  void Pathfinder(Unit a){
        a.target = targets.get(0);
        int n = 0;
        for (int i=0; i<targets.size(); i++){
            if(Manhattan(a.start, targets.get(i)) < Manhattan(a.start, a.target)){
                a.target = targets.get(i);
                n = i;
            }
        }
        targets.remove(n);
        refresh();
        //System.out.println("Target for unitID " +a.id+ " is (" +a.target.x+ ", " +a.target.y+ ", " +a.target.z+ ").");
        for(int i=0;i<xdimension;i++){
            for(int j=0;j<ydimension;j++){
                for(int k=0;k<zdimension;k++){
                    grid[i][j][k].hcost = Math.abs(i-a.target.x)+Math.abs(j-a.target.y)+Math.abs(k-a.target.z);
                }
            }
        }
        grid[a.start.x][a.start.y][a.start.z].fcost = 0;
        open.add(grid[a.start.x][a.start.y][a.start.z]);
        Cell current;
        while(true){
            //System.out.println("Pathfinding...");
            current = open.poll();
            if(current == null)break;
            if(current.type == 2)break;
            closed[current.location.x][current.location.y][current.location.z]=true;
            //if(current.equals(grid[a.target.x][a.target.y][a.target.z])){
            //    return; 
            //}
            Cell t;
            if(frontValid(current)){
                t = grid[current.location.x+1][current.location.y][current.location.z];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(backValid(current)){
                t = grid[current.location.x-1][current.location.y][current.location.z];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(rightValid(current)){
                t = grid[current.location.x][current.location.y+1][current.location.z];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(leftValid(current)){
                t = grid[current.location.x][current.location.y-1][current.location.z];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(frontUpValid(current)){
                t = grid[current.location.x+1][current.location.y][current.location.z+1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(backUpValid(current)){
                t = grid[current.location.x-1][current.location.y][current.location.z+1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(rightUpValid(current)){
                t = grid[current.location.x][current.location.y+1][current.location.z+1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(leftUpValid(current)){
                t = grid[current.location.x][current.location.y-1][current.location.z+1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(frontDownValid(current)){
                t = grid[current.location.x+1][current.location.y][current.location.z-1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(backDownValid(current)){
                t = grid[current.location.x-1][current.location.y][current.location.z-1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(rightDownValid(current)){
                t = grid[current.location.x][current.location.y+1][current.location.z-1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(leftDownValid(current)){
                t = grid[current.location.x][current.location.y-1][current.location.z-1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(UpValid(current)){
                t = grid[current.location.x][current.location.y][current.location.z+1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }
            if(DownValid(current)){
                t = grid[current.location.x][current.location.y][current.location.z-1];
                checkAndUpdateCost(current, t, current.fcost+1);
                //System.out.print("Checking " +t+ "...");
            }   
        }
        if(closed[a.target.x][a.target.y][a.target.z]){
            //valid path found, write it to the units Path variable to remember it for later
            //System.out.println("Valid path found for unitID " +a.id+ "!");
            Cell temp;
            temp = grid[a.target.x][a.target.y][a.target.z];
            //System.out.print(temp);
            a.path.add(new Point(temp.location.x, temp.location.y, temp.location.z));
            while(temp.parent != null){
                //System.out.print(" -> "+temp.parent);
                a.path.add(new Point(temp.parent.location.x, temp.parent.location.y, temp.parent.location.z));
                temp = temp.parent;
            }   
        }//else //System.out.println("No possible path");  
    }
    ArrayList<Integer> finishedUnits = new ArrayList<>();
    public void Mover(){
        int timestep = 0;
        int moved = 0;
        Unit current;
        Point next;
        for(int i=0; i<unitcounter; i++){
            setOccupied(grid[units.get(i).start.x][units.get(i).start.y][units.get(i).start.z]);
            /*history.add(timestep);
            history.add(units.get(i).id);
            history.add(units.get(i).start.x);
            history.add(units.get(i).start.y);
            history.add(units.get(i).start.z);
            */
            //System.out.println(units.get(i));

            //System.out.println(units.get(i).path);
            ArrayList<Point> path = units.get(i).path;
            path.remove(path.size()-1);
            }
        timestep++;
        
        //System.out.println();
        //System.out.println("timestep " +timestep+ ":");
        while(finishedUnits.size() != units.size()){
        	
            if(moved == unitcounter){
                timestep++;
                //System.out.println("timestep " +timestep+ ":");
                moved = 0;
            }
            current = units.get(moved);
            next = current.path.get(current.path.size()-1);
            if(finishedUnits.contains(current.id)){
            	history.add(timestep);
                history.add(current.id);
                history.add(current.start.x);
                history.add(current.start.y);
                history.add(current.start.z);
                history.add(current.start.x);
                history.add(current.start.y);
                history.add(current.start.z);
                moved++;
            }
            if(grid[next.x][next.y][next.z].type == 0){
                current.protocol.add(next);
                history.add(timestep);
                history.add(current.id);
                history.add(current.start.x);
                history.add(current.start.y);
                history.add(current.start.z);
                setFree(grid[current.start.x][current.start.y][current.start.z]);
                current.start = next;
                setOccupied(grid[current.start.x][current.start.y][current.start.z]);
                
                history.add(current.start.x);
                history.add(current.start.y);
                history.add(current.start.z);
                ArrayList<Point> tempPath = current.path;
                tempPath.remove(tempPath.size() - 1);
                moved++;
                //System.out.println("unitID " +current.id+ " moved to " +current.start+ ".");
                if((current.start.x == current.target.x)&&(current.start.y == current.target.y)&&(current.start.z == current.target.z)){
                	finishedUnits.add(current.id);
                	//units.remove(current);
                    setBlocked(grid[current.target.x][current.target.y][current.target.z]);
                    //moved--;
                    //unitcounter--;
                    //System.out.println("unitID " +current.id+ " arrived at its destination.");
                }
            } else if(grid[next.x][next.y][next.z].type == 1){
                current.protocol.add(current.start);
                history.add(timestep);
                history.add(current.id);
                history.add(current.start.x);
                history.add(current.start.y);
                history.add(current.start.z);
                history.add(current.start.x);
                history.add(current.start.y);
                history.add(current.start.z);
                moved++;
                //System.out.println("unitID " +current.id+ " is waiting during this timestep.");
            } else if(grid[next.x][next.y][next.z].type == 2){
                targets.add(current.target);
                Pathfinder(current);
                current.path.remove(current.path.get(current.path.size()-1));
            }
        }
        //System.out.print(history);
    }
    /*
    public  void main(String[] args){
        ArrayList<Point> blocked = new ArrayList<>();
        blocked.add(new Point(3,0,0));
        blocked.add(new Point(3,1,0));
        blocked.add(new Point(3,2,0));
        blocked.add(new Point(3,3,0));
        blocked.add(new Point(3,4,0));
        blocked.add(new Point(3,5,0));
        blocked.add(new Point(3,6,0));
        blocked.add(new Point(3,7,0));
        blocked.add(new Point(3,8,0));
        blocked.add(new Point(3,9,0));
        initialize(10,10,10,blocked);
        addUnit(0,0,0,8,6,0);
        addUnit(0,1,0,9,6,0);
        addUnit(0,2,0,8,7,0);
        addUnit(0,3,0,9,7,0);
        Pathfinder(units.get(0));
        Pathfinder(units.get(1));
        Pathfinder(units.get(2));
        Pathfinder(units.get(3));
        Mover();
    }*/
}
    
    

