import java.nio.ByteBuffer;

public class Packet {

    private int id;
    private int packetLength = -1;
    private ByteBuffer buffer;

    private boolean completed;

    Packet() {

        this.buffer = ByteBuffer.allocate(2048);
        this.buffer.position(4);
    }


    public int getPacketLength() {
        return this.packetLength;
    }


    public void appendBuffer(byte[] buffer, int offset, int length) {

        if (this.buffer.position() + length > 2048) {
            Log.e("packet size must be less than or equal to 2048");
            return;
        }
        this.buffer.put(buffer, offset, length);
    }

    public ByteBuffer getData() {

        int oldPosition = this.buffer.position();
        this.buffer.rewind();


        // size of packet
        this.buffer.putInt(oldPosition - 4);
        this.buffer.limit(oldPosition);


        return this.buffer;
    }
}
