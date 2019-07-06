package ufabc_bcc_compiladores.compilador_lexico;
public class Main {
  public static void main(String[] args) {
      AnalisadorLexico l;
      l = new AnalisadorLexico("prog.in");
      
      String token = l.nextToken().getText();
      
      while(token != null)
      System.out.println(token);
      token = l.nextToken().getText();

  }
}