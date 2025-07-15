/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalhofinal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Gerenciamento {

    private ArrayList<Lancamento> lancamentos;
    private final String ARQUIVO_DADOS = "data.csv";

    /**
     * Construtor que inicializa a lista de lançamentos e carrega os dados do arquivo CSV
     * 
     */
    public Gerenciamento() {
        this.lancamentos = new ArrayList<>();
    }

    /**
     * Adiciona um novo lançamento à lista
     *
     * @param lancamento Receita ou Despesa a ser adicionada
     */
    public void adicionarLancamento(Lancamento lancamento) {
        if (lancamento != null) {
            this.lancamentos.add(lancamento);
            this.salvarDados(); // Salva automaticamente no arquivo
        }
    }

    /**
     * @return Lista com todos os lançamentos
     */
    public ArrayList<Lancamento> getTodosLancamentos() {
        return new ArrayList<>(this.lancamentos); // Retorna cópia para proteger o original
    }

    /**
     * Calcula o total de todas as receitas lançadas
     *
     * @return Soma dos valores das receitas
     */
    public double calcularTotalReceitas() {
        double total = 0;

        for (Lancamento lancamento : this.lancamentos) {
            if (lancamento instanceof Receita && lancamento.getLancado()) {
                total += lancamento.getValor();
                System.out.println("Receita encontrada: " + lancamento.getValor());
            }
        }

        System.out.println("Total de Receitas: " + total);
        return total;
    }

    /**
     * Calcula o total de todas as despesas lançadas
     *
     * @return Soma dos valores das despesas
     */
    public double calcularTotalDespesas() {
        double total = 0;

        for (Lancamento lancamento : this.lancamentos) {
            if (lancamento instanceof Despesas && lancamento.getLancado()) {
                total += lancamento.getValor();
            }
        }

        return total;
    }

    /**
     * Calcula o saldo atual até a data atual / receitas - despesas
     *
     * @return Saldo disponível
     */
    public double calcularSaldoAtual() {
        double saldo = 0;
        LocalDate hoje = LocalDate.now();

        for (Lancamento lancamento : this.lancamentos) {
            if (lancamento.getLancado() && !lancamento.getData().isAfter(hoje)) {
                saldo += lancamento.impactoNoSaldo();
            }
        }

        return saldo;
    }

    /**
     * Calcula o saldo total das receitas.
     * Considera todos os lançamentos marcados como true, independente da data
     *
     * @return Saldo total projetado
     */
    public double calcularSaldoTotal() {
        double saldo = 0;

        for (Lancamento lancamento : this.lancamentos) {
            if (lancamento.getLancado()) {
                saldo += lancamento.impactoNoSaldo();
            }
        }

        return saldo;
    }

    /**
     * Ordena os lançamentos por data (do mais antigo para o mais recente)
     *
     * @return Lista ordenada por data
     */
    public ArrayList<Lancamento> getLancamentosPorData() {
        ArrayList<Lancamento> ordenados = new ArrayList<>(this.lancamentos);

        for (int i = 0; i < ordenados.size() - 1; i++) {
            for (int j = 0; j < ordenados.size() - i - 1; j++) {
                if (ordenados.get(j).getData().isAfter(ordenados.get(j + 1).getData())) {
                    Lancamento temp = ordenados.get(j);
                    ordenados.set(j, ordenados.get(j + 1));
                    ordenados.set(j + 1, temp);
                }
            }
        }

        return ordenados;
    }

    /**
     * Carrega os dados do arquivo CSV
     */
    public void carregarDados() {
        this.lancamentos = Arquivos.carregarLancamentosCSV(ARQUIVO_DADOS);

    }

    /**
     * Salva os dados no arquivo CSV
     */
    public void salvarDados() {
        Arquivos.salvarLancamentosCSV(this.lancamentos, ARQUIVO_DADOS);
    }

    public static class Validacoes {

        /**
         * Valida uma data no formato dd/MM/yyyy, padrão br.
         *
         * @param dataTexto Data em formato de texto
         * @return Objeto LocalDate com a data validada
         * @throws IllegalArgumentException Se a data for inválida
         */
        public static LocalDate validarData(String dataTexto) throws IllegalArgumentException {
            try {
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate data = LocalDate.parse(dataTexto, formatador);

                if (data.isAfter(LocalDate.now())) {
                    throw new IllegalArgumentException("A data não pode ser no futuro!");
                }

                return data;
            } catch (Exception e) {
                throw new IllegalArgumentException("Data inválida! Use o formato DD/MM/AAAA");
            }
        }

        /**
         * Valida o valor inserido.
         *
         * @param valor Valor a ser validado
         * @throws IllegalArgumentException Se o valor for inválido
         */
        public static void validarValor(double valor) throws IllegalArgumentException {
            if (valor <= 0) {
                throw new IllegalArgumentException("O valor deve ser positivo!");
            }
        }
    }
}
