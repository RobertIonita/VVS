package Test;

        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertTrue;
        import static org.junit.jupiter.api.Assertions.assertThrows;
        import static org.junit.jupiter.api.Assertions.fail;
        import static org.mockito.Mockito.mock;
        import static org.mockito.Mockito.when;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.io.OutputStream;
        import java.io.PrintWriter;
        import java.net.Socket;

        import application.Server;
        import application.ServerState;
        import org.junit.jupiter.api.BeforeAll;
        import org.junit.jupiter.api.BeforeEach;

public class ServerTest {

    private static Server server;
    private static final int testPort = 12345;
    private static Socket client;
    private ByteArrayOutputStream socketOutput;
    private ServerState serverState;

    @BeforeAll
    public static void setup() {
        try {
            server = new Server(testPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client = mock(Socket.class);
    }

    @BeforeEach
    public void socketSetup() throws IOException {
        //so we can read data that is sent to socket. We have no test cases that need to fake send data to socket yet.

        socketOutput = new ByteArrayOutputStream();
        when(client.getOutputStream()).thenReturn(socketOutput);
    }


}