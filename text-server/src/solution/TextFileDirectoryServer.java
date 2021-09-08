package solution;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TextFileDirectoryServer {

    private static File directory;
    private static final int LISTENING_PORT = 3000;

    public static void main(String[] args){
        if(args.length>0){
            directory = new File(args[0]);
        }else{
            directory = new File("src\\resources\\files");
        }

        ServerSocket listener;
        Socket connection;
        String messageIn;
        BufferedReader reader;
        PrintWriter outgoing;

        try{
            listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
            while (true) {
                connection = listener.accept();
                if(connection.isConnected()){
                    System.out.println("Connected.");
                }

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()) );
                outgoing = new PrintWriter(connection.getOutputStream());

                messageIn = reader.readLine();



                if(messageIn.equals("INDEX")){
                    getIndex(connection);
                    outgoing.flush();
                    outgoing.close();
                    connection.close();
                }else if(messageIn.startsWith("GET ")){
                    File searchFile = new File (directory+"\\" + messageIn.substring(4));

                    if(searchFile.exists()){
                        getFileContents(searchFile,connection);
                    }else{
                        outgoing.println("ERROR");
                        outgoing.println(messageIn.substring(4)+" is not a valid file name.");
                    }
                    outgoing.flush();
                    outgoing.close();
                    connection.close();
                }else{
                    outgoing.println("ERROR");
                    outgoing.println(messageIn + " is not a valid request.");
                    outgoing.flush();
                    outgoing.close();
                    connection.close();
                }


            }
        }catch (Exception e){
            System.out.println("Sorry, the server has shut down.");
            System.out.println("Error: " + e);
            return;
        }


    }





    private static void getIndex(Socket connection){
        String[] filesInDirectory;
        if(directory.exists()&&directory.isDirectory()){
            filesInDirectory = directory.list();
        }else{
            return;
        }

        try{
            PrintWriter output = new PrintWriter(connection.getOutputStream());
            for(String fileName : filesInDirectory){
                output.println(fileName);
            }
            output.flush();
            System.out.println("Connection" + connection.getPort() +" closed.");
            connection.close();
        }catch (Exception e){
            System.out.println("An error occurred.");
        };
    }

    private static void getFileContents(File searchFile, Socket connection){
        try{
            BufferedReader lineReader = new BufferedReader(new FileReader(searchFile));
            PrintWriter output = new PrintWriter(connection.getOutputStream());
            lineReader.lines().forEach(output::println);
            lineReader.close();
            output.flush();
            connection.close();
        }catch (IOException e){
            System.out.println("An error occurred.");
        }
    }

}
