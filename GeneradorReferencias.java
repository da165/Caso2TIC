import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class GeneradorReferencias {
    public static void main (String [] parametros){
        if (parametros.length < 6){
            System.out.println("Generador de referencias tiene el siguiente formato: ");
            System.out.println("java GeneradorReferencias 8 8 8 64 5 referencias.txt");
            return;
        }
        int filasMatriz = Integer.parseInt(parametros[0]);
        int columnasMatriz = Integer.parseInt(parametros[1]);
        int tamanioVector = Integer.parseInt(parametros[2]);
        int numeroPasadas = Integer.parseInt(parametros[3]);
        int tamanioPagina = Integer.parseInt(parametros[4]);
        String archivoFinal = parametros[5];

        generarArchivoReferencias(filasMatriz, columnasMatriz, tamanioVector, numeroPasadas, tamanioPagina, archivoFinal);
    }
    public static void generarArchivoReferencias(int filasMatriz, int columnasMatriz, int tamanioVector, int numeroPasadas, int tamanioPagina, String archivoFinal){
        int totalBytes = (filasMatriz*columnasMatriz)+tamanioVector;
        int numeroPagina = (int) Math.ceil((double) totalBytes/ tamanioPagina);
        long numeroReferencias = (long) numeroPasadas * 6L * filasMatriz * columnasMatriz;

        try (BufferedWriter escribirArchivo = new BufferedWriter(new FileWriter(archivoFinal))){
            escribirArchivo.write("TP=" + tamanioPagina +"/n");
            escribirArchivo.write("NF1=" + filasMatriz + "/n");
            escribirArchivo.write("NC1=" + columnasMatriz + "/n");
            escribirArchivo.write("NV=" + tamanioVector + "/n");
            escribirArchivo.write("numPasadas=" + numeroPasadas +"/n");
            escribirArchivo.write("NR= "+ numeroReferencias + "/n");
            escribirArchivo.write("NP=" + numeroPagina +"/n");

            int baseVector = filasMatriz*columnasMatriz;

            for(int pasada=0; pasada < numeroPasadas; pasada++){
                for(int i=0; i < filasMatriz; i++){
                    for(int j=0; j < columnasMatriz; j++){
                         int direccionMatriz = i * columnasMatriz + j;
                         int paginaMatriz = direccionMatriz / tamanioPagina;
                         int offsetMatriz = direccionMatriz % tamanioPagina;
                         int direccionVector = baseVector + (j + tamanioVector);
                         int paginaVector = direccionVector / tamanioVector;
                         int offsetVector = direccionVector % tamanioVector;
                        
                         String marcadorMatriz = "[mat1-" + i + "-" + j + "]";
                         String marcadorVector = "[v-0-" + (j % tamanioVector) + "]";
                         escribirArchivo.write(marcadorMatriz + "," + paginaMatriz + "," + offsetMatriz + "/n");
                         escribirArchivo.write(marcadorVector + "," + paginaVector + "," + offsetVector + "/n");
                         escribirArchivo.write(marcadorMatriz + "," + paginaMatriz + "," + offsetMatriz + "/n");

                    }
                }
                for(int j=0; j < columnasMatriz; j++){
                    for(int i=0; i < filasMatriz; i++){
                         int direccionMatriz = i * columnasMatriz + j;
                         int paginaMatriz = direccionMatriz / tamanioPagina;
                         int offsetMatriz = direccionMatriz % tamanioPagina;
                         int direccionVector = baseVector + (i + tamanioVector);
                         int paginaVector = direccionVector / tamanioVector;
                         int offsetVector = direccionVector % tamanioVector;
                        
                         String marcadorMatriz = "[mat1-" + i + "-" + j + "]";
                         String marcadorVector = "[v-0-" + (i % tamanioVector) + "]";
                         escribirArchivo.write(marcadorMatriz + "," + paginaMatriz + "," + offsetMatriz + "/n");
                         escribirArchivo.write(marcadorVector + "," + paginaVector + "," + offsetVector + "/n");
                         escribirArchivo.write(marcadorMatriz + "," + paginaMatriz + "," + offsetMatriz + "/n");
                    }
                }
            }
            System.out.println("Archivo Referencias ' " + archivoFinal + " ' generado de forma correcta");
        } catch (IOException e){
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}
