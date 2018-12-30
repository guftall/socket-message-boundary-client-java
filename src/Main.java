import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        InetSocketAddress address = null;
        try {
            address = new InetSocketAddress("127.0.0.1", 5310);
            Connection connection = new Connection();
            connection.connect(address);

            connection.startReadingFromInput();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
