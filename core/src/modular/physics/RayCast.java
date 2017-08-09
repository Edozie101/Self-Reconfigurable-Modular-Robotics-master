package modular.physics;

import com.badlogic.gdx.math.Vector3;

public class RayCast {
	public Vector3 traceRay(Vector3 position, Vector3 direction){
		Vector3 ray1 = new Vector3(position);
		Vector3 ray2 = new Vector3(position);
		Vector3 ray3 = new Vector3(position);
		Vector3 ray4 = new Vector3(position);
		
		Vector3 contNormDir = new Vector3(direction);
		contNormDir.nor();
		
		ray1.nor();
		ray2.add(contNormDir.x, 0f, contNormDir.z).nor();
		ray3.add(0f, 1f, 0f).nor();
		ray4.add(contNormDir.x, 1f, contNormDir.z).nor();
		
		contNormDir.scl(0.01f);
		
		boolean collided = false;
		
		while(!collided){
			ray1.add(contNormDir);
			ray2.add(contNormDir);
			ray3.add(contNormDir);
			ray4.add(contNormDir);
			
			collided = true;
		}
		Vector3 collPos = new Vector3();
		return collPos;
	}
}
