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
public abstract class Lancamento {

    private LocalDate data;
    private double valor;
    private boolean lancado;
   
    /**
     * Construtor padrão vazio.
     */
    public Lancamento() {
    }

    /**
     * Construtor que inicializa os atributos.
     *
     * @param data Data do lançamento financeiro; não deve ser nula.
     * @param valor Valor do lançamento; deve ser maior que zero.
     * @param lancado Indica se o lançamento já foi realizado.
     */
    public Lancamento(LocalDate data, double valor, boolean lancado) {
        setData(data);
        setValor(valor);
        this.lancado = lancado;
    }

    /**
     * Método abstrato que deve ser implementado nas subclasses para calcular o
     * impacto desse lançamento no saldo financeiro.
     *
     * @return Valor do impacto no saldo (positivo para receita, negativo para
     * despesa).
     */
    public abstract double impactoNoSaldo();

    /**
     * Retorna a data do lançamento.
     *
     * @return Data do lançamento.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data do lançamento.
     *
     * @param data Data a ser definida; não pode ser nula.
     * @throws IllegalArgumentException caso a data seja nula.
     */
    public void setData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("A data não pode ser nula.");
        }
        if (data.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("A data não pode ser no futuro!");
    }
        this.data = data;
    }

    /**
     * Retorna o valor do lançamento.
     *
     * @return Valor do lançamento.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor do lançamento.
     *
     * @param valor Valor a ser definido; deve ser maior que zero.
     * @throws IllegalArgumentException caso o valor seja menor ou igual a zero.
     */
    public void setValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor para o campo valor está incorreto");
        }
        this.valor = valor;
    }

    /**
     * Indica se o lançamento foi efetivado.
     *
     * @return true se já foi lançado;
     */
    public boolean getLancado() {
        return lancado;
    }

    /**
     * Define o status de lançamento.
     *
     * @param lancado true.
     * @throws IllegalStateException caso tente alterar de true para false.
     */
    public void setLancado(boolean lancado) {
        if (this.lancado && !lancado) {
            throw new IllegalStateException("Valor inserido inválido");
        }
        this.lancado = lancado;
    }
}
