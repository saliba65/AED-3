import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

// Classe Livro
//Implementa os métodos declarados pela interface "Register"

class Book implements Register {

  protected int idLivro;
  protected String titulo;
  protected String autor;
  protected float preco;

  DecimalFormat df = new DecimalFormat("#,##0.00");

  public Book(int i, String t, String a, float p) {
    idLivro = i;
    titulo = t;
    autor = a;
    preco = p;
  }

  public Book(String t, String a, float p) {
    idLivro = -1;
    titulo = t;
    autor = a;
    preco = p;
  }

  public Book() {
    idLivro = -1;
    titulo = "";
    autor = "";
    preco = 0F;
  }

  public String toString() {
    return "\nID: " + idLivro + "\nTítulo: " + titulo + "\nAutor: " + autor + "\nPreço: R$ " + df.format(preco);
  }

  public int getID() {
    return idLivro;
  }

  public void setID(int id) {
    idLivro = id;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeInt(idLivro);
    dos.writeUTF(titulo);
    dos.writeUTF(autor);
    dos.writeFloat(preco);

    return baos.toByteArray();

  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);

    idLivro = dis.readInt();
    titulo = dis.readUTF();
    autor = dis.readUTF();
    preco = dis.readFloat();

  }
}