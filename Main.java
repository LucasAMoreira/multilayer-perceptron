
public class Main{

	public static void main(String[]args){
		double[][] and=	{
					{1,1,1},
					{1,-1,-1},
					{-1,1,-1},
					{-1,-1,-1}
				};
		Perceptron p=new Perceptron(2);

		//double[] pesos=p.treinamento(and);
		double[] pesos = p.treino(and,2);
		/*for(int i=0; i<pesos.length; i++){
			System.out.println(pesos[i]);
		}*/

		double[][] in ={
				{1,1},
				{1,-1},
				{-1,1},
				{-1,-1}
				};
		p.teste(in);

	}

}
