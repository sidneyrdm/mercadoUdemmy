package view;

import helper.Utils;
import jdk.jshell.execution.Util;
import model.Produto;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static Scanner teclado = new Scanner(System.in);
    private static ArrayList<Produto> produtos;
    private static Map<Produto, Integer> carrinho;

    public static void main(String[] args) {

        produtos = new ArrayList<>();
        carrinho = new HashMap<>();
        Main.menu();
    }

    private static void menu(){

        System.out.println("==================================");
        System.out.println("========== Bem-vindo(a) ==========");
        System.out.println("========== Tech Shop =============");
        System.out.println("==================================");

        System.out.println("Selecione uma das opções.: ");
        System.out.println("1 - Cadastrar produto");
        System.out.println("2 - Listar produtos");
        System.out.println("3 - Adicionar produtos ao carrinho");
        System.out.println("4 - Vizualizar carrinho");
        System.out.println("5 - Limpar carrinho");
        System.out.println("6 - Sair do sistema");

    int opcao = 0;

    try{
        opcao = Integer.parseInt(Main.teclado.nextLine());
    } catch (InputMismatchException e){
        Main.menu();
    } catch (NumberFormatException e){
        Main.menu();
    }

    switch (opcao){
        case 1: Main.cadastrarProduto();
            break;
        case 2: Main.listarProdutos();
            break;
        case 3: Main.comprarProduto();
            break;
        case 4: Main.vizualizarCarrinho();
            break;
        case 5: Main.limparCarrinho();
            break;
        case 6:
            System.out.println("Volte Sempre!");
            Utils.pausar(2);
            System.exit(0);
        default:
            System.out.println("Opção inválida.");
            Utils.pausar(2);
            Main.menu();
            break;
    }

    }

    private static void limparCarrinho() {
        if(carrinho.isEmpty()){
            System.out.println("o carrinho já está vazio...");
            Utils.pausar(2);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
            menu();
        }
        System.out.println("limpando carrinho...");
        Utils.pausar(3);
        carrinho.clear();
        System.out.println("carrinho limpo, pode continuar suas compras.");
        Utils.pausar(2);
        menu();
    }

    private static void cadastrarProduto(){
        System.out.println("Cadastro de produto");
        System.out.println("==================================");

        System.out.println("Digite o nome do produto.: ");
        String nome = Main.teclado.nextLine();

        System.out.println("Digite o preço do produto.: ");
        Double preco = Main.teclado.nextDouble();

        Produto produto = new Produto(nome, preco);
        Main.produtos.add(produto);

        System.out.println("O produto "+produto.getNome()+" foi cadastrado com sucesso.");
        Utils.pausar(2);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        Main.menu();
    }

    private static void listarProdutos(){
        if(Main.produtos.isEmpty()){
            System.out.println("Nenhum produto foi encontrado.");
            Utils.pausar(2);
            Main.menu();
        }
        System.out.println("Listando produtos");
        System.out.println("==================================");
        for(Produto produto : Main.produtos){
            System.out.println(produto);
            System.out.println("==================================");
        }

        Utils.pausar(2);
        Main.menu();
    }

    private static void comprarProduto(){
        if(Main.produtos.isEmpty()){
            System.out.println("Nenhum produto foi encontrado.");
            Utils.pausar(2);
            Main.menu();
        }
        System.out.println("Comprando produtos");
        System.out.println("==================================");
        System.out.println("informe o código do produto que deseja adicionar ao cararinho.:");
        System.out.println("========== Produtos disponíveis===");
        for (Produto produto : produtos){
            System.out.println(produto);
            System.out.println("==================================");
        }

        int codigo = Integer.parseInt(Main.teclado.nextLine());
        boolean tem = false;


        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                int quant = 0;
                try {
                    quant = Main.carrinho.get(produto);
                    //Já tem o produto no carrinho, atualiza a quantidade
                    Main.carrinho.put(produto, (quant + 1));
                } catch (NullPointerException e) {
                    //Primeiro produto no carinho
                    Main.carrinho.put(produto, 1);
                }

                System.out.println("O produto " + produto.getNome() + " foi adicionado ao carrinho.");
                tem = true;
            }
        }

            if(tem){
                System.out.println("Deseja adicionar outros produtos ao carrinho? ");
                System.out.println("informe 1 - para SIM | 0 - para NÃO ");
                int op = Integer.parseInt(teclado.nextLine());

                if(op == 1){
                    Main.comprarProduto();
                }else{
                    System.out.println("ok, vamos ver o seu carrinho de compras");
                    Utils.pausar(2);
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
                    Main.vizualizarCarrinho();
                }

            } else{
                System.out.println("não foi encontrado o produto com o código "+ codigo);
                Utils.pausar(2);
                Main.menu();
            }

    }

    private static void fecharPedido() {
        Double valorTotal = 0.0;
        System.out.println("Produtos no carrinho .:");
        System.out.println("==================================");

        for (Produto produto : carrinho.keySet()){
            valorTotal += produto.getPreco() * carrinho.get(produto);
            System.out.println(produto);
            System.out.println("Quantidade.: "+carrinho.get(produto));
            System.out.println("==================================");
        }
        System.out.println("seu pedido ficou no valor de .: "+Utils.doubleParaString(valorTotal));
        carrinho.clear();
        System.out.println("obrigado pela preferência.");
        Utils.pausar(5);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        menu();
    }

    private static void vizualizarCarrinho(){
        if(Main.carrinho.isEmpty()){
            System.out.println("O carrinho está vazio.");
            Utils.pausar(2);
            Main.menu();
        }
        Double valorTotal = 0.0;
        System.out.println("Produtos no carrinho .:");
        System.out.println("==================================");
        for(Produto produto : Main.carrinho.keySet()){
            System.out.println("Produto.: " + produto + "\nQuantidade.: "+Main.carrinho.get(produto));
            System.out.println("==================================");
            valorTotal += produto.getPreco() * carrinho.get(produto);
        }
        System.out.println("sua fatura é .: "+Utils.doubleParaString(valorTotal));
        Utils.pausar(2);
        System.out.println("deseja fechar o pedido?");
        System.out.println("informe 1 - para SIM | 0 - para NÃO ");
        int op = Integer.parseInt(teclado.nextLine());

        if(op == 1){
            System.out.println("Aguarde enquanto fechamos o seu pedido...");
            Utils.pausar(2);
            Main.fecharPedido();
        }else{
            System.out.println("ok, vamos voltar ao menu principal");
            Utils.pausar(2);
            Main.menu();
        }
    }

}