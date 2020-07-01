import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) {
        ServerSocket serverSocket= null;
        try {
            serverSocket = new ServerSocket(8080);
            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            byte[] buf = new byte[1024];

            int read = inputStream.read(buf);
            String s=new String(buf,0,read);
            System.out.println(s);
            accept.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
