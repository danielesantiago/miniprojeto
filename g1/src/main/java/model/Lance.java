package model;

public class Lance {
    public String nome;
    public String produto;
    public float valor_lance;
    

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setProduto(String produto){
        this.produto = produto;
    }

    public void setValor_lance(float valor_lance){
        this.valor_lance = valor_lance;
    }

    public String getNome(){
        return this.nome;
    }

    public String getProduto(){
        return this.produto;
    }

    public float getValor_lance(){
        return this.valor_lance;
    }
}
