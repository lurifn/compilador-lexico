package ufabc_bcc_compiladores.compilador_lexico;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import ufabc_bcc_compiladores.compilador_lexico.enums.ETipoToken;

public class AnalisadorLexico {

    public static final String[] STRINGSRESERVADAS = {"programa", "inicio","declare", "fim"};

    private char[] content;
    private int    pos;
    public AnalisadorLexico(String filename){
       try{
    	   byte[] bContent = Files.readAllBytes(new File(
           		getClass().getClassLoader().getResource(filename).getFile()
           		).toPath());
           this.content = new String(bContent).toCharArray();
           this.pos = 0;
       }
       catch(IOException ex){
           System.err.println("Erro ao ler arquivo");
       }
       
    }

    private boolean eof(){
       return pos==content.length;
    }
    private char nextChar(){
        return content[pos++];
    }

    private boolean isLetter(char c){
        return c >= 'a' && c <= 'z';
    }
    private boolean isWhitespace(char c){
        return c==' ' || c=='\n' || c=='\t' || c=='\r' ;
    }
    private boolean isOperator(char c){
       return c=='+' || c=='-' || c=='*' || c=='/' || c=='^' || c=='=';
    }
    private boolean isDigit(char c){
        return c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' || c=='7' || c=='8' || c=='9' || c=='0';
    }
    private boolean isColon(char c)
    {
    	return c==':';
    }
    private boolean isPunctuation(char c)
    {
    	return c==',' || c=='.' || c=='(' || c==')' || c==':';
    }
    private void retroceder(){
       pos--;
    }


    public Token nextToken(){
       int s=0;
       int startingPoint = pos;
       ETipoToken tokenToReturn = ETipoToken.CONTINUA; //Numero do capeta para evitar bugs.
       while(tokenToReturn == ETipoToken.CONTINUA){
         switch(s){
           case 0:
              char c = nextChar();
              //Esse vem primeiro pq o colon pode ser so : ou :=
              if (isColon(c)){
                  s = 3;
              } 
              else if (isLetter(c)){
                  s = 1;
              }
              else if (isOperator(c)){
                 s = 2;
              }
              else if (isWhitespace(c)){
                 s = 0;
              }  
              else if (isPunctuation(c)){
                 tokenToReturn = ETipoToken.PONTUACAO;
              } 
              else if (isDigit(c)){
                  s = 4;
               } 
              else{
                 tokenToReturn = ETipoToken.ERROR;
              }
              break;
           case 1:
	            c = nextChar();
	            if (isLetter(c) || isDigit(c)){
	               s = 1;
	            }
	            else if (isWhitespace(c)){
	            	tokenToReturn = ETipoToken.IDENTIFICADOR;
	            }
	            else if (isOperator(c) || isPunctuation(c)){
	               retroceder();
	               tokenToReturn = ETipoToken.IDENTIFICADOR;
	            }
	            else {
	            	tokenToReturn = ETipoToken.ERROR;
	            }
	            break;
           case 2:
        	   	tokenToReturn  = ETipoToken.OPERADOR;
           case 3:
        	   //trata casos de :
        	   c = nextChar();
        	   if (isOperator(c))
        		   tokenToReturn = ETipoToken.OPERADOR;
        	   if (isWhitespace(c)) {
        		   retroceder();
        		   tokenToReturn=ETipoToken.PONTUACAO;
        	   }
        	   else
        		   tokenToReturn = ETipoToken.ERROR;
        	   break;
           case 4:
        	   c = nextChar();
	            if (isDigit(c)){
	               s = 4;
	            }
	            else if (isWhitespace(c)){
	            	tokenToReturn = ETipoToken.INTEIRO;
	            }
	            else if (isOperator(c) || isPunctuation(c)){
	               retroceder();
	               tokenToReturn = ETipoToken.INTEIRO;
	            }
	            else {
	            	tokenToReturn = ETipoToken.ERROR;
	            }
	            break;
        	   
         }
       }
       
       //Se o token for um erro, pelo menos vamos pegar o token ate o final.
       if (tokenToReturn == ETipoToken.ERROR)
       {
    	   while(true)
    	   {
    		   char c = nextChar();
    		   if(isWhitespace(c) || isPunctuation(c) || isOperator(c))
    			   break;
    	   }
    	   retroceder();
       }
       
       //Essa parte 'recorta' o valor do token.
       int endingPoint = pos;
       String tokenValue = new String(content);       
       //a funcao trim() remove espaco em branco, newline, essas coisa chata.
       tokenValue = tokenValue.substring(startingPoint, endingPoint).trim(); 
       
       //Se o token for um identificador, vamos verificar se nao esta reservado
       if(tokenToReturn == ETipoToken.IDENTIFICADOR)
       {
    	   if(Arrays.asList(STRINGSRESERVADAS).contains(tokenValue))
    			   tokenToReturn=ETipoToken.RESERVED;
       }
       
       //Retorna o objeto TOKEN
       return new Token(tokenToReturn, tokenValue);
    
    }
}