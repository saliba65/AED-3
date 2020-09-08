import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.io.*;

//Classe CRUD Indexado
// Responsável por realizar operações de criação, leitura, escrita e exclusão no banco de dados.

public class CRUD2<T extends Register> {
  protected Constructor<T> construtor; // Construtor generico
  protected String fileName;
  protected String diretorio;
  protected String cestos;
  protected String arvoreB;
  private HashExtensivel hash;
  private ArvoreBMais_String_Int arvore;

  public CRUD2(Constructor<T> construtor, String fn) throws Exception {
    this.construtor = construtor;
    fileName = fn;

    // Criar arquivos
    diretorio = fn;
    String[] temp = fn.split("\\.");
    diretorio = temp[0] + ".diretorio.idx";
    cestos = temp[0] + ".cestos.idx";
    arvoreB = temp[0] + ".arvore.idx";
    new File(diretorio).delete(); // apaga o arquivo anterior, caso já exista um
    new File(cestos).delete(); // apaga o arquivo anterior, caso já exista um

    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    arq.writeInt(0);
    // arq.writeLong(12);
    arq.close();

    // Inicia as estruturas usadas nos arquivos índices
    hash = new HashExtensivel(10, diretorio, cestos);
    arvore = new ArvoreBMais_String_Int(10, arvoreB);
  }

  public int create(T obj) throws Exception {
    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");

    // Lê o cabeçalho e converte o obj recebido para vetor de bytes.
    int id = arq.readInt() + 1;
    String chaveSecundaria = obj.chaveSecundaria();
    obj.setID(id);
    byte[] ba = obj.toByteArray();
    // long pos = arq.readLong();

    // Avança o ponteiro ate a última posição do arquivo e cria o registro.
    arq.seek(arq.length());
    long pos = arq.getFilePointer();
    arq.writeByte(' ');
    arq.writeInt(ba.length);
    arq.write(ba);

    // Salva a nova última posição do arquivo e atualiza o cabeçalho.
    pos = arq.getFilePointer();
    arq.seek(0);
    arq.writeInt(id);

    // arq.writeLong(pos);
    hash.create(id, pos);
    arvore.create(chaveSecundaria, id);
    arq.close();

    return id;
  }

  public T read(int id) throws Exception {

    Long pos = hash.read(id);

    T obj = this.construtor.newInstance();
    /*
     * int tamR; T obj = this.construtor.newInstance(); byte[] ba; boolean found =
     * false;
     */
    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    boolean found = false;
    // arq.readInt();
    // long endOfFile = arq.readLong();

    // Lê o registro
    if (pos >= 0) {
      arq.seek(pos);

      int tamR;
      byte[] ba;

      if (arq.readByte() != '*') {
        tamR = arq.readInt();
        ba = new byte[tamR];
        arq.read(ba);
        obj.fromByteArray(ba);
        if (id == obj.getID()) {
          found = true;
        }
      }
    }

    if (!found) {
      obj = null;
    }

    arq.close();

    return obj;
  }

  public T read(String chave) throws Exception {
    int id = arvore.read(chave);
    return this.read(id);
  }

  public boolean update(T obj) throws Exception {
    // int tamR;
    long pos;
    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    T obj2 = this.read(obj.getID());
    byte[] ba, ba2;
    // boolean found = false;
    boolean ok = false;

    // Compara os tamanhos dos objetos (existente e atualizado)
    if (obj2 != null) {
      pos = hash.read(obj.getID());
      arq.seek(pos);

      ba = obj.toByteArray();
      ba2 = obj2.toByteArray();

      if (ba2.length >= ba.length) {// Sobrescreve o arquivo existente
        arq.seek(arq.getFilePointer() + 5);
        arq.write(ba);

      } else {// Exclui o registro existente e cria um novo no final do arquivo
        arq.writeByte('*');
        arq.seek(arq.length());
        pos = arq.getFilePointer();
        arq.writeByte(' ');
        arq.writeInt(ba.length);
        arq.write(ba);
        hash.update(obj.getID(), pos);
      }

      // Atualiza o índice indireto
      if (obj.chaveSecundaria().compareTo(obj2.chaveSecundaria()) != 0) {
        arvore.update(obj.chaveSecundaria(), obj.getID());
      }

      ok = true;
    }

    arq.close();
    return ok;
  }

  public boolean delete(int id) throws Exception {
    int tamR;
    long pos = hash.read(id);
    T obj = construtor.newInstance();
    byte[] ba;
    // boolean found = false;
    boolean ok = false;

    // Posicao do indice direto
    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    arq.seek(pos);
    // long endOfFile = arq.readLong();

    if (arq.readByte() != '*') {
      tamR = arq.readInt();
      ba = new byte[tamR];
      arq.read(ba);
      obj.fromByteArray(ba);
      if (id == obj.getID()) {
        ok = true;
        arq.seek(pos);
        arq.writeByte('*');
        arvore.delete(obj.chaveSecundaria());
        hash.delete(id);
      }
    }

    arq.close();

    return ok;
  }
}
