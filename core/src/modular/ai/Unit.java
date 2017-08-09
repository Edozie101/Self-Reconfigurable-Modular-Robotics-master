package modular.ai;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

public class Unit {
	public int id;
	public int adj;
	public float manhattan;
	public Vector3 prev;
    public Vector3 anchor;
    public Vector3 next;
    public ArrayList<Vector3> protocol;
    public Unit(int id, float x, float y, float z) {
         this.id = id;
         this.adj = 0;
         this.manhattan = 0;
         this.anchor = new Vector3(x,y,z);
         this.prev = new Vector3(x,y,z);
         this.protocol = new ArrayList<>();
         protocol.add(anchor);
     }
}
