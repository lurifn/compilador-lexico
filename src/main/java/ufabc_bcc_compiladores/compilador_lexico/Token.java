package ufabc_bcc_compiladores.compilador_lexico;

import ufabc_bcc_compiladores.compilador_lexico.enums.ETipoToken;

public class Token {

    public static final int ID             = 0;
    public static final int INT_NUMBER     = 1;
    public static final int FLOAT_NUMBER   = 2;
    public static final int OPERATOR       = 3;
    public static final int RESERVERD_WORD = 4;
    public static final int PONTUACTION    = 5;
    
    private ETipoToken id;
    private String text;

    public Token() {
        id = ETipoToken.CONTINUA;
        text = "";
    }

    public Token(ETipoToken tokenToReturn, String text) {
        this.id = tokenToReturn;
        this.text = text;
    }

    public ETipoToken getId() {
        return this.id;
    }

    public void setId(ETipoToken id) {
        this.id = id;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getText() {
        return this.text;
    }
    
    public String toString(){
        return "<"+id+","+text+">";
    }
}