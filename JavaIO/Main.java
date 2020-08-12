import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class Main {

  public static void main(String[] args) {

    Livro l1 = new Livro(1, "Naruto", "Masashi Kishimoto", 14.9F);
    Livro l2 = new Livro(2, "Dragon Ball", "Akira Toriyama", 21.99F);
    Livro l3 = new Livro(3, "One Piece", "Eiichiro Oda", 17.86F);

    FileOutputStream arq;
    DataOutputStream dos;

    FileInputStream arq2;
    DataInputStream dis;

    try {

      // * ESCRITA *
      arq = new FileOutputStream("dados/livros.db");
      dos = new DataOutputStream(arq);

      dos.writeInt(l1.idLivro);
      dos.writeUTF(l1.titulo);
      dos.writeUTF(l1.autor);
      dos.writeFloat(l1.preco);

      dos.writeInt(l2.idLivro);
      dos.writeUTF(l2.titulo);
      dos.writeUTF(l2.autor);
      dos.writeFloat(l2.preco);

      dos.writeInt(l3.idLivro);
      dos.writeUTF(l3.titulo);
      dos.writeUTF(l3.autor);
      dos.writeFloat(l3.preco);

      dos.close();
      arq.close();

      // * LEITURA *

      Livro l4 = new Livro();
      Livro l5 = new Livro();

      arq2 = new FileInputStream("dados/livros.db");
      dis = new DataInputStream(arq2);

      l4.idLivro = dis.readInt();
      l4.titulo = dis.readUTF();
      l4.autor = dis.readUTF();
      l4.preco = dis.readFloat();
      System.out.println(l4);

      l5.idLivro = dis.readInt();
      l5.titulo = dis.readUTF();
      l5.autor = dis.readUTF();
      l5.preco = dis.readFloat();
      System.out.println(l5);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}