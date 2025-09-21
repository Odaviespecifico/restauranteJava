import java.util.*;
public class Restaurante {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<ArrayList<String>> pedidos = new ArrayList<ArrayList<String>>();
    static Menu cardapio = new Menu();
    static Estoque estoque = new Estoque();
    static Clientes clientes = new Clientes();
    public static void main(String[] args) {
        while (true) {
            System.out.println("======== Menu inicial ========");

            System.out.println("Qual opção deseja utilizar?");
            System.out.println("1 - Registrar pedidos");
            System.out.println("2 - Relatório");
            System.out.println("0 - Sair");
            int funcaoPrincipal = Integer.parseInt(scanner.nextLine());

            if (funcaoPrincipal == 0) {
                break;
            }

            else if (funcaoPrincipal == 1) {
                int metodo;
                System.out.println("1 -> For loop");
                System.out.println("2 -> While");
                System.out.println("3 -> Do While");
                System.out.print("Digite o método escolhido: ");
                metodo = Integer.parseInt(scanner.nextLine());

                if (metodo == 1) {
                    for (int i = 0; i < 5; i++) {
                        pedidos.add(registrarPedido());
                    }
                }
                if (metodo == 2) {
                    boolean registrandoPedidos = true;
                    while (registrandoPedidos) {
                        pedidos.add(registrarPedido());
                        System.out.print("Deseja registrar um novo pedido (S/N)? ");
                        String novoCliente = scanner.nextLine().toUpperCase();
                        if (!Objects.equals(novoCliente, "S")) {
                            registrandoPedidos = false;
                        }
                    }
                }
                if (metodo == 3) {
                    boolean registrandoPedidos = true;
                    do {
                        pedidos.add(registrarPedido());
                        System.out.print("Deseja registrar um novo pedido (S/N)? ");
                        String novoCliente = scanner.nextLine().toUpperCase();
                        if (!Objects.equals(novoCliente, "S")) {
                            registrandoPedidos = false;
                        }
                    } while (registrandoPedidos);
                }
            }

            else if (funcaoPrincipal == 2) {
                displayAllOrders(pedidos);
            }

            else {
                System.out.println("Opção inválida");
            }

        }
    }

    /**
     * Gets the items using the Do While Loop
     * @return returns the Order made by the client
     */
    private static ArrayList<String> registrarPedido() {
        clientes.listClients();
        System.out.print("Digite o código do cliente (Digite um novo para criar [O código é numérico]): ");
        int codCliente = Integer.parseInt(scanner.nextLine());
        if (!clientes.exists(codCliente)) {
            System.out.println("Adicionado o cliente de código: " + codCliente);
            clientes.put(codCliente, 1);
        }
        else {
            clientes.put(codCliente,clientes.get(codCliente) + 1);
            System.out.println("Novo pedido do cliente: " + codCliente);
        }

        int opcao;
        var escolhas = new ArrayList<String>();
        System.out.println("Olá! Somos a Café com código para ser feliz! Vou te passar o nosso cardápio e você seleciona entre 0 a 4 o que desejar.");
        do {
            cardapio.printMenu();
            opcao = Integer.parseInt(scanner.nextLine());
            getOrderItem(opcao, escolhas);
        } while (opcao != 0);
        System.out.println("========= Nota fiscal =========");
        cardapio.showInvoice(escolhas, codCliente);
        return escolhas;
    }

    /**
     * Helper function to switch all the elements items
     *
     * @param opcao    The value given my the input of the user
     * @param escolhas The array that represents the choices of the client
     */
    private static void getOrderItem(int opcao, ArrayList<String> escolhas) {
        String item;
        switch (opcao) {
            case 1:
                item = cardapio.getNameByIndex(0);
                if (estoque.hasAvailable(item)) {
                    escolhas.add(item);
                    estoque.removeFromItem(item,1);
                    System.out.println("Adicionado " + item);
                }
                else {
                    System.out.println("Item não disponível");
                }
                break;
            case 2:
                item = cardapio.getNameByIndex(1);
                if (estoque.hasAvailable(item)) {
                    escolhas.add(item);
                    estoque.removeFromItem(item,1);
                    System.out.println("Adicionado " + item);
                }
                else {
                    System.out.println("Item não disponível");
                }
                break;
            case 3:
                item = cardapio.getNameByIndex(2);
                if (estoque.hasAvailable(item)) {
                    escolhas.add(item);
                    estoque.removeFromItem(item,1);
                    System.out.println("Adicionado " + item);
                }
                else {
                    System.out.println("Item não disponível");
                }
                break;
            case 4:
                item = cardapio.getNameByIndex(3);
                if (estoque.hasAvailable(item)) {
                    escolhas.add(item);
                    estoque.removeFromItem(item,1);
                    System.out.println("Adicionado " + item);
                }
                else {
                    System.out.println("Item não disponível");
                }
                break;
            case 0:
                System.out.println("Finalizando pedido...");
                break;
            default:
                System.out.println("Opção invalida! ");
        }
    }

    /**
     * Shows all the orders of the clients
     * @param pedidos the Object with all orders
     */
    public static void displayAllOrders(ArrayList<ArrayList<String>> pedidos) {
        double valorMaiorPedido = 0;
        int maiorPedido = 0;
        double valorTotal = 0;
        System.out.println("\n=========== Pedidos ===========");
        for (int i = 0; i < pedidos.size(); i++) {
            double desconto;
            if (pedidos.size() >= 3 && pedidos.size() <=4) {
                desconto = 0.05;
            } else if (pedidos.size() >= 5) {
                desconto = 0.1;
            } else {
                desconto = 0;}

            var totalPedido = cardapio.calculateTotal(pedidos.get(i),desconto);
            System.out.print("Pedido do cliente " + (i+1) + ":");
            if (desconto > 0) {
                System.out.printf("Desconto de %.2f%% aplicado",desconto*100);
            }
            else {System.out.println();}
            cardapio.showInvoice(pedidos.get(i),0);

            if ((totalPedido > valorMaiorPedido)) {
                valorMaiorPedido = totalPedido;
                maiorPedido = i+1;
            }

            valorTotal += totalPedido;
            System.out.println();
        }

        System.out.println("\n========== Relatório ==========");
        System.out.printf("O maior pedido foi do cliente %s com valor total de R$%.2f \n",maiorPedido,valorMaiorPedido);
        System.out.printf("O valor total foi de R$%.2f \n",valorTotal);
        System.out.printf("A média por cliente foi R$%.2f \n",valorTotal/pedidos.size());
    }

    static class Menu extends LinkedHashMap<String, Float> {
        Menu() {
            super(Map.of(
                    "Café simples", 4.50f,
                    "Capuccino", 6.00f,
                    "Cha Organico", 3.50f,
                    "Bolo Artesanal", 7.00f));
        }

        /**
         * Shows the item formated with its price by a given index.
         *
         * @param name  The name of the item
         * @param index The index to be displayed next to the item can me ommited
         * @return A formated string with "1 - Café --------- R$ 30.00
         */
        public String getFormattedItemPrice(String name, int index) {
            float price = this.get(name);
            String firstPart;
            if (index == -1) {
                firstPart = name;
            } else {
                firstPart = index + 1 + " - " + name;
            }
            String paddingElement = "-";
            String padding = paddingElement.repeat(20 - name.length());
            return (firstPart + " " + padding + " R$" + price);
        }

        public String getFormattedItemPrice(String name) {
            return (getFormattedItemPrice(name, 0));
        }

        /**
         * Shows all items formated in a given Order
         *
         * @param pedido The order (An array composed of Strings)
         */
        public void showInvoice(ArrayList<String> pedido, int codCliente) {
            for (int i = 0; i < pedido.size(); i++) {
                System.out.println(getFormattedItemPrice(pedido.get(i), i));
            }
            double discount = 0;
            double total = calculateTotal(pedido, 0);
            if (clientes.exists(codCliente)) {
                discount = 0.15;
                System.out.println("Bem vindo cliente " + codCliente +". Aproveite seu desconto");
                System.out.printf("Total com desconto de %.2f%% -- R$%.2f\n",discount * 100, total * 1-discount);
            }
            else if (pedido.size() >= 3 && pedido.size() < 5) {
                discount = 0.05;
                System.out.printf("Total com desconto de %.2f%% -- R$%.2f\n",discount * 100, total * 1-discount);
            }
            else if (pedido.size() >= 5) {
                discount = 0.1;
                System.out.printf("Total com desconto de %.2f%% -- R$%.2f\n",discount * 100, total * 1-discount);
            }
            else {
                System.out.printf("Total sem desconto ------- R$%.2f\n",total);
            }
        }

        /**
         * Shows the menu with all prices
         */
        public void printMenu() {
            System.out.println("0 - Finalizar pedido.");
            for (int i = 0; i < this.size(); i++) {
                System.out.println(this.getFormattedItemPrice(this.getNameByIndex(i), i));
            }
            System.out.print("Escolha sua opção: ");
        }

        /**
         * @param pos The index
         * @return A string with the name like "Café artesanal"
         */
        public String getNameByIndex(int pos) {
            return this.keySet().toArray()[pos].toString();
        }

        /**
         * Calculates the total of a given Order
         *
         * @param pedido The Order value
         * @param discount The value of the discount (From 0 to 1)
         * @return The total value as a double
         */
        public double calculateTotal(ArrayList<String> pedido, double discount) {
            float total = 0;
            for (String item : pedido) {
                total += this.get(item);
            }
            return total * (1-discount);
        }
    }

    static class Estoque extends LinkedHashMap<String,Integer> {
        static ArrayList<String> items = new ArrayList<String>(List.of("Café simples", "Capuccino", "Cha Organico", "Bolo Artesanal"));
        static HashMap<String,Integer> itensEstoque = new HashMap<String,Integer>();
        Estoque() {
            System.out.println("====== Definir estoque ======");
            items.forEach(item -> {
                int quantidade = 0;
                while (true) {
                    try {;
                        System.out.println("Quanto você tem do item: " + item);
                        quantidade = Integer.parseInt(scanner.nextLine());
                        if (quantidade < 0) {
                            System.out.println("O valor não pode ser negativo");
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Valor inválido. Digite novamente");
                    }
                }
                itensEstoque.put(item,quantidade);
            });
        }

        public void removeFromItem(String name, int ammount) {
            if (hasAvailable(name)) {
                itensEstoque.compute(name, (k, quantidadeAtual) -> (quantidadeAtual - ammount));
            }
        }

        public boolean hasAvailable(String name) {
            return (itensEstoque.get(name) > 0);
        }
    }

    static class Clientes extends HashMap<Integer,Integer> {
        void addClient(int id) {
            this.put(id,0);
        }

        void listClients() {
            System.out.println("======== Clientes: ========");
            this.keySet().forEach(integer -> System.out.println("Cliente: " + integer + "\nTotal de compras: " + this.get(integer)));
        }

        boolean exists(int id) {
            return this.containsKey(id);

        }
    }
}

