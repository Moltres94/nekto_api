package core.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Thread.currentThread;

public class LOG {

    public static void stackTrace(){
        StackTraceElement[] tracert = currentThread().getStackTrace();
        int counter = 0;
        for(StackTraceElement element : tracert)
            System.out.println(" ["+counter+++"] "+element.toString());
    }

    private static StackTraceElement trace(){
        return currentThread().getStackTrace()[4];
    }

    private static String simplifiedName(String name){
        String[] parts = name.split("\\.");
        return parts[parts.length -1 ]+".java";
    }

    private static int getLine(StackTraceElement element){
        return element.getLineNumber() -1;
    }

    public static void verbose() {
        System.out.println("LOG:" + trace().getMethodName() + " : (" + simplifiedName(trace().getClassName()) + ":" + getLine(trace())+")");
    }

    public static void println(String message){
        System.out.print("LOG:"+trace().getMethodName()+" : ("+simplifiedName(trace().getClassName()) + ":" + getLine(trace())+")");
        System.out.println(" : print : "+message);
    }

    public static void println(String format, Object ... message){
        System.out.print("LOG:"+trace().getMethodName()+" : ("+simplifiedName(trace().getClassName()) + ":" + getLine(trace())+")");
        System.out.println(" : print : "+String.format(format,message));
    }


    public static void printrn(String message){
        System.err.print("LOG:"+trace().getMethodName()+" : ("+simplifiedName(trace().getClassName()) + ":" + getLine(trace())+")");
        System.err.println(" : print : "+message);
    }

    public static void printrn(String format, Object ... message){
        System.err.print("LOG:"+trace().getMethodName()+" : ("+simplifiedName(trace().getClassName()) + ":" + getLine(trace())+")");
        System.err.println(" : print : "+String.format(format,message));
    }

    public static void writeToLog(String text)throws IOException {
        File logFile = new File("/sdcard/logs.txt");
        if (!logFile.exists()) { logFile.createNewFile(); }
        BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
        buf.append(text);
        buf.newLine();
        buf.close();
    }

    public static void logMultilineString(String data) {
        for (String line : data.split("\n")) {
            final int CHUNK_SIZE = 4076;
            int offset = 0;
            while (offset + CHUNK_SIZE <= data.length()) {
                System.out.println(data.substring(offset, offset += CHUNK_SIZE));
            }
            if (offset < data.length()) {
                System.out.println(data.substring(offset));
            }
        }
    }
}
