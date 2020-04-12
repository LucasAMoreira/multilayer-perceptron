
public class Util {
	
	public static double[][] getWeightMatrix(int rows, int columns) {
		double[][] weightMatrix = new double[rows][columns];
		for (int i = 0; i < weightMatrix.length; i ++)
			for (int y = 0; y < weightMatrix[i].length; y++)
				weightMatrix[i][y] = 0;
		return weightMatrix;
	}
}
