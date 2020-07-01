import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        String host="127.0.0.1";
        int port=8080;
        try {
            Socket socket=new Socket(host,port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello".getBytes());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
