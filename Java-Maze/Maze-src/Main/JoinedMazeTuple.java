package Main;

import Maze.Maze;

public class JoinedMazeTuple {

	public final Maze origin; 
	public final Maze teleMaze;
	
	public JoinedMazeTuple(Maze origin, Maze teleMaze)
	{
		this.origin = origin; 
		this.teleMaze = teleMaze; 
	}
}
