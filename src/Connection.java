import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Connection {

    private Socket socket;

    private Packet packet;

    private boolean readingStarted = false;
    private boolean continueReading = false;

    Connection() {

        this.packet = new Packet();
        this.socket = new Socket();

    }

    public void connect(InetSocketAddress address) throws IOException {
        this.socket.connect(address);
    }

    public void startReadingFromInput() {

        if (readingStarted) {
            Log.w("reading from input already started");
            return;
        }

        continueReading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("start reading from input");

                byte[] buffer = new byte[2048 - 4];
                int totalRead = 0;
                while (totalRead < buffer.length) {

                    try {

                        byte b = (byte) System.in.read();

                        buffer[totalRead++] = b;

                        if (totalRead > buffer.length || b == -1) {

                            Packet p = new Packet();

                            p.appendBuffer(buffer, 0, totalRead + (b == -1 ? -1 : 0));

                            sendPacket(p);
                            break;
                        }

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void sendPacket(Packet p) {

        try {

            ByteBuffer buffer = p.getData();

            OutputStream os = this.socket.getOutputStream();
            buffer.rewind();

            byte[] bytes = buffer.array();

            int i = 0;
            os.write(bytes, i++, 1);
            os.flush();
            os.write(bytes, i++, 1);
            os.flush();
            os.write(bytes, i++, 1);
            os.flush();
            os.write(bytes, i++, 1);
            os.flush();
            os.write(bytes, i, buffer.limit() - i);

            i = 0;
            os.write(bytes, i++, 1);
            os.flush();
            os.write(bytes, i++, 1);
            os.flush();
            os.write(bytes, i++, 1);
            os.flush();
            os.write(bytes, i++, 1);
            os.flush();
            os.write(bytes, i, buffer.limit() - i);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





















