package controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;
import java.util.regex.Matcher;

import model.*;

import static model.ReservedWords.*;


public class LexicalAnalyzer {

    public static final ArrayList wordList = new ArrayList();
    public static final ArrayList errorList = new ArrayList();
    static int line = 0;
    public static String[] word = null;
    public static String[] wordL = null;
    public static boolean exist = false;
    @SuppressWarnings("unused")
    public static void readArq() throws FileNotFoundException {
        try{
            File file = new File("input"); // Identifica o diretório de entrada de arquivos
            File afile[] = file.listFiles(); // Lista todos os arquivos do diretório e armazena em um array
            int l = 0;
            int x = 0;
            int o = 0;
            boolean dComentario = false;
            boolean mesma_linha = false;
            boolean identificou = false;
            for (int j = afile.length; l < j; l++) {
                if (afile[l].toString().contains("entrada")){ // Verifica de o arquivo inicia com o nome "entrada"
                    File arquivos = afile[l]; // Armazena os nomes dos arquivos já verificados
                    String name[] = afile[l].toString().split("entrada"); // Separa o restante do nome do arquivo
                    for (int h = 0; h < name.length; h++){
                        if (name[h].toString().contains(".txt")) { // Verifica se o arquivo possui a extensão .txt
                            BufferedReader inputArq = new BufferedReader(new FileReader(afile[l])); // // Passa o caminho e o nome do arquivo
                            BufferedWriter outputArq = new BufferedWriter(new FileWriter("output//saida" + name[h])); // Concatena a String "saida" com o restante do nome do arquivo de entrada
                            PrintWriter gravarArq = new PrintWriter(outputArq);
                            gravarArq.printf("LISTA DE TOKENS:\n\n");
                            while (inputArq.ready()){
                                wordList.add(inputArq.readLine());

                                for (int i = 0; i < wordList.size(); i++) {

                                    word = wordList.get(i).toString().split("([\\;]|([ ])|[(])|[)]|[|]|[&]|[=]|[{]|[}]");
                                    for (int t = 0; t < word.length; t++){
                                        if(!word[t].equals("") && word[t].matches("(-)?\\s*[0-9]([0-9]*\\.?[0-9]+)?")) {
                                            gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + word[t] + "\t\t\t" + "|\tNumeral");
                                        }else if(!word[t].equals("") && word[t].matches("(-)?\\s*[0-9]([0-9]*\\.?[a-z]+)?")) {
                                            errorList.add("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + word[t] + "\t\t" + "|\tNúmero mal formado");
                                        }
                                        else if(!word[t].equals("") && word[t].matches("(-)?\\s*[a-z]([a-z]*\\.?[0-9]+)?")) {
                                            errorList.add("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + word[t] + "\t\t\t" + "|\tIdentificador mal formado");
                                        }
                                        else if(!word[t].equals("") && word[t].matches("[a-zA-Z]([a-zA-Z]*[!-+])")) {
                                            errorList.add("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + word[t] + "\t\t\t" + "|\tIdentificador mal formado");
                                        }
                                    }
                                    word = null;
                                    word = wordList.get(i).toString().split("[\\W]");
                                    for (int g = 0; g < word.length; g++){
                                        for (int k = 0; k < Structs_Lexical.aSLexical.size(); k++){
                                            if (!word[g].equals("") && !Structs_Lexical.aSLexical.toString().contains(word[g]) && !word[g].matches("[0-9]+") && word[g].matches("[_]?(([a-z]|[A-Z]|_)+[0-9]*)+(([a-z]|[A-Z]|[0-9]|_)*)*")){
                                                gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + word[g] + "\t\t\t" + "|\tIdentificador");
                                                break;
                                            }
                                        }
                                    }
                                    word = null;
                                    word = wordList.get(i).toString().split(""); // Identificação dos erros
                                    for (int z = 0; z < word.length; z++){
                                        for (int g = 0; g < Symbol.aSymbol.size(); g++){
                                            if (!word[z].equals(" ") && !word[z].equals("&")){
                                                if (!Symbol.aSymbol.contains(word[z])){ // Intentifica simbolos que não pertencem a linguagem
                                                    errorList.add("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + word[z] + "\t\t\t" + "|\tSímbolo não pertencente a linguagem");
                                                    break;
                                                }
                                            }
                                        }
                                        if(word[z].equals("&") && word[z+1].equals("&")){ // Identifica o operador lógico && mal formado
                                            exist = true;
                                            break;
                                        }else if (word[z].equals("&") && !word[z+1].equals("&")){
                                            errorList.add("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + word[z] + "\t\t\t" + "|\tOperador Lógico mal formado");
                                            z++;
                                            exist = false;
                                        }
                                    }
                                    word = null;
                                    word = wordList.get(i).toString().split(""); // Identificação dos erros
                                    for (int z = 0; z < word.length; z++){
                                        if(word[z].equals("|") && word[z+1].equals("|")){ // Identifica o operador lógico && mal formado
                                            exist = true;
                                            break;
                                        }else if (word[z].equals("|") && !word[z+1].equals("|")){
                                            errorList.add("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + word[z] + "\t\t\t" + "|\tOperador Lógico mal formado");
                                            z++;
                                            exist = false;
                                        }
                                    }
                                    word = null;
                                    for (int k = 0; k < CommentDelimiters.aCDelimiters.size(); k++){
                                        if(wordList.get(i).toString().contains(CommentDelimiters.aCDelimiters.get(k).toString()) && !dComentario){
                                            gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + CommentDelimiters.aCDelimiters.get(k).toString() + "\t\t\t" + "|\tDelimitador de Comentário");
                                            dComentario = true;
                                        } else if (wordList.get(i).toString().contains(CommentDelimiters.aCDelimiters.get(k).toString()) && dComentario){
                                            gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + CommentDelimiters.aCDelimiters.get(k).toString() + "\t\t\t" + "|\tDelimitador de Comentário");
                                            dComentario = false;
                                            mesma_linha= true;
                                        }
                                    }
                                    for (int g = 0; g < ArithmeticOperators.aAOperators.size(); g++){
                                        if(wordList.get(i).toString().contains(ArithmeticOperators.aAOperators.get(g).toString()) && !dComentario && !mesma_linha){
                                            gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + ArithmeticOperators.aAOperators.get(g).toString() + "\t\t\t" + "|\tOperador Aritmético");
                                        }
                                    }
                                    for (int k = 0; k < Delimiters.aDelimiters.size(); k++){
                                        if(wordList.get(i).toString().contains(Delimiters.aDelimiters.get(k).toString())){
                                            gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + Delimiters.aDelimiters.get(k).toString() + "\t\t\t" + "|\tDelimitador");
                                        }
                                    }
                                    for (int k = 0; k < LogicalOperators.aLOperators.size(); k++){
                                        if(wordList.get(i).toString().contains(LogicalOperators.aLOperators.get(k).toString())){
                                            gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + LogicalOperators.aLOperators.get(k).toString()+ "\t\t\t" + "|\tOperador Lógico");
                                        }
                                    }
                                    for (int k = 0; k < RelationalOperators.aROperators.size(); k++){
                                        if (wordList.get(i).toString().contains(RelationalOperators.aROperators.get(k).toString())){
                                            gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + RelationalOperators.aROperators.get(k).toString() + "\t\t\t" + "|\tOperador Relacional");
                                        }
                                    }
                                    for (int k = 0; k < aRWords.size(); k++){
                                        if (wordList.get(i).toString().contains(aRWords.get(k).toString())){
                                            gravarArq.println("Linha:\t" + (line+1) + "\t" + "| Lexema:\t" + aRWords.get(k) + "\t\t\t" + "|\tPalavra Reservada");
                                            break;
                                        }
                                    }

                                }
                                wordList.clear();
                                line++;
                            }
                            inputArq.close();
                            gravarArq.printf("\n\nLISTA DE ERROS:\n\n");
                            for (int e = 0; e < errorList.size(); e++){
                                gravarArq.println(errorList.get(e));
                            }
                            outputArq.close();
                            line = 0;
                        }
                    }
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
// tratar comentarios, guardar lista de tokens, tratar erros.