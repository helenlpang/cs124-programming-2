import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Strassen{
    public static int[][] normalMult(int[][] matr1, int[][] matr2) {
        int dimension = matr1.length;

        int[][] matr3 = new int[dimension][dimension];
            
            //Multiply the two matrices conventionally
            for (int i = 0; i < dimension; i++) {
                for (int k = 0; k < dimension; k++) {
                    for (int j = 0; j < dimension; j++) {
                        matr3[i][j] += matr1[i][k] * matr2[k][j];
                    }
                }
            }
            
            //Return the resulting matrix
            return matr3;
    }

    public static int[][] strassenMult(int[][] matr1, int[][] matr2, int dim){

        //Finds the dimension of the two matrices we will apply Strassen's on to
        int dimension = matr1.length;

        //This if-else statement determines where the cross-over point will be used
        //If we are under the cross-over point:
        if (dimension <= dim){
            //Instantiate resulting matrix
            int[][] matr3 = new int[dimension][dimension];
            
            //Multiply the two matrices conventionally
            for (int i = 0; i < dimension; i++) {
                for (int k = 0; k < dimension; k++) {
                    for (int j = 0; j < dimension; j++) {
                        matr3[i][j] += matr1[i][k] * matr2[k][j];
                    }
                }
            }
            
            //Return the resulting matrix
            return matr3;
        }

        //If we are over the cross-over point:
        else{

            /*This section instantiates stuff and performs the necessary matrix additions*/
            ///////////////////////////////////////////////////////////////////////////////////////////
            //Half of the dimension, used to split the matrices up into 4 parts
            int split = dimension / 2;

            //Instantiate matrices used in Strassen's
            int[][] matrP11 = new int[split][split];
            int[][] matrP12 = new int[split][split];
            int[][] matrP21 = new int[split][split];
            int[][] matrP22 = new int[split][split];
            int[][] matrP31 = new int[split][split];
            int[][] matrP32 = new int[split][split];
            int[][] matrP41 = new int[split][split];
            int[][] matrP42 = new int[split][split];
            int[][] matrP51 = new int[split][split];
            int[][] matrP52 = new int[split][split];
            int[][] matrP61 = new int[split][split];
            int[][] matrP62 = new int[split][split];
            int[][] matrP71 = new int[split][split];
            int[][] matrP72 = new int[split][split];

            //Perform the necessary additions that create the matrices used in Strassen's
            for (int i = 0; i < split; i++){
                for(int j = 0; j < split; j++){
                    matrP11[i][j] = matr1[i][j];
                    matrP12[i][j] = matr2[i][j + split] - matr2[i + split][j + split];
                    matrP21[i][j] = matr1[i][j] + matr1[i][j + split];
                    matrP22[i][j] = matr2[i + split][j + split];
                    matrP31[i][j] = matr1[i + split][j] + matr1[i + split][j + split];
                    matrP32[i][j] = matr2[i][j];
                    matrP41[i][j] = matr1[i + split][j + split];
                    matrP42[i][j] = matr2[i + split][j] - matr2[i][j];
                    matrP51[i][j] = matr1[i][j] + matr1[i + split][j + split];
                    matrP52[i][j] = matr2[i][j] + matr2[i + split][j + split];
                    matrP61[i][j] = matr1[i][j + split] - matr1[i + split][j + split];
                    matrP62[i][j] = matr2[i + split][j] + matr2[i + split][j + split];
                    matrP71[i][j] = matr1[i][j] - matr1[i + split][j];
                    matrP72[i][j] = matr2[i][j] + matr2[i][j + split];
                }
            }
            ///////////////////////////////////////////////////////////////////////////////////////////


            /*This section is the recursive part of Strassen's*/
            ///////////////////////////////////////////////////////////////////////////////////////////
            //Instantiate the P's of Strassen and do the recursion
            int[][] matrP1 = strassenMult(matrP11, matrP12, dim);
            int[][] matrP2 = strassenMult(matrP21, matrP22, dim);
            int[][] matrP3 = strassenMult(matrP31, matrP32, dim);
            int[][] matrP4 = strassenMult(matrP41, matrP42, dim);
            int[][] matrP5 = strassenMult(matrP51, matrP52, dim);
            int[][] matrP6 = strassenMult(matrP61, matrP62, dim);
            int[][] matrP7 = strassenMult(matrP71, matrP72, dim);
            ///////////////////////////////////////////////////////////////////////////////////////////


            /*This section constructs our resulting matrix*/
            ///////////////////////////////////////////////////////////////////////////////////////////
            //Instantiate the four parts of our resulting matrix
            int[][] matr31 = new int[split][split];
            int[][] matr32 = new int[split][split];
            int[][] matr33 = new int[split][split];
            int[][] matr34 = new int[split][split];

            //Construct them
            for (int i = 0; i < split; i++){
                for (int j = 0; j < split; j++){
                    matr31[i][j] = matrP5[i][j] + matrP4[i][j] - matrP2[i][j] + matrP6[i][j];
                    matr32[i][j] = matrP1[i][j] + matrP2[i][j];
                    matr33[i][j] = matrP3[i][j] + matrP4[i][j];
                    matr34[i][j] = matrP5[i][j] + matrP1[i][j] - matrP3[i][j] - matrP7[i][j];
                }
            }

            //Instantiate our resulting matrix
            int[][] matr3 = new int[dimension][dimension];

            //Construct it using the four parts
            for (int i = 0; i < split; i++){
                for (int j = 0; j< split; j++){
                    matr3[i][j] = matr31[i][j];
                    matr3[i][j + split] = matr32[i][j];
                    matr3[i + split][j] = matr33[i][j];
                    matr3[i + split][j + split] = matr34[i][j];
                }
            }
            ///////////////////////////////////////////////////////////////////////////////////////////

            //Return the resulting matrix
            return matr3;  
        }        
    }

    public static void main(String[] args) throws FileNotFoundException, IOException{



        // /*This section does some initialization work*/
        // ///////////////////////////////////////////////////////////////////////////////////////////
        //This will be the input dimension, the dimension of the matrix in question
        int dimension = 1024;

        //This will determine the dimensions of our matrix with zeros added on to fit a power of 2
        int alt_dimension = (int) Math.pow(2.0, Math.ceil(Math.log(dimension) / Math.log(2)));

        // // //This is some file and scan instantiation
        // // BufferedWriter writer = new BufferedWriter(new FileWriter("test1.txt"));

        // int[][] triangMatr = new int[dimension][dimension];

        // for (int i = 0; i < dimension; i++){
        //     for (int j = 0; j < dimension; j++){
        //         if (j > i){
        //             int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        //             if (randomNum == 1){
        //                 triangMatr[i][j] = 1;
        //             }
        //             else{
        //                 triangMatr[i][j] = 0;
        //             }
        //         }
        //         else{
        //             triangMatr[i][j] = triangMatr[j][i];
        //         }
        //     }
        // }

        // int[][] matr = strassenMult(triangMatr, triangMatr);
        // int[][] finalMatr = strassenMult(matr, triangMatr);
        // int sumTriang = 0;

        // for (int i = 0; i < dimension; i++){
        //     for (int j = 0; j < dimension; j++){
        //         if (i == j){
        //             sumTriang += finalMatr[i][j];
        //         }
        //     }
        // }

        // System.out.println(sumTriang / 6.0);

        // // for (int i = 0; i < dimension; i++){
        // //     int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        // //     String connection;

        // //     if (randomNum == 1){
        // //         connection = Integer.toString(1);
        // //     }
        // //     else{
        // //         connection = Integer.toString(0);
        // //     }

        // //     writer.write(connection);
        // // }
        // // writer.close();

        File file = new File("test.txt");
        Scanner scan = new Scanner(file);
        ///////////////////////////////////////////////////////////////////////////////////////////


        /*This section creates our two matrices to multiply*/
        ///////////////////////////////////////////////////////////////////////////////////////////
        //Instantiation of the matrix of a power of 2
        int[][] matr1 = new int[alt_dimension][alt_dimension];
        int[][] matr2 = new int[alt_dimension][alt_dimension];

        //These for loops iterate through both matrices
        for (int i = 0; i < alt_dimension; i++){
            for (int j = 0; j < alt_dimension; j++){
                //This is the padding of extra zeroes
                if (i >= dimension || j >= dimension){
                    matr1[i][j] = 0;
                }
                else{
                    matr1[i][j] = Integer.parseInt(scan.nextLine());
                }
            }
        }

        //Same thing
        for (int i = 0; i < alt_dimension; i++){
            for (int j = 0; j < alt_dimension; j++){
                if (i >= dimension || j >= dimension){
                    matr2[i][j] = 0;
                }
                else{
                    matr2[i][j] = Integer.parseInt(scan.nextLine());
                }
            }
        }

        //Just closes the scanner (not really important but makes an error message shut up)
        scan.close();
        ///////////////////////////////////////////////////////////////////////////////////////////


        // HELEN'S TIMING //////////////////////////////////////////////////////////////////////////

        int dimm = 2;
        while(true) {
            long start = System.currentTimeMillis();
            int[][] matr3 = strassenMult(matr1, matr2, dimm);
            long finish = System.currentTimeMillis();
            long time = finish - start;
            System.out.println(time + "diff");
            System.out.println(dimm + "dim");
            dimm*=2;
        }


        // /*This section calls the recursive strassenMult method and prints the resulting diagonal*/
        // ///////////////////////////////////////////////////////////////////////////////////////////
        // //Call strassenMult and instantiate start/finish times
    
        // long start = System.currentTimeMillis();
        // int[][] matr3 = strassenMult(matr1, matr2, dimmm);
        // long finish = System.currentTimeMillis();
        //  time1 = finish - start;

        // long start2 = System.currentTimeMillis();
        // int[][] matr32 = normalMult(matr1, matr2);
        // long finish2 = System.currentTimeMillis();
        //  time2 = finish2 - start2;


        // System.out.println(time1 + "strassen");
        // System.out.println(time2 + "normal");
        // System.out.println(time1 - time2 + "diff");

        //     dimmm = dimmm + 1;
        //     System.out.println("dim "+ dimmm);

        
        // //Print the diagonal entries of the resulting matrix
        // for (int i = 0; i < dimension; i++){
        //     for (int j = 0; j < dimension; j++){
        //         if (i == j){
        //             System.out.println(matr3[i][j]);
        //         }
        //     }
        // }

        // //Print the time elapsed
        // System.out.println((finish - start) / 1000.0);
        // ///////////////////////////////////////////////////////////////////////////////////////////
    }
}