package modular.ai;

import com.badlogic.gdx.math.Vector3;

public class Obstacle {
	public int id;
	public Vector3 anchor;

	public Obstacle (int id, float x, float y, float z) {
	     this.id = id;
	     this.anchor = new Vector3(x,y,z);
	}
}

