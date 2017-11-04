using System;
using System.Diagnostics;

public class JarStarter {

	public static void Main(){
		string[] args = new string[]{"0", "../TestMazes", "T", "30", "40", "0", "0", "0"};
		jarRunner(args);
		MazeInitializer mazeInit = new MazeInitializer();
		string[,] mazeMatrix = mazeInit.matrixMaker(args[2]);
	}

    public static void jarRunner(string[] args){
   		System.Diagnostics.Process javaJar = new Process();
       	javaJar.StartInfo.FileName = "MazeGen.jar";
       	string arguments = "";
       	foreach(string arg in args) {
       		arguments += arg;
       		arguments += " ";
       	}
       	System.Console.WriteLine(arguments);
       	javaJar.StartInfo.Arguments = arguments;
       	javaJar.Start();
   	}
}

public class MazeInitializer {
    
	public string[,] matrixMaker(string filename)
    {
       	string[] lines = System.IO.File.ReadAllLines("../TestMazes/" + filename);
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
}