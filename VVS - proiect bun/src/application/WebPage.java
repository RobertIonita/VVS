package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import customExceptions.AccessDeniedException;

public class WebPage {
	private static WebPage instance;
	private String pageLocation = "./htdocs/";
	
	
	private WebPage() {}
	
	public static WebPage getInstance() {
		if (instance == null) {
			instance = new WebPage();
		}
		return instance;
	}
	
	
	public void runHtml(String request, OutputStream clientOutputStream, ServerState serverState)
	{
		if (request == null) 
			return; 
		
	    PrintWriter out = new PrintWriter(clientOutputStream, true);
	    out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("\r\n");
        
        switch (serverState) {
        case Maintenance:
        	runMaintenaceHtml(out);
        	break;
        case Running:
        	runRunningHtml(request, out);
        	break;
        case Stopped: 
        default:
        		break;
        }
        
		
	}

	private void runRunningHtml(String request, PrintWriter out) {
		String[] requestParameters = request.split(" ");
	    String htmlFilePath = requestParameters[1].equals("/") ? "/index.html" : requestParameters[1];
	
		try {
			if (htmlFilePath.toLowerCase().contains("maintenance/index.html"))
				throw new AccessDeniedException("Not possible to access maintenance in running");
			
	        BufferedReader reader = new BufferedReader(new FileReader(pageLocation + htmlFilePath));
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null)
	        	out.println(inputLine);
	
	        reader.close();
	 	} catch (Exception e) {
	         out.println("<h1>404 File not found</h1>");
		}
	}
	
	private void runMaintenaceHtml(PrintWriter out) {
		String htmlFilePath = pageLocation + "maintenance/index.html";
    	try {
    		BufferedReader reader = new BufferedReader(new FileReader(htmlFilePath));
    		String inputLine;
    		while ((inputLine = reader.readLine()) != null)
    			out.println(inputLine);

        reader.close();
    	} catch (Exception e) {
    		out.println("<h1>404 File not found</h1>");
        	out.close();
    	}

	}
}
