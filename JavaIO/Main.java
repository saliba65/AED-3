
import java.io.RandomAccessFile;

public class Main {

  public static void main(String[] args) {

    Livro l1 = new Livro(1, "Naruto", "Masashi Kishimoto", 14.9F);
    Livro l2 = new Livro(2, "Dragon Ball", "Akira Toriyama", 21.99F);

    RandomAccessFile arq;
    byte[] ba;

    try {

      // * ESCRITA *
      arq = new RandomAccessFile("dados/livros.db", "rw");

      long p1 = arq.getFilePointer();
      ba = l1.toByteArray();
      arq.writeInt(ba.length);
      arq.write(ba);

      long p2 = arq.getFilePointer();
      ba = l2.toByteArray();
      arq.writeInt(ba.length);
      arq.write(ba);

      // * LEITURA *

      Livro l3 = new Livro();
      Livro l4 = new Livro();
      // Livro l5 = new Livro();
      int tam;

      arq.seek(p2);
      tam = arq.readInt();
      ba = new byte[tam];
      arq.read(ba);
      l3.fromByteArray(ba);

      arq.seek(p1);
      tam = arq.readInt();
      ba = new byte[tam];
      arq.read(ba);
      l4.fromByteArray(ba);

      System.out.println(l3);
      System.out.println(l4);

      arq.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}