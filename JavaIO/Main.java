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

    byte[] ba;

    try {

      // * ESCRITA *
      arq = new FileOutputStream("dados/livros.db");
      dos = new DataOutputStream(arq);
      ba = l1.toByteArray();
      dos.writeInt(ba.length);
      arq.write(ba);

      ba = l2.toByteArray();
      dos.writeInt(ba.length);
      arq.write(ba);

      arq.close();

      // * LEITURA *

      Livro l4 = new Livro();
      // Livro l5 = new Livro();
      int tam;

      arq2 = new FileInputStream("dados/livros.db");
      dis = new DataInputStream(arq2);

      tam = dis.readInt();
      ba = new byte[tam];
      dis.read(ba);
      l3.fromByteArray(ba);

      tam = dis.readInt();
      ba = new byte[tam];
      dis.read(ba);
      l4.fromByteArray(ba);

      System.out.println(l3);
      System.out.println(l4);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}