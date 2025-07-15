/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalhofinal;

import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class Receita extends Lancamento {

    private CategoriaReceita categoriaR;

    public CategoriaReceita getCategoriaReceita() {
        return categoriaR;
    }

    /**
     * Construtor padrão da classe Receita.
     *
     * @param data Data da receita
     * @param valor Valor da receita
     * @param lancado true da receita
     * @param categoriaR Categoria da receita
     */
    public Receita(LocalDate data, double valor, boolean lancado, CategoriaReceita categoriaR) {
        super(data, valor, true);
        setCategoriaR(categoriaR);
    }

    /**
     * Construtor da classe Receita com parâmetros.
     *
     * @param data Data da receita
     * @param valor Valor da receita
     * @param categoriaR Categoria da receita
     */
    public Receita(LocalDate data, double valor, CategoriaReceita categoriaR) {
        super(data, valor, true); // Por padrão, não lançado
        setCategoriaR(categoriaR);
    }

    /**
     * Retorna a categoria da receita.
     *
     * @return Categoria da receita
     */
    public CategoriaReceita getCategoriaR() {
        return categoriaR;
    }

    /**
     * Define a categoria da receita.
     * Validacao da categoria
     * @param categoriaR Categoria da receita
     */
    public void setCategoriaR(CategoriaReceita categoriaR) {
         if (categoriaR == null) {
            throw new IllegalArgumentException("Categoria da despesa não pode ser nula!");
        }
        this.categoriaR = categoriaR;
    }

    @Override
    public double impactoNoSaldo() {
        return getValor();
    }

    @Override
    public String toString() {
        return "Data: " + this.getData() + ", Valor: " + this.getValor() + ", Categoria: " + this.getCategoriaR();

    }
}
