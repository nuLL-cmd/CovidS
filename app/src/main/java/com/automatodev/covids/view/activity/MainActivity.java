package com.automatodev.covids.view.activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.automatodev.covids.R;
import com.automatodev.covids.controller.callback.AllCallback;
import com.automatodev.covids.controller.callback.SingleCallback;
import com.automatodev.covids.controller.service.CovidService;
import com.automatodev.covids.model.entity.Covid;
import com.automatodev.covids.view.FormatUtils;
import com.automatodev.covids.view.component.ChartLine;
import com.github.mikephil.charting.charts.LineChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    private TextView txtTotalCases_main;
    private TextView txtTotalDeaths_main;
    private TextView txtTotalRecover_main;
    private TextView txtDate_main;
    private RelativeLayout relativeProgressChart_main;
    private RelativeLayout relativeProgess_global;
    private LineChart chart;
    private Animation anim;
    private CardView card_total;
    private List<Covid> globalList;
    private ChartLine chartLine;
    private FormatUtils utils;
    //#############
    private CovidService covidService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //#############
        txtTotalCases_main = findViewById(R.id.txtTotalCases_main);
        txtTotalDeaths_main = findViewById(R.id.txtTotalDeaths_main);
        txtTotalRecover_main = findViewById(R.id.txtTotalRecover_main);
        relativeProgess_global = findViewById(R.id.relativeProgress_main);
        txtDate_main = findViewById(R.id.txtDate_main);
        card_total = findViewById(R.id.card_total);
        chart = findViewById(R.id.chart);
        relativeProgressChart_main = findViewById(R.id.relativeProgressChart_main);
        //############
        utils  = new FormatUtils();
        //############
        anim = AnimationUtils.loadAnimation(this, R.anim.push_right_fast);
        //#############
        chartLine = new ChartLine(MainActivity.this, chart);
        //#############
        showGlobalData();
        showCountriesData();
    }
    public void showGlobalData() {
        covidService = new CovidService();
        covidService.serviceresultsGlobal(new SingleCallback() {
            @Override
            public void onResponse(Covid covid) {
                txtTotalCases_main.setText(utils.decimal(covid.getCases()));
                txtTotalDeaths_main.setText(utils.decimal(covid.getDeaths()));
                txtTotalRecover_main.setText(utils.decimal(covid.getRecovered()));
                //#############
                long date = System.currentTimeMillis();
                Locale locale = new Locale("pt", "BR");
                SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", locale);
                txtDate_main.setText(f.format(date));
                //#############
                relativeProgess_global.setVisibility(View.GONE);
                card_total.setVisibility(View.VISIBLE);
                card_total.setAnimation(anim);
            }
        });
    }
    public void showCountriesData() {
        final String sheets[] = getResources().getStringArray(R.array.sheets);
        covidService.servceResultCountries(new AllCallback() {
            @Override
            public void onResponse(List<Covid> listCovid) {
                globalList = listCovid;
                Log.i("logx", "All: " + listCovid.toString());
                List<Covid> covidFilter = new ArrayList<>();
                for (Covid c : listCovid) {
                    if (c.getDeaths() >= 100)
                        covidFilter.add(c);
                    for (String s : sheets) {
                        if (s.equals(c.getCountry()))
                            covidFilter.remove(c);
                    }
                }
                chartLine.makeGraph(covidFilter, "Perspectiva: Global - Fatais >= 100");
                relativeProgressChart_main.setVisibility(View.GONE);
            }
        });
    }
    public void showFilter(View view) {
        if (globalList == null) {
            Toast.makeText(this, "Favor aguarde o carregamento dos dados\nantes de filtra-los", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.btn_0:
                List<Covid> europeList = new ArrayList<>();
                String europe[] = getResources().getStringArray(R.array.europa_english);
                for (Covid c : globalList) {
                    for (String e : europe) {
                        if (e.equals(c.getCountry()))
                            europeList.add(c);
                    }
                }
                chartLine.makeGraph(europeList, "Perspectiva: Europa - " + europeList.size() + " Paises");
                break;
            case R.id.btn_1:
                break;
            case R.id.btn_2:
                break;
            case R.id.btn_3:
                List<Covid> asiaList = new ArrayList<>();
                String asia[] = getResources().getStringArray(R.array.asia_english);
                for (Covid c : globalList) {
                    for (String e : asia) {
                        if (e.equals(c.getCountry()))
                            asiaList.add(c);
                    }
                }
                chartLine.makeGraph(asiaList, "Perspectiva: Asia - " + asiaList.size() + " Paises");
                break;
            case R.id.btn_4:
                break;
            case R.id.btn_5:
                break;
            case R.id.btn_6:
                String sheets[] = getResources().getStringArray(R.array.sheets);
                List<Covid> covidFilter = new ArrayList<>();
                for (Covid c : globalList) {
                    if (c.getDeaths() >= 0)
                        covidFilter.add(c);
                    for (String s : sheets) {
                        if (s.equals(c.getCountry()))
                            covidFilter.remove(c);
                    }
                }
                chartLine.makeGraph(covidFilter, "Perspectiva: Global");
                break;
        }

    }
}
