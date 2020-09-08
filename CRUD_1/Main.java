//import java.io.*;

public class Main {

  // Arquivo declarado fora de main() para ser poder ser usado por outros métodos
  private static CRUD<Book> arqLivros;
  private static CRUD<Client> arqCliente;

  public static void main(String[] args) {

    // Livros de exemplo
    Book l1 = new Book("Eu, Robô", "Isaac Asimov", 14.9F);
    Book l2 = new Book("Eu Sou A Lenda", "Richard Matheson", 21.99F);
    Book l3 = new Book("Número Zero", "Umberto Eco", 34.11F);
    int id1, id2, id3;

    try {

      // Abre (cria) o arquivo de livros
      arqLivros = new CRUD<>(Book.class.getConstructor(), "livros.db");

      // Insere os três livros
      id1 = arqLivros.create(l1);
      l1.setID(id1);
      id2 = arqLivros.create(l2);
      l2.setID(id2);
      id3 = arqLivros.create(l3);
      l3.setID(id3);

      // Busca por dois livros
      System.out.println(arqLivros.read(id3));
      System.out.println(arqLivros.read(id1));

      // Altera um livro para um tamanho maior e exibe o resultado
      l2.autor = "Richard Burton Matheson";
      arqLivros.update(l2);
      System.out.println(arqLivros.read(id2));

      // Altera um livro para um tamanho menor e exibe o resultado
      l1.autor = "I. Asimov";
      arqLivros.update(l1);
      System.out.println(arqLivros.read(id1));

      // Excluir um livro e mostra que não existe mais
      arqLivros.delete(id3);
      Book l = arqLivros.read(id3);
      if (l == null)
        System.out.println("\nLivro excluído");
      else
        System.out.println(l);

    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\tFIM  LIVROS\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

    Client c1 = new Client(-1, "Lucas Saliba", (byte) 20, "lucassaliba@ioasys.com.br");
    Client c2 = new Client(-1, "Arthur Azalim", (byte) 19, "arthurazalim14@gmail.com");
    Client c3 = new Client(-1, "Lucca Anderson", (byte) 18, "luccaanderson@gmail.com");

    try {

      // Abre (cria) o arquivo de Produto
      arqCliente = new CRUD<>(Client.class.getConstructor(), "clients.db");

      // Insere os três Produto
      id1 = arqCliente.create(c1);
      c1.setID(id1);
      id2 = arqCliente.create(c2);
      c2.setID(id2);
      id3 = arqCliente.create(c3);
      c3.setID(id3);

      // Busca por dois Produto
      System.out.println(arqCliente.read(id1));
      System.out.println(arqCliente.read(id2));
      System.out.println(arqCliente.read(id3));

      // Altera um Produto para um tamanho maior e exibe o resultado
      c2.nome = "Arthur Azalim de Campos";
      arqCliente.update(c2);
      System.out.println(arqCliente.read(id2));

      // Altera um Produto para um tamanho menor e exibe o resultado
      c1.nome = "Lucas";
      arqCliente.update(c1);
      System.out.println(arqCliente.read(id1));

      // Excluir um Produto e mostra que não existe mais
      arqCliente.delete(id3);
      Client l = arqCliente.read(id3);
      if (l == null)
        System.out.println("\nCliente excluído");
      else
        System.out.println(l);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
