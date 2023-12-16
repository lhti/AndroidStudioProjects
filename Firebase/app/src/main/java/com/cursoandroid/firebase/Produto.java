package com.cursoandroid.firebase;

/**
 * Created by lhgyn on 10/03/2018.
 */

public class Produto {

    public Produto() {
    }

    private String descricao;
    private String cor;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    private String fabricante;

}
