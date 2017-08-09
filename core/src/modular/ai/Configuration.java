package modular.ai;

import java.util.ArrayList;

public class Configuration {
	final int sizex;
	final int sizey;
	final int sizez;
	final int unitno;
	final int[][][] blocked;
	public ArrayList<Unit> units;
	public ArrayList<Obstacle> obstacles;
	public Configuration (int sizex, int sizey, int sizez, ArrayList<Unit> units, ArrayList<Obstacle> obstacles) {
		this.sizex = sizex;
		this.sizey = sizey;
		this.sizez = sizez;
		this.unitno = units.size();
		this.units = units;
		this.blocked = new int[sizex*10][sizey*10][sizez*10];
		for (int i = 0; i < obstacles.size(); i++) {
			int tempx = (int) (10*obstacles.get(i).anchor.x);
			int tempy = (int) (10*obstacles.get(i).anchor.y);
			int tempz = (int) (10*obstacles.get(i).anchor.z);
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 10; k++) {
					for (int l = 0; l < 10; l++) {
						blocked[tempx+j][tempy+k][tempz+l] = 1;
					}
				}
			}
		}
	}
}

