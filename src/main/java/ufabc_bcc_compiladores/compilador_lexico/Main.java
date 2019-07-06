package ufabc_bcc_compiladores.compilador_lexico;

class Main {
  public static void main(String[] args) {
      AnalisadorLexico l;
      l = new AnalisadorLexico(new String("abcd +      ab123 ab@ "));
      System.out.println(l.nextToken());
      System.out.println(l.nextToken());
      System.out.println(l.nextToken());
      //System.out.println(l.nextToken());

  }
}