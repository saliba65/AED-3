import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
//import java.text.DecimalFormat;

//Classe Cliente, implenta metodos declarados em 'Registro'

public class Client implements Register {
  protected int id;
  protected String nome;
  protected byte idade;
  protected String email;

  // DecimalFormat df = new DecimalFormat("#,##0.00");

  // Construtores
  public Client(int id_, String n, byte i, String e) {
    this.id = id_;
    nome = n;
    idade = i;
    email = e;
  }

  public Client(String n, byte i, String e) {
    id = -1;
    nome = n;
    idade = i;
    email = e;
  }

  public Client() {
    id = -1;
    nome = "";
    idade = (short) -1;
    email = "";
  }

  public String toString() {
    return "\nID: " + id + "\nNome: " + nome + "\nIdade: " + idade + "\nEmail: " + email;
  }

  // Obter ID
  public int getID() {
    return id;
  }

  // Modificar ID
  public void setID(int newId) {
    id = newId;
  }

  /*
   * Escreve os dados dos atributos do objeto em um vetor de bytes e o retorna.
   */
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeInt(id);
    dos.writeUTF(nome);
    dos.writeByte(idade);
    dos.writeUTF(email);

    return baos.toByteArray();

  }

  /*
   * Atribui valores aos atributos de uma determinada instância a partir da
   * leitura de um vetor de bytes.
   */

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);

    id = dis.readInt();
    nome = dis.readUTF();
    idade = dis.readByte();
    email = dis.readUTF();

    dis.close();

  }

}