import java.io.IOException;

// Interface Registro

public interface Register {
  public int getID();

  public void setID(int n);

  public byte[] toByteArray() throws IOException;

  public void fromByteArray(byte[] ba) throws IOException;
}