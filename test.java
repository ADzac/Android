package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText inputURL;
    private EditText output;
    private ExecutorService exe;
    private Future<String> todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inputURL = (EditText) findViewById(R.id.urlInput);
        output = (EditText) findViewById(R.id.output);
    }

    public void onClick(View v) {
        String s;
        URL u;
        Intent action;
        JSONObject jobj;
        JSONArray jsonArray,sonArray;

        StringBuilder r;

        r = new StringBuilder("");

        //s = "http://infort.gautero.fr/index2022.php?action=get&obj=but";
        s = "http://infort.gautero.fr/index2022.php?action=get&obj=ue&idBut=1";//inputURL.getText().toString();
        Pattern pattern = Pattern.compile(s);
        Matcher m = pattern.matcher("^.*idBut=1$");
        boolean b = m.matches();

        try {
            u = new URL(s);
        } catch (MalformedURLException e) {
            // Ce n'est qu'un exemple, pas de traitement propre de l'exception
            e.printStackTrace();
            u = null;
        }
        // On cr??e l'objet qui va g??rer la thread
        exe = Executors.newSingleThreadExecutor();
        // On lance la thread
        todo = lireURL(u);
        // On attend le r??sultat
        try {
            s = todo.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        action =new Intent(this, MainActivity2.class);
        // On affiche le r??sultat sur une autre activit??
        r.append(" ID | Semestre | Num??ro | IdComp??tence | Parcours " + "\n");
        r.append("-------------------------------------------------------------------------------------" + "\n");
        //action.putExtra("Table",r.toString());
        action.putExtra("Val",s);
        this.startActivity(action);
        /*try {
            jsonArray = new JSONArray(s);
            sonArray = new JSONArray();
            for (int i = 0; i <= jsonArray.length(); i++) {
                JSONObject json=  new JSONObject();
                int id = jsonArray.getJSONObject(i).getInt("id");
                String spec = jsonArray.getJSONObject(i).getString("specialite");
                json.put("id" , id);
                json.put("spec",spec);
                sonArray.put(json);
                action.putExtra("Val",sonArray.toString());
                this.startActivity(action);
                r.append(" " + id + "   |");
                r.append("   " + spec + "\n");
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }*/

        //action =new Intent(this, MainActivity2.class);
        //action.putExtra("Val1",r.toString());
        //this.startActivity(action);

    }

    public Future<String> lireURL(URL u) {
        return exe.submit(() -> {
            URLConnection c;
            String inputline;
            StringBuilder codeHTML = new StringBuilder("");

            try {
                c = u.openConnection();
                //temps maximun allou?? pour se connecter
                c.setConnectTimeout(60000);
                //temps maximun allou?? pour lire
                c.setReadTimeout(60000);
                //flux de lecture avec l'encodage des caract??res UTF-8
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(c.getInputStream(), "UTF-8"));
                while ((inputline = in.readLine()) != null) {
                    //concat??nation+retour ?? la ligne avec \n
                    codeHTML.append(inputline + "\n");
                }
                //il faut bien fermer le flux de lecture
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return codeHTML.toString();
        });
    }
}
