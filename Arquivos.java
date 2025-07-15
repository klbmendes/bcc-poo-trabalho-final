/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalhofinal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Arquivos {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat df = new DecimalFormat("#0.00"); // Formato com duas casas decimais

   
    /**
     * Salva a lista de lançamentos financeiros em um arquivo CSV.
     * 
     * Cada linha representa um lançamento, com os campos: tipo, categoria, data, valor e status de lançamento.
     * 
     * @param lista Lista de lançamentos a serem salvos.
     * @param caminho Caminho completo (com nome e extensão) do arquivo CSV para salvar os dados.
     */
    public static void salvarLancamentosCSV(ArrayList<Lancamento> lista, String caminho) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(caminho));
            writer.println("TIPO;CATEGORIA;DATA;VALOR;LANCADO");

            for (Lancamento lancamento : lista) {
                String tipo;
                String categoria;
                if (lancamento instanceof Receita) {
                    tipo = "RECEITA";
                    categoria = ((Receita) lancamento).getCategoriaR().name();
                } else {
                    tipo = "DESPESA";
                    categoria = ((Despesas) lancamento).getCategoriaD().name();
                }

                String dataText = lancamento.getData().format(FORMATO_DATA);
                String valorText = df.format(lancamento.getValor());
                boolean lancado = lancamento.getLancado();

                String linha = tipo + ";" + categoria + ";" + dataText + ";" + valorText + ";" + lancado;
                writer.println(linha);
            }

        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar dados! " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Carrega uma lista de lançamentos financeiros a partir de um arquivo CSV.
     * 
     * O arquivo deve conter o cabeçalho e linhas no formato esperado. Linhas inválidas são ignoradas.
     * 
     * @param caminho Caminho completo (com nome e extensão) do arquivo CSV a ser lido.
     * @return Lista de lançamentos carregados do arquivo CSV.
     */
    public static ArrayList<Lancamento> carregarLancamentosCSV(String caminho) {
        ArrayList<Lancamento> lancamentos = new ArrayList<>();
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            return lancamentos;
        }

        Scanner teclado = null;
        try {
            teclado = new Scanner(arquivo);

            // Pula o cabeçalho
            if (teclado.hasNextLine()) {
                teclado.nextLine();
            }

            while (teclado.hasNextLine()) {
                String linha = teclado.nextLine();
                String[] partes = linha.split(";");

                if (partes.length < 5) {
                    System.err.println("Linha inválida: " + linha);
                    continue;
                }
                String tipo = partes[0].trim();
                String categoria = partes[1].trim();
                LocalDate data = LocalDate.parse(partes[2].trim(), FORMATO_DATA);
                double valor = Double.parseDouble(partes[3].trim().replace(",", "."));
                boolean lancado = Boolean.parseBoolean(partes[4].trim());

                if (tipo.equalsIgnoreCase("RECEITA")) {
                    CategoriaReceita categoriaR = CategoriaReceita.valueOf(categoria);
                    Receita receita = new Receita(data, valor, categoriaR);
                    receita.setLancado(lancado);
                    lancamentos.add(receita);
                } else {
                    CategoriaDespesas categoriaD = CategoriaDespesas.valueOf(categoria);
                    Despesas despesa = new Despesas(data, valor, categoriaD);
                    despesa.setLancado(lancado);
                    lancamentos.add(despesa);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        } finally {
            if (teclado != null) {
                teclado.close();
            }
        }

        return lancamentos;
    }

}
