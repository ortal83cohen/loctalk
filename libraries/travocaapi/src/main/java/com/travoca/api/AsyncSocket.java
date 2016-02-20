package com.travoca.api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Validator;

/**
 * @author user
 * @date 2016-02-17
 */
public class AsyncSocket extends AsyncTask<String, Void, String> {
    private java.net.Socket client;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private OutputStream outputStream;
    Context mContext;

    public AsyncSocket(Context context) {
        mContext = context;
    }

    protected String doInBackground(String... urls) {
        try {


            client = new java.net.Socket("stormy-bastion-18585.herokuapp.com", 8080);

            byte[] mybytearray = new byte[urls[0].length()]; //create a byte array to file

            fileInputStream = new FileInputStream(urls[0]);
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            bufferedInputStream.read(mybytearray, 0, mybytearray.length); //read the file

            outputStream = client.getOutputStream();

            outputStream.write(mybytearray, 0, mybytearray.length); //write file to the output stream byte by byte
            outputStream.flush();
            bufferedInputStream.close();
            outputStream.close();
            client.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return  e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return  e.getMessage();
        }
        return "File Sent";
    }

    protected void onPostExecute(String s) {
        Toast.makeText(mContext,  s, Toast.LENGTH_LONG).show();
    }
}