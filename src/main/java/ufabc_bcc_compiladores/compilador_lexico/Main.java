package ufabc_bcc_compiladores.compilador_lexico;

public class Main {
  public static void main(String[] args) {
      AnalisadorLexico l;
      l = new AnalisadorLexico("prog.in");
      
      while(true) 
      {
    	  try
    	  {
    		  Token token = l.nextToken();
    		  System.out.println(token.getId().toString() + " - " + token.getText());
    	  }
    	  catch(Exception e)
    	  {
    		  break;
    	  }
      }

  }
}