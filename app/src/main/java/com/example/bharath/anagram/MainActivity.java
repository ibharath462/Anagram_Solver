package com.example.bharath.anagram;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    EditText ip;
    info.hoang8f.widget.FButton b1;
    ProgressDialog progressDialog;
    String t;
    TextView text;
    ArrayList<String> grams;
    HashMap<String,ArrayList<String>> dictionary=new HashMap<String,ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip=(EditText)findViewById(R.id.input);
        text=(TextView)findViewById(R.id.text);
        b1=(info.hoang8f.widget.FButton)findViewById(R.id.b1);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading the words...");
        progressDialog.show();
        loadWords loader=new loadWords();
        loader.execute();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String t=ip.getText().toString();
                t=t.toLowerCase();
                char []chars=t.toCharArray();
                Arrays.sort(chars);
                String sorted=new String(chars);
                grams=dictionary.get(sorted);
                if(grams!=null) {
                    text.setText("");
                    for (String word : grams) {
                        text.append(word + "\n");
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"No anagrams found",Toast.LENGTH_SHORT).show();
                }


            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    protected class loadWords extends AsyncTask<String, String, String>
    {
        public loadWords()
        {

        }

        @Override
        protected void onPreExecute() {

            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    loadWords.this.cancel(true);
                }
            });

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... arg0) {

            String cardPath = ""+Environment.getExternalStorageDirectory();
            BufferedReader r = null;
            try {
                r = new BufferedReader(new FileReader(cardPath + "/words.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line;
            try {
                while((line = r.readLine()) != null) {
                    String t=line.toLowerCase();
                    char []chars=t.toCharArray();
                    Arrays.sort(chars);
                    String sorted=new String(chars);
                    ArrayList<String> list;
                    if(dictionary.containsKey(sorted)){

                        list=dictionary.get(sorted);
                        list.add(t);

                    }
                    else{
                        list=new ArrayList<String>();
                        list.add(t);
                        dictionary.put(sorted,list);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Loaded words..",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            });

        }
    }

    /*void permute(int l,int r)
    {
        if(l==r) {
            if(!op.contains(t))
                op.push(t);
        }
        for(int i=l;i<=r;i++)
        {
            swap(l,i);
            permute(l + 1, r);
            swap(l, i);
        }
    }
    void swap(int x,int y)
    {
        char[] temp=t.toCharArray();
        char z;
        z=temp[x];
        temp[x]=temp[y];
        temp[y]=z;
        t=String.copyValueOf(temp);
        //Toast.makeText(getApplicationContext(),""+temp[x]+","+temp[y]+","+t,Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
