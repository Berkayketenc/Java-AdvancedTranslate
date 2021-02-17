package com.infobk.translatetr;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.infobk.translatetr.DataBase.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.infobk.translatetr.EnFragment.RESULT_SPEECH;


public class StartScreenFragment extends Fragment {
    View myView1;
    TextView tv ;
    TextView tvs;
    ListView listviewv;
    ImageButton ara ;
    EditText aranan ;
    private String dilen = "en";
    private String diltr = "tr";
    private String dilgn = "gn";
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor c;
    ImageButton degistir8;
    TextView txtr;
    TextView txgn;
    private String x ="GN" ;
    private String y ="TR" ;
    ImageView temizle;
    ImageButton mic;
    TextToSpeech t;
    ImageButton hpr;
    ImageButton copy;

    ArrayAdapter arrayAdapter;
    public ArrayList<String> degerleriDondur(String aranan , String aranacakDil){
        ArrayList list = new ArrayList();
        if (txtr.getText().toString().equals(x)){
            try {
                databaseHelper = new DatabaseHelper (getContext ());

                db= databaseHelper.getReadableDatabase();
                Cursor c = null ;

                if ("tr".equalsIgnoreCase(aranacakDil)){
                    c = db.rawQuery("select*from translate where tr_kelime='"+aranan+"'",  null);

                }else if ("gn".equalsIgnoreCase(aranacakDil)){
                    c=  db.rawQuery("select*from translate where gn_kelime='"+aranan+"'",  null);

                }
                while (c.moveToNext()){
                    if ("tr".equalsIgnoreCase(aranacakDil)){
                        list.add(c.getString(c.getColumnIndex("gn_kelime")));
                    }else if ("gn".equalsIgnoreCase(aranacakDil)){
                        list.add(c.getString(c.getColumnIndex("tr_kelime")));

                        tvs.setText(c.getString(c.getColumnIndex("tr_kelime")));


                    }


                }
                c.close();
                db.close();



            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;


        }else {
            try {
            databaseHelper = new DatabaseHelper (getContext ());

            db= databaseHelper.getReadableDatabase();
            Cursor c = null ;

            if ("tr".equalsIgnoreCase(aranacakDil)){
                c = db.rawQuery("select*from translate where gn_kelime='"+aranan+"'",  null);

            }else if ("gn".equalsIgnoreCase(aranacakDil)){
                c=  db.rawQuery("select*from translate where tr_kelime='"+aranan+"'",  null);

            }
            while (c.moveToNext()){
                if ("tr".equalsIgnoreCase(aranacakDil)){
                    list.add(c.getString(c.getColumnIndex("tr_kelime")));
                }else if ("gn".equalsIgnoreCase(aranacakDil)){
                    list.add(c.getString(c.getColumnIndex("gn_kelime")));

                    tvs.setText(c.getString(c.getColumnIndex("gn_kelime")));


                }


            }
            c.close();
            db.close();



        } catch (IOException e) {
            e.printStackTrace();
        }

            return list;

        }

    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        myView1 = inflater.inflate(R.layout.fragment_ucuncu,container,false);

        tv = (TextView) myView1.findViewById(R.id.textViews);
        listviewv = (ListView) myView1.findViewById(R.id.listviewv);
        ara = (ImageButton) myView1.findViewById(R.id.ara);
        tvs=(TextView)myView1.findViewById(R.id.textViews);
        aranan = (EditText) myView1.findViewById(R.id.aranan);
        degistir8=(ImageButton)myView1.findViewById(R.id.degistir8);
        txgn=(TextView)myView1.findViewById(R.id.textView4);
        txtr=(TextView)myView1.findViewById(R.id.textViewtr) ;
        temizle=(ImageView)myView1.findViewById(R.id.imageView2);
        mic=(ImageButton)myView1.findViewById(R.id.mic);
        hpr=(ImageButton)myView1.findViewById(R.id.hprr);
        copy=(ImageButton)myView1.findViewById(R.id.copy);

        Typeface myFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Anton-Regular.ttf");
        txgn.setTypeface(myFont);
        txtr.setTypeface(myFont);

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtt = aranan.getText().toString().toLowerCase();

                arrayAdapter=new ArrayAdapter(
                        getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        degerleriDondur(txtt,dilgn)

                );

                listviewv.setAdapter(arrayAdapter);
                listviewv.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Search Complate!", Toast.LENGTH_SHORT).show();



            }
        });

        degistir8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtr.getText().toString().equals(x)){
                    txtr.setText(y);
                    txgn.setText(x);
                }else{

                    txtr.setText(x);
                    txgn.setText(y);
                }
            }
        });

        temizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aranan.setText("");
                tvs.setText("");
                listviewv.setVisibility(View.INVISIBLE);

                Toast.makeText(getActivity(), "Search Clear Complate!", Toast.LENGTH_SHORT).show();
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    aranan.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getActivity(),
                            "Ops! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        });

        t=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t.setLanguage(Locale.GERMANY);
                }

            }
        });

        hpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = tvs.getText().toString();


                Toast.makeText(getActivity(), toSpeak,Toast.LENGTH_SHORT).show();
                t.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringYouExtracted = tvs.getText().toString();
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", stringYouExtracted);

                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Search Copy Complate!", Toast.LENGTH_SHORT).show();
            }
        });


        return myView1;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    aranan.setText(text.get(0));

                }
                break;
            }

        }
    }

    public void onPause(){
        if(t !=null){
            t.stop();

        }
        super.onPause();
    }
}
