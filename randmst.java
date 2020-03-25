import java.util.*;

public class randmst {
    public static double calculateWeight(int currentIndex, int referenceIndex, int dimension, double[][] points){
        double d = 0.0;
        for(int k = 0; k < dimension; k++){
          d += (points[currentIndex][k] - points[referenceIndex][k]) * (points[currentIndex][k] - points[referenceIndex][k]);
        }
        d = Math.sqrt(d);
        return d;
    }
    public static double traverse(int numpoints, int numtrials, int dimension){
        if (dimension == 0) {
          return traverse_0(numpoints, numtrials);
        }
        double total = 0;

        for (int i = 0; i < numtrials; i++){

            Random rand1 = new Random();
            double[][] points = new double[numpoints][dimension];
            for (int k = 0; k < dimension; k++){
                points[0][k] = rand1.nextDouble();
            }
            int referenceIndex = 0;

            double[] keyWeight = new double[numpoints];
            for (int k = 1; k < numpoints; k++){
                keyWeight[k] = Double.MAX_VALUE;
            }    
            keyWeight[0] = -1.0;

            int traverseCounter = 1;
            while (true) {
                double min = Double.MAX_VALUE;
                int minIndex = -1;

                for (int j = 0; j < numpoints; j++){
                    if (keyWeight[j] != -1.0){
                        for (int l = 0; l < dimension; l++){
                            Random rand = new Random();
                            double rn = rand.nextDouble();
                            points[j][l] = rn;
                        }

                        double d = calculateWeight(j, referenceIndex, dimension, points);
                        if (keyWeight[j] > d){
                            keyWeight[j] = d;
                        }
                          
                        if (keyWeight[j] < min){
                            min = keyWeight[j];
                            minIndex = j;
                        }
                    }
                }

                total += min;
                System.out.println(min);
                keyWeight[minIndex] = -1.0;
                referenceIndex = minIndex;
    
                traverseCounter++;
                if (traverseCounter == numpoints){
                  break;
                }
                
            }
            System.out.println("Trial " + (i + 1) + ": " + total);
            
        }
        return total/numtrials;
    }

    public static double traverse_0(int numpoints, int numtrials) {
        double total = 0;
    
        for(int i = 0; i < numtrials; i++){
          
          double[] keyWeight = new double[numpoints];
          for (int k = 1; k < numpoints; k++){
            keyWeight[k] = Double.MAX_VALUE;
          }
          keyWeight[0] = -1.0;
      
          int traverseCounter = 1;
          while (true) {
            double min = Double.MAX_VALUE;
            int minIndex = -1;
            
            for(int j = 1; j < numpoints; j++){
              if(keyWeight[j] != -1.0) {
                Random rand = new Random();
                double rn = rand.nextDouble();
                if (keyWeight[j] > rn){
                  keyWeight[j] = rn;
                }
                
                if (keyWeight[j] < min){
                  min = keyWeight[j];
                  minIndex = j;
                }
              }
            }
    
            total += min;
            System.out.println(min);
            keyWeight[minIndex] = -1.0;
    
            traverseCounter++;
            if (traverseCounter == numpoints){
              break;
            }
          }
          System.out.println("Trial " + (i + 1) + ": " + total);
        }
        
        return total / numtrials;
      }
    
    public static void main(String[] args) 
    { 
          //double averageWeight = traverse_0(1000, 5);
          //System.out.println("Average weight:" + averageWeight);
    
        // double averageWeight = traverse_0(32, 5);
        // double averageWeight = traverse(512, 5, 4);
        double averageWeight = traverse(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

        System.out.println("Average weight:" + averageWeight);
    }
}