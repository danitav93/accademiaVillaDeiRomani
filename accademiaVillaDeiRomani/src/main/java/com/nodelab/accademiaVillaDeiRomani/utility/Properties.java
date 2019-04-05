package com.nodelab.accademiaVillaDeiRomani.utility;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class Properties {
	 public static void main( String[] args )
	   {
	    try {
	       ArrayList<String> pippo = new ArrayList<String>();
	       int i;
	       FileReader f;
	       BufferedReader b;
	       String s;
	       

	        FileOutputStream prova = new FileOutputStream("C:\\Users\\User\\Downloads\\eccoci.txt");
	        @SuppressWarnings("resource")
	        PrintStream scrivi = new PrintStream(prova);
	        
	       
	       f=new FileReader("C:\\Users\\User\\Downloads\\wow.txt");
	       b=new BufferedReader(f);

	       s=b.readLine();
	       
	       
	    // lettura array da file
	       while (s!=null) {
	        
	        pippo.add(s);
	        s=b.readLine();
	        
	       }
	       
	    // stampa dell'array
	       for(i=0; i<pippo.size(); i++) {
	         scrivi.println(pippo.get(i).substring(0,pippo.get(i).indexOf("=")+1));
	       }
	       b.close();
	     } catch(Exception e) {e.printStackTrace();}
	    
	   }
}
