/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalhofinal;

import java.text.DecimalFormat;
import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class LancamentoArquivo {

    private final String tipo;
    private final String categoria;
    private final LocalDate data;
    private final double valor;
    private final boolean lancado;

    /**
     * Construtor da classe LancamentoArquivo.
     *
     * @param tipo se é despesa ou receita
     * @param categoria Categoria do lançamento
     * @param data Data do lançamento
     * @param valor Valor do lançamento
     * @param lancado indica que esta lancado
     */
    public LancamentoArquivo(String tipo, String categoria, LocalDate data, double valor, boolean lancado) {
        this.tipo = tipo.toUpperCase();
        this.categoria = categoria.toUpperCase();
        this.data = data;
        this.valor = valor;
        this.lancado = true;
    }

    /**
     * Obtém o tipo do lançamento.
     *
     * @return Tipo do lançamento (RECEITA ou DESPESA)
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtém a categoria do lançamento.
     *
     * @return Categoria do lançamento
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Obtém a data do lançamento.
     *
     * @return Data do lançamento
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Obtém o valor do lançamento.
     *
     * @return Valor do lançamento
     */
    public double getValor() {
        return valor;
    }

    /**
     * Indica se o lançamento está marcado como lançado.
     *
     * @return true se lançado, false caso contrário
     */
    public boolean isLancado() {
        return lancado;
    }

    /**
     * Gera uma linha CSV correspondente ao lançamento no formato: TIPO;CATEGORIA;DATA;VALOR;LANCADO
     * formatado com duas casas decimais.
     *
     * @return String formatada para ser escrita em arquivo CSV
     */
    public String toCSV() {
        DecimalFormat df = new DecimalFormat("0.00");
        return tipo + ";" + categoria + ";" + data.toString() + ";" + df.format(valor) + ";" + lancado;
    }

    /**
     * Constrói um objeto LancamentoArquivo a partir de uma linha CSV.
     *
     *
     * @param linhaCSV Linha do arquivo CSV
     * @return Objeto LancamentoArquivo correspondente
     */
    public static LancamentoArquivo fromCSV(String linhaCSV) {
        String[] partes = linhaCSV.split(";");
        if (partes.length < 5) {
            System.out.println("Linha CSV inválida: " + linhaCSV);
            return null;
        }

        String tipo = partes[0].trim();
        String categoria = partes[1].trim();
        LocalDate data = LocalDate.parse(partes[2].trim());
        double valor = Double.parseDouble(partes[3].trim().replace(",", "."));
        boolean lancado = Boolean.parseBoolean(partes[4].trim());

        return new LancamentoArquivo(tipo, categoria, data, valor, lancado);
    }

    /**
     * Converte este objeto auxiliar LancamentoArquivo para o objeto original
     *
     * @return Objeto Lancamento correspondente ao conteúdo deste objeto
     * @throws RuntimeException se o tipo for inválido (não RECEITA nem DESPESA)
     */
    public Lancamento toLancamentoOriginal() {
        if (tipo.equalsIgnoreCase("RECEITA")) {
            return new Receita(data, valor, CategoriaReceita.valueOf(categoria));
        } else if (tipo.equalsIgnoreCase("DESPESA")) {
            return new Despesas(data, valor, CategoriaDespesas.valueOf(categoria));
        } else {
            throw new RuntimeException("Tipo inválido: " + tipo);
        }
    }
}
