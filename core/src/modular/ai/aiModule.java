package modular.ai;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

import modular.ai.Pathfinder.Cell;
import modular.ai.Pathfinder.LogicResult;

public interface aiModule {
	void setType(Cell a, int type);

	void refresh();

	void addUnit(int a, int b, int c, int x, int y, int z);

	LogicResult validMove(Cell a);

	boolean finished(int[][] Points);

	void step(int current);

	void Execute();

	void Planner();

	void Mover();

	void initialize(int sIZE, ArrayList<Vector3> blocked);
}
