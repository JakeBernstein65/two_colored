import java.util.*;
import java.io.*;

public class twoColored {
       
        static class Vertex{

                int color;
                LinkedList edges = new LinkedList();

                public void setColor(boolean currentColor){
                        if(currentColor == false){
                          color = 1;
                        }

                        else{
                          color = 2;
                        }
                }

                public int getColor(){
                        return color;
                }

                public LinkedList getEdges(){
                        return edges;
                }

                public void add(int nextNode){
                        edges.add(nextNode);
                }

        }

        //this String keeps track of where a path breaks
        static String wrongPath = "";

        //this array keeps track of the vertices
        static Vertex vertices[];
 
        //get's the string equivalent of a color
        public static String getNameOfColor(int colorName){
                if(colorName == 1){
                   return "red";
                }
                else{
                    //if colorName isn't 1, that means it is 2 and therefore blue
                   return "blue";
                }
        }
       
        public static boolean DFS(int source, boolean bool){
  
                if(vertices[source] == null){
                    vertices[source] = new Vertex();
                }

                //set the currentVertex, the currentVertex's color, and keep track of that color
                Vertex currentVertex = vertices[source];

                currentVertex.setColor(bool);
                int colorOne = currentVertex.getColor();

                // use currentVertex to update the path
                LinkedList<Integer> linked = currentVertex.getEdges();

                //use for each loop to traverse through the edges of the currentVertex 
                for(int i : linked){
                        // If (color == 0) it means that it is undiscovered
                        int colorTwo = vertices[i].getColor();
                        //An invalid cycle was found, so we should return false and an appropriate broken string to match the situation
                        if((colorTwo > 0) && (colorOne == colorTwo)){
                                //keep track of the brokenpath and return false
                                wrongPath = "==> " + (i+1) + "(" + getNameOfColor(colorTwo) + "), ";
                                return false;
                        }
                        else if(colorTwo == 0){
                            if(!DFS(i,!bool)){
                                //keep track of the brokenpath and return false
                                wrongPath = (i+1) + "(" + getNameOfColor(vertices[i].getColor()) + "), " + wrongPath;
                             // }
                                return false;
                                }
                        }
                }
               
                return true;
        }
       
        public static void main(String[] args){
                //check to make sure that whoever is running this file passed in the proper arguments
                if((args.length <= 0) || (args[0] == null)){
                    //Prompt the user with the necessary arguments to run this file if they didn't run the file correctly
                    System.out.println("The necessary arguments are <inputfile name> and <outputfile name>");
                }
                else{
                        try {
                            //read in the name of the file that contains the graph information
                            FileReader reader = new FileReader(args[0]);
                            LineNumberReader lineReader = new LineNumberReader(reader);
                               
                                try {
                                    int fileLength = Integer.parseInt(lineReader.readLine());
                                    vertices = new Vertex[fileLength];

                                        while(lineReader.ready()){
                                            String split[] = lineReader.readLine().split(" ");

                                            //adds the vertex to the vertices array
                                            int indexOne = Integer.parseInt(split[0])-1;
                                            int indexTwo = Integer.parseInt(split[1])-1;

    //The edges are undirected, so we should add them to each vertex. We do this because traversal can happen in either direction
                                            if(vertices[indexOne] == null){
                                                vertices[indexOne] = new Vertex();
                                            }

                                            if(vertices[indexTwo] == null){
                                                vertices[indexTwo] = new Vertex();
                                            }

                                            vertices[indexTwo].add(indexOne);
                                            vertices[indexOne].add(indexTwo);
                                        }
                                        try {
                                            reader.close();
                                            lineReader.close();
                                        }
                                        catch(IOException e){
                                                //If exception arises, print the stack
                                            e.printStackTrace();
                                        }

                                        // Now we want to write our output to a file
                                        try {
                                            //the second argument is the name of the output file
                                            FileOutputStream outputStream = new FileOutputStream(args[1]);
                                            PrintWriter out = new PrintWriter(outputStream);
                                            boolean isTwoColorable = true;
                                            String startPath = "";

                                                // loop through and find vertices that aren't discovered
                                            for(int j = 0; j < vertices.length; j++){
                                                 if(vertices[j] == null || vertices[j].getColor() == 0){
                                                        startPath = (j+1) + "(blue), ";
                                                    
                                                        if(!DFS(j, true)){
                                                            isTwoColorable = false;
                                                            break;
                                                        }
                                                        else {
                                                            startPath = "";
                                                        }
                                                 }
                                            }
 
                                                System.out.println("isTwoColorable = " + isTwoColorable);
                                                // write our output file according to the result
                                                out.println("Two colorable : " + isTwoColorable);
                                                if(isTwoColorable){
                                                        for(int i = 0; i < vertices.length; i++){
                                                                if((vertices[i] != null) && (vertices[i].getColor() == 1)){
                                                                        out.println((i+1) + " = red");
                                                                }
                                                                else {
                                                                    out.println((i+1) + " = blue");
                                                                }
                                                        }
                                                }
                                                else{
                                                    out.println(startPath + wrongPath);
                                                }
                                                out.flush();
                                                out.close();
                                        }
                                        catch(Exception e){
                                        e.printStackTrace();
                                        } 
                                       
                                } 
                                catch(IOException e){
                                        e.printStackTrace();
                                }
                                catch(NumberFormatException e){
                                        e.printStackTrace();
                                } 
                        } 
                        catch(FileNotFoundException e){
                                e.printStackTrace();
                        }
                }
        }
 
}