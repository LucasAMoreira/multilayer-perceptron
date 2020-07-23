arquivo<-read.csv(file="./saidas/erros.csv",header=TRUE, sep=",");

erros <-as.numeric(as.character(arquivo$erro));
epocas <-as.numeric(as.character(arquivo$epoca));

plot(epocas,erros);
#lines(lowess(epocas,erros));
