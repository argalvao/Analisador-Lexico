package model;

public class Token {
    private String tipo;
    private String lexema;
    private int linha;
    
    public Token(String tipo, String lexema, int linha) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.linha = linha;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
    
    public void setLinha(int linha) {
        this.linha = linha;
    }
    
    public String getTipo() {
        return this.tipo;
    }

    public String getLexema() {
        return this.lexema;
    }

    public int getLinha() {
        return this.linha;
    }

    @Override
    public String toString() {
        //if (tipo == String.ERRO_SINT)
           //return (this.linha + " " + this.lexema + " " + this.tipo);
        //else
            return ("Linha:\t" + (this.linha+1) + "\t" + "| Lexema:\t" + this.lexema + "\t\t\t" + "|\t"+this.tipo);
    }
}