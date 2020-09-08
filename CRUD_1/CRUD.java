import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.io.IOException;

//Classe CRUD 
// Responsável por realizar operações de criação, leitura, escrita e exclusão no banco de dados.

public class CRUD<T extends Register> {
  protected Constructor<T> construtor; // Construtor generico
  protected String fileName;

  public CRUD(Constructor<T> construtor, String fn) throws Exception {
    this.construtor = construtor;
    fileName = fn;

    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    arq.writeInt(0);
    arq.writeLong(12);
    arq.close();
  }

  public int create(T obj) throws IOException {
    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");

    // Lê o cabeçalho e converte o obj recebido para vetor de bytes.
    int id = arq.readInt() + 1;
    obj.setID(id);
    byte[] ba = obj.toByteArray();
    long pos = arq.readLong();

    // Avança o ponteiro ate a última posição do arquivo e cria o registro.
    arq.seek(pos);
    arq.writeByte(' ');
    arq.writeInt(ba.length);
    arq.write(ba);

    // Salva a nova última posição do arquivo e atualiza o cabeçalho.
    pos = arq.getFilePointer();
    arq.seek(0);
    arq.writeInt(id);
    arq.writeLong(pos);
    arq.close();

    return id;
  }

  public T read(int id) throws Exception {
    int tamR;
    T obj = this.construtor.newInstance();
    byte[] ba;
    boolean found = false;

    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    arq.readInt();
    long endOfFile = arq.readLong();

    do {
      if (arq.readByte() != '*') {
        tamR = arq.readInt();
        ba = new byte[tamR];
        arq.read(ba);
        obj.fromByteArray(ba);
        if (id == obj.getID()) {
          found = true;
        }
      } else {
        tamR = arq.readInt();
        arq.seek(arq.getFilePointer() + tamR);
      }
    } while (!found && arq.getFilePointer() < endOfFile);

    if (!found) {
      obj = null;
    }

    arq.close();

    return obj;
  }

  public boolean update(T obj) throws Exception {
    int tamR;
    long pos;
    T obj2 = this.construtor.newInstance();
    byte[] ba, ba2 = null;
    boolean found = false;
    boolean ok = true;

    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    arq.readInt();
    long endOfFile = arq.readLong();
    do {
      pos = arq.getFilePointer();
      if (arq.readByte() != '*') {
        tamR = arq.readInt();
        ba2 = new byte[tamR];
        arq.read(ba2);
        obj2.fromByteArray(ba2);
        if (obj.getID() == obj2.getID()) {
          found = true;
        }
      } else {
        tamR = arq.readInt();
        arq.seek(arq.getFilePointer() + tamR);
      }
    } while (!found && arq.getFilePointer() < endOfFile);

    if (found) {
      arq.seek(pos);

      ba = obj.toByteArray();

      if (ba2.length >= ba.length) {
        arq.seek(arq.getFilePointer() + 5);
        arq.write(ba);

      } else {
        arq.writeByte('*');
        arq.seek(4);
        arq.seek(arq.readLong());
        arq.writeByte(' ');
        arq.writeInt(ba.length);
        arq.write(ba);
        pos = arq.getFilePointer();

        arq.seek(4);
        arq.writeLong(pos);
      }
    } else {
      ok = false;
    }

    arq.close();
    return ok;
  }

  public boolean delete(int id) throws Exception {
    int tamR;
    long pos;
    T obj = construtor.newInstance();
    byte[] ba;
    boolean found = false;
    boolean ok = false;

    RandomAccessFile arq = new RandomAccessFile(fileName, "rw");
    arq.readInt();
    long endOfFile = arq.readLong();

    do {
      pos = arq.getFilePointer();
      if (arq.readByte() != '*') {
        tamR = arq.readInt();
        ba = new byte[tamR];
        arq.read(ba);
        obj.fromByteArray(ba);
        if (id == obj.getID()) {
          found = ok = true;
          arq.seek(pos);

          arq.writeByte('*');
        }
      } else {
        tamR = arq.readInt();
        arq.seek(arq.getFilePointer() + tamR);
      }
    } while (!found && arq.getFilePointer() < endOfFile);

    arq.close();

    return ok;
  }
}
