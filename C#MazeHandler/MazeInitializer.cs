using System;

	public class MazeInitializer {
    
	    public string[,] matrixMaker(string filename)
    	{
        	string[] lines = System.IO.File.ReadAllLines("../TestMazes" + filename);
        	string[] dimensions = lines[0].Split(' ');
        	int rows = Int32.Parse(dimensions[0]);
	        int columns = Int32.Parse(dimensions[1]);
    	    string[,] mazeMatrix = new string[rows,columns];
        	for(int i = 0; i < rows; i++){
	        	string[] walls = lines[i+1].Split(' ');
    	    	for(int j = 0; j < columns; j++){
        			mazeMatrix[i, j] = walls[j];
	        	}
    	    }

        	return mazeMatrix;
    	 }

    	public static void Main(){}
	}