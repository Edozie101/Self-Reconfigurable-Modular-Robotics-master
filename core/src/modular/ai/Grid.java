package modular.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector3;

import modular.state.AIState;
import modular.util.Constants;

/**
 * @author Group2
 * A grid class used to divide the board into smaller, easier to handle, squares.
 */
public class Grid {

	int id;
	int xdimension;
	int zdimension;
	int ydimension;
	int x;
	int z;
	ArrayList<Vector3> positions;
	public Grid top = null;
	public Grid right = null;
	public Grid bottom = null;
	public Grid left = null;
	public GridData data;
	public int obstacles=0;
	static Map<Float, Vector3> currentState = new HashMap<>(AIState.currentState);
	
	static Map<Float, Vector3> finalState = new HashMap<>(AIState.finalState);
	
	/**
	 * @param a first value of grid anchor position vector
	 * @param b second value of grid anchor position vector
	 * @param x grid rightmost limit
	 * @param y grid topmost limit
	 * @param z grid height
	 * @param id grid individual identification numberr
	 */
	public Grid(int a, int b, int x, int y, int z, int id){
		xdimension = x-a;
		zdimension = z-b;
		this.x = a;
		this.z = b;
		ydimension = y;
		this.id = id;
		positions = new ArrayList<Vector3>();
		
		data = new GridData(this);
		
		for(int k = 0; k<xdimension; k++){
			for(int l = 0; l<zdimension; l++){
				for(int m = 0; m<ydimension; m++){
					positions.add(new Vector3(k,l,m));
				}
			}
		}
	}
	public void setTop(Grid x){
		top = x;
	}
	public void setRight(Grid x){
		right = x;
	}
	public void setBottom(Grid x){
		bottom = x;
	}
	public void setLeft(Grid x){
		left = x;
	}
	public Grid getTop(){
		return top;
	}
	public Grid getRight(){
		return right;
	}
	public Grid getBottom(){
		return bottom;
	}
	public Grid getLeft(){
		return left;
	}
	public int getID(){
		return id;
	}
	public ArrayList<Vector3> getPositions(){
		return positions;
	}
	/**
	 * @param width of the grid
	 * @param height of the grid
	 * @return ArrayList of all initialized Grids
	 */
	public static ArrayList<Grid> initGrids(int width, int height){
		int xLimit = (int) Constants.SIZE;
		int zLimit = (int) Constants.SIZE;
		int yLimit = (int) Constants.SIZE;
		int xCount =  (int) (xLimit/width);
		int zCount =  (int) (zLimit/height);
		int idCount = 0;
		ArrayList<Grid> arr = new ArrayList<>();
		int a = 0;
		int x = width-1;
		int b = 0;
		int z = height-1;
		
		//initializes a Grid object for each 'width' by 'height' square on the map
		for(int i = 0;i<xCount;i++){
			
			for(int j = 0;j<zCount;j++){
			a = 0+(i*width);
			x = width-1+(i*width);
			b = 0+(j*height);
			z = height-1+(j*height);		
			arr.add(new Grid(a,b,x,z,yLimit,idCount));
			idCount++;
			}
		}
		//initializes references to neighbors for each Grid object
		int k = 0;
		int m = 0;
		for(Grid tempGrid: arr){
			int id = tempGrid.getID();
			
			if(k==0){
			tempGrid.setTop(arr.get(id+1));
			k++;
			}
			else if(k<zCount-1){
			tempGrid.setTop(arr.get(id+1));
			tempGrid.setBottom(arr.get(id-1));
			k++;
			}
			else{
			tempGrid.setBottom(arr.get(id-1));
			k = 0;
			}
			if(m<zCount){
			tempGrid.setRight(arr.get(id+zCount));
			m++;
			}
			else if(m<arr.size()-zCount){
			tempGrid.setRight(arr.get(id+zCount));
			tempGrid.setLeft(arr.get(id-zCount));
			m++;
			}
			else{
			tempGrid.setLeft(arr.get(id-zCount));
			m++;
			}
			for(Vector3 position : currentState.values()){
				if(position.x > tempGrid.x-1 && position.x < tempGrid.x+tempGrid.xdimension ){
					if(position.z > tempGrid.z-1 && position.z < tempGrid.z+tempGrid.zdimension ){
						tempGrid.data.start = true;
					}
				}
			}
			for(Vector3 position : finalState.values()){
				if(position.x > tempGrid.x-1 && position.x < tempGrid.x+tempGrid.xdimension ){
					if(position.z > tempGrid.z-1 && position.z < tempGrid.z+tempGrid.zdimension ){
						tempGrid.data.end = true;
					}
				}
			}
		}
		return arr;
	}
	
	public String toString(){
		return " "+ id;
	}
}
