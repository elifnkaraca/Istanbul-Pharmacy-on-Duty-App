package com.example.nobetcieczane;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nobetcieczane.Models.Eczane;
import com.example.nobetcieczane.Models.EczaneDetay;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    String tokenText = "";
    Spinner spinner;
    Document document;
    List<EczaneDetay> eczaneList;
    EczaneAdapter eczaneAdapter;
    ListView listView;
    Button listeleButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        webView = new WebView(getApplicationContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsBridge(), "Android"); //name hep android yazılmalı baska bir seyde calismiyor
        this.getToken();
        final String ilceler[] = {"Adalar", "Arnavutkoy",
                "Ataşehir", "Avcılar", "Bağcılar", "Bahçelievler", "Bakırköy",
                "Başakşehir", "Bayrampaşa", "Beşiktaş", "Beykoz", "Beylikdüzü",
                "Beyoğlu", "Büyükçekmece", "Çatalca", "Çekmeköy", "Esenler",
                "Esenyurt", "Eyüp", "Fatih", "Gaziosmanpaşa", "Güngören", "Kadıköy",
                "Kağıthane", "Kartal", "Küçükcekmece", "Maltepe", "Pendik",
                "Sancaktepe", "Sarıyer", "Şile", "Silivri", "Şişli", "Sultanbeyli",
                "Sultangazi", "Tuzla", "Ümraniye", "Üsküdar", "Zeytinburnu"};
        final int ilceid[] = {1, 33, 34, 2, 3, 4, 5, 35, 6, 7, 8, 36, 9, 10, 11, 37, 13, 38, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 39, 24, 27, 25, 28, 26, 40, 29, 30, 31, 32};

        spinner = findViewById(R.id.ilceSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ilceler);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        listeleButon = findViewById(R.id.listeleButon);
        listeleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                int index = Integer.parseInt(String.valueOf(java.util.Arrays.asList(ilceler).indexOf(item)));
                int id = ilceid[index];
                getEczane(String.valueOf(id));
            }
        });


    }

    public void getEczane(String id) {

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.Android.htmlEczaneDetay(" +
                        "'<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });
        webView.loadUrl("https://apps.istanbulsaglik.gov.tr/Eczane/nobetci?id=" + id + "&token=" + tokenText);
    }

    public void getToken() {
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         super.onPageFinished(view, url);
                                         view.loadUrl("javascript:window.Android.htmlContentForToken(" +
                                                 "'<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                                     }
                                 }
        );
        webView.loadUrl("https://apps.istanbulsaglik.gov.tr/Eczane");

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                tokenText = (String) msg.obj;

            } else if (msg.what == 2) {
                Eczane ec = parseHtml((String) msg.obj);
                eczaneList = ec.getEczaneDetay();
                eczaneAdapter = new EczaneAdapter(eczaneList,MainActivity.this,MainActivity.this);
                listView.setAdapter(eczaneAdapter);

            }
        }
    };

    private Eczane parseHtml(String htmlKaynak) {
        document = Jsoup.parse(htmlKaynak);
        Elements table = document.select("table.ilce-nobet-detay");
        Elements ilceDetay = table.select("caption>b");
        Eczane eczane = new Eczane();
        eczane.setTarih(ilceDetay.get(0).text());
        eczane.setIlceIsmi(ilceDetay.get(1).text());

        Elements eczaneDetayElement = document.select("table.nobecti-eczane");

        List<EczaneDetay> eczaneDetayList = new ArrayList<>();

        for (Element el : eczaneDetayElement) {

            EczaneDetay eczaneDetay = getEczaneDetay(el);
            if (eczaneDetay != null) {
                eczaneDetayList.add(eczaneDetay);
            }

        }
        eczane.setEczaneDetay(eczaneDetayList);
        return eczane;


    }

    public EczaneDetay getEczaneDetay(Element el) {

        String fax = "", adres = "", tel = "",adresTarif="";

        EczaneDetay eczaneDetay = new EczaneDetay();
        Elements eczaneIsmiTag = el.select("thead");
        String eczaneIsmi = eczaneIsmiTag.select("div").attr("title");
        eczaneDetay.setEczaneIsmi(eczaneIsmi);

        Elements trTags = el.select("tbody>tr");
        Elements adresTags = trTags.select("tr#adres");
        adres = adresTags.select("label").get(1).text();
        eczaneDetay.setAdres(adres);

        Elements telTags = trTags.select("tr#Tel");
        tel = telTags.select("label").get(1).text();
        eczaneDetay.setTelefon(tel);

        Element faxTags = trTags.get(2);
        fax = faxTags.select("label").get(1).text();
        if (!fax.equals("")) {
            eczaneDetay.setFax(fax);
        }

        Element adresTarifTags = trTags.get(3);
        adresTarif = adresTarifTags.select("label").get(1).text();
        if (!adresTarif.equals("")) {
            eczaneDetay.setTarif(adresTarif);
        }


        return eczaneDetay;

    }


    class JsBridge extends MainActivity {
        @JavascriptInterface
        public void htmlContentForToken(String str) {

            String[] token = str.split("token");

            if (token.length > 1) {
                String token2[] = token[1].split(Pattern.quote("}"));

                tokenText = token2[0].replaceAll(" ", "")
                        .replaceAll(":", "")
                        .replaceAll("\"", "");
                Message message = new Message();
                message.what = 1;
                message.obj = tokenText;
                handler.sendMessage(message);

            }
        }

        @JavascriptInterface
        public void htmlEczaneDetay(String str) {

            Message message = new Message();
            message.what = 2;
            message.obj = str;
            handler.sendMessage(message);

        }
    }


}
