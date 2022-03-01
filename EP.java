import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;


public class EP{


	public static int tamanhoArray(String s){
		int resultado=0;
		for(int i=0; i<s.length();i++){
			char atual=s.charAt(i);
			if(atual==','){
				resultado++;
			}
		}
		return resultado;
	}

	public static String[] divide(String s, int tamanho){
		String[] resposta = new String[tamanho];
		//double[] resposta = new double[tamanho];
		int inicio=0;
		int fim=0;
		int j=0;
		for(int i=0; i<s.length();i++){
			char atual=s.charAt(i);
			if(atual==','){
				String valor=s.substring(inicio,fim);
				valor = valor.replaceAll("[\"R$ ]", "");
				resposta[j]=valor;
				//resposta[j]=Double.valueOf(valor);
				inicio=i+1;
				fim=inicio-1;
				j++;
			}
			fim++;
		}
		return resposta;
	}

	public static double[] paraNumerico(String[]s){
		double[] resposta=new double[s.length];
		for(int i=0; i<s.length;i++){
			s[i] = s[i].replaceAll("[\"R$ ]", "");
			resposta[i]=Double.valueOf(s[i].toString());
		}
		return resposta;
	}

	public static void main(String[]args)throws Exception{
		String[] teste;
		double[] numerico;
		double[][] dados=new double[21][63];
		int tamanho=0;

		try{
			FileReader fr = new FileReader("entradas/caracteres-limpo.txt");
			BufferedReader br = new BufferedReader(fr);

			int i=0;

			while(i<dados.length){
				//System.out.println("a");
				String linha = br.readLine();
				teste=divide(linha,dados[0].length);
				numerico=paraNumerico(teste);
				dados[i]=numerico;
				i++;
				//System.out.println(dados[i]+" ");
			}

			br.close();
		}
		catch(Exception e){
			throw e;
		}
		/*for(int j=0; j<dados.length; j++){
			System.out.println(j);
			for(int k=0; k<dados[0].length; k++){
				System.out.print(dados[j][k]+" ");
			}
			System.out.println();
		}*/

		int a=(int)(Math.sqrt(63*7));
		MLP rede = new MLP(63,a,7);



		double respostas[][]={
		{1,0,0,0,0,0,0},
		{0,1,0,0,0,0,0},
		{0,0,1,0,0,0,0},
		{0,0,0,1,0,0,0},
		{0,0,0,0,1,0,0},
		{0,0,0,0,0,1,0},
		{0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0},
		{0,1,0,0,0,0,0},
		{0,0,1,0,0,0,0},
		{0,0,0,1,0,0,0},
		{0,0,0,0,1,0,0},
		{0,0,0,0,0,1,0},
		{0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0},
		{0,1,0,0,0,0,0},
		{0,0,1,0,0,0,0},
		{0,0,0,1,0,0,0},
		{0,0,0,0,1,0,0},
		{0,0,0,0,0,1,0},
		{0,0,0,0,0,0,1}};

		rede.treinamento(dados,respostas);

		System.out.println("Fim do treino");

		try{
			FileReader fr = new FileReader("entradas/caracteres-ruido.csv");
			BufferedReader br = new BufferedReader(fr);

			int i=0;

			while(i<dados.length){
				//System.out.println("a");
				String linha = br.readLine().replaceAll("\\uFEFF","");
				teste=divide(linha,dados[0].length);
				numerico=paraNumerico(teste);
				dados[i]=numerico;
				i++;
				//System.out.println(dados[i]+" ");
			}

			br.close();
		}
		catch(Exception e){
			throw e;
		}


		rede.teste(dados);

	}



}
