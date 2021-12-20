package application;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
            Server server = new Server(63343);
            server.start();
        }
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
