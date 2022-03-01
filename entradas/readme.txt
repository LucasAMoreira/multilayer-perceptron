Entradas do arquivo

Histórico de mudanças

## Separação de classes
- Originalmente Main.java continha (além da classe Main) a classe Perceptron.
- Separamos as classes com o arquivo Perceptron.java
- Separamos as classes EP e MLP em dois arquivos distintos

## Métodos renomeados e com novas assinaturas
- O método treinamento foi renomeado para "treino"
- o método treino recebeu nova assinatura treino(double[][] conjuntoTreino, int epocas)

## Novos métodos:
- atualizaPesos
- alimentaEntrada

## Escopo
Atributos e métodos (Exceto Teste e Treino) com acesso privado
