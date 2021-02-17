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

public class EnFragment extends Fragment {
    View myView;
    ImageButton ara ;
    EditText aranan;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor c;
    TextView tww;
    ListView listvv;
    ImageButton araa;
    EditText aranann;
    private String dilen = "en";
    private String diltr = "tr";
    private String dilgn = "gn";
    ImageView sil;
    ImageButton kopyala ;
    private String x ="EN" ;
    private String y ="TR" ;
    ImageButton degistir1;
    TextView txtr;
    TextView txen;
    TextToSpeech t;
    ImageButton hpr ;
    ImageButton mic ;


    public static final int RESULT_SPEECH = 1;

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


                }else if ("en".equalsIgnoreCase(aranacakDil)){
                    c=  db.rawQuery("select*from translate where en_kelime='"+aranan+"'",  null);

                }
                while (c.moveToNext()){
                    if ("tr".equalsIgnoreCase(aranacakDil)){
                        list.add(c.getString(c.getColumnIndex("en_kelime")));
                    }else if ("en".equalsIgnoreCase(aranacakDil)){
                        list.add(c.getString(c.getColumnIndex("tr_kelime")));


                        tww.setText(c.getString(c.getColumnIndex("tr_kelime")));


                    }



                }
                c.close();
                db.close();



            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;



        }else{
            try {
                databaseHelper = new DatabaseHelper(getContext ());
                db= databaseHelper.getReadableDatabase();
                Cursor c = null ;

                if ("tr".equalsIgnoreCase(aranacakDil)){
                    c = db.rawQuery("select*from translate where en_kelime='"+aranan+"'",  null);

                }else if ("en".equalsIgnoreCase(aranacakDil)){
                    c=  db.rawQuery("select*from translate where tr_kelime='"+aranan+"'",  null);

                }
                while (c.moveToNext()){
                    if ("tr".equalsIgnoreCase(aranacakDil)){
                        list.add(c.getString(c.getColumnIndex("tr_kelime")));
                    }else if ("en".equalsIgnoreCase(aranacakDil)){
                        list.add(c.getString(c.getColumnIndex("en_kelime")));

                        tww.setText(c.getString(c.getColumnIndex("en_kelime")));


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



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        myView =  inflater.inflate(R.layout.fragment_birinci,container,false);


         tww = (TextView) myView.findViewById(R.id.textViewtw);
         listvv = (ListView) myView.findViewById(R.id.listvv);
         araa = (ImageButton) myView.findViewById(R.id.buttonara);
         aranann = (EditText) myView.findViewById(R.id.aranannn);
         degistir1=(ImageButton) myView.findViewById(R.id.degistir2);
         txtr=(TextView)myView.findViewById(R.id.textViewtr);
         txen=(TextView)myView.findViewById(R.id.textViewen);
         sil=(ImageView)myView.findViewById(R.id.imageView2);
         kopyala=(ImageButton)myView.findViewById(R.id.imageButton7);
         hpr=(ImageButton)myView.findViewById(R.id.imageButton6);
         mic=(ImageButton)myView.findViewById(R.id.imageButton3);



        Typeface myFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Anton-Regular.ttf");
        txen.setTypeface(myFont);
        txtr.setTypeface(myFont);

        araa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = aranann.getText().toString().toLowerCase();


                arrayAdapter=new ArrayAdapter(
                        getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        degerleriDondur(txt,dilen)

                );

                listvv.setAdapter(arrayAdapter);
                listvv.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Search Complate!", Toast.LENGTH_SHORT).show();

            }
        });

        degistir1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtr.getText().toString().equals(x)){
                    txtr.setText(y);
                    txen.setText(x);
                }else{

                    txtr.setText(x);
                    txen.setText(y);
                }
                Toast.makeText(getActivity(), "Language Changed Complate", Toast.LENGTH_SHORT).show();
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aranann.setText("");
                tww.setText("");
                listvv.setVisibility(View.INVISIBLE);

                Toast.makeText(getActivity(), "Search Clear Complate!", Toast.LENGTH_SHORT).show();
            }
        });

        kopyala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringYouExtracted = tww.getText().toString();
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", stringYouExtracted);

                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Search Copy Complate!", Toast.LENGTH_SHORT).show();
            }
        });



        mic.setOnClickListener(new View.OnClickListener() {   /* MİCROFON AYARLARI*/
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    aranann.setText("");
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
                    t.setLanguage(Locale.ENGLISH);
                }

            }
        });



        hpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = tww.getText().toString();


                Toast.makeText(getActivity(), toSpeak,Toast.LENGTH_SHORT).show();
                t.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


            }
        });

        return myView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    aranann.setText(text.get(0));

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
