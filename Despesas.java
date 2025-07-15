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
public class Despesas extends Lancamento {

    private CategoriaDespesas categoriaD;

    /**
     * Construtor completo da classe Despesas.
     *
     * @param data Data da despesa; não pode ser nula.
     * @param valor Valor da despesa; deve ser maior que zero.
     * @param lancado TRUE.
     * @param categoriaD Categoria da despesa; não pode ser nula.
     */
    public Despesas(LocalDate data, double valor, boolean lancado, CategoriaDespesas categoriaD) {
        super(data, valor, true);
        setCategoriaD(categoriaD);
    }

    /**
     * Construtor da classe Despesas sem o parâmetro 'lancado', que assume como
     * true.
     *
     * @param data Data da despesa; não pode ser nula.
     * @param valor Valor da despesa; deve ser maior que zero.
     * @param categoriaD Categoria da despesa; não pode ser nula.
     */
    public Despesas(LocalDate data, double valor, CategoriaDespesas categoriaD) {
        super(data, valor, true);
        setCategoriaD(categoriaD);
    }

    /**
     * Retorna a categoria da despesa.
     *
     * @return Categoria da despesa.
     */
    public CategoriaDespesas getCategoriaD() {
        return categoriaD;
    }

    /**
     * Define a categoria da despesa.
     * Validacao da categoria.
     * @param categoriaD Categoria da despesa; não pode ser nula.
     */
    public void setCategoriaD(CategoriaDespesas categoriaD) {
        if (categoriaD == null) {
            throw new IllegalArgumentException("Categoria da despesa não pode ser nula!");
        }
        this.categoriaD = categoriaD;
    }

    /**
     * Calcula o impacto desta despesa no saldo financeiro
     *
     * @return Valor negativo correspondente à despesa.
     */
    @Override
    public double impactoNoSaldo() {
        return -getValor();
    }

    /**
     * Representação em texto da despesa, contendo data, valor e categoria.
     *
     * @return String com informações formatadas da despesa.
     */
    @Override
    public String toString() {
        return "Data: " + this.getData() + ", Valor: " + this.getValor() + ", Categoria: " + this.getCategoriaD();
    }

}
