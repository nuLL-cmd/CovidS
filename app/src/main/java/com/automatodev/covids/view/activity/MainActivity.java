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

import java.util.ArrayList;
import java.util.List;
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
        utils = new FormatUtils();
        //############
        anim = AnimationUtils.loadAnimation(this, R.anim.push_right_fast);
        //#############
        chartLine = new ChartLine(MainActivity.this, chart);
        //#############
        covidService = new CovidService();
        //#############
        showGlobalData();
        showCountriesData();
    }

    public void refreshData(View view){
        if (relativeProgess_global.getVisibility() == View.GONE){
            relativeProgess_global.setVisibility(View.VISIBLE);
            relativeProgressChart_main.setVisibility(View.VISIBLE);
            card_total.setVisibility(View.GONE);
        }
        covidService.serviceresultsGlobal(new SingleCallback() {
            @Override
            public void onResponse(Covid covid) {
                txtTotalCases_main.setText(utils.decimal(covid.getCases()));
                txtTotalDeaths_main.setText(utils.decimal(covid.getDeaths()));
                txtTotalRecover_main.setText(utils.decimal(covid.getRecovered()));
                //#############
                long date = System.currentTimeMillis();
                txtDate_main.setText(utils.dateFormat(date));
                //#############
                relativeProgess_global.setVisibility(View.GONE);
                card_total.setVisibility(View.VISIBLE);
            }
        });

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
                Toast.makeText(MainActivity.this,"Dados atualizados",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void showGlobalData() {
        if (relativeProgess_global.getVisibility() == View.GONE){
            relativeProgess_global.setVisibility(View.VISIBLE);
            card_total.clearAnimation();
        }
        covidService.serviceresultsGlobal(new SingleCallback() {
            @Override
            public void onResponse(Covid covid) {
                txtTotalCases_main.setText(utils.decimal(covid.getCases()));
                txtTotalDeaths_main.setText(utils.decimal(covid.getDeaths()));
                txtTotalRecover_main.setText(utils.decimal(covid.getRecovered()));
                //#############
                long date = System.currentTimeMillis();
                txtDate_main.setText(utils.dateFormat(date));
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
                relativeProgressChart_main.setVisibility(View.VISIBLE);
                final List<Covid> europeList = new ArrayList<>();
                String europe[] = getResources().getStringArray(R.array.europa_english);
                for (Covid c : globalList) {
                    for (String e : europe) {
                        if (e.equals(c.getCountry()))
                            europeList.add(c);
                    }
                }
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            relativeProgressChart_main.post(new Runnable() {
                                @Override
                                public void run() {
                                    relativeProgressChart_main.setVisibility(View.GONE);
                                    chartLine.makeGraph(europeList, "Perspectiva: Europa - " + europeList.size() + " paises");
                                }
                            });
                        }
                    }
                    ;
                }.start();
                break;
            case R.id.btn_1:
                relativeProgressChart_main.setVisibility(View.VISIBLE);
                final List<Covid> naList = new ArrayList<>();
                String na[] = getResources().getStringArray(R.array.na_english);
                for (Covid c : globalList) {
                    for (String e : na) {
                        if (e.equals(c.getCountry()))
                            naList.add(c);
                    }
                }
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            relativeProgressChart_main.post(new Runnable() {
                                @Override
                                public void run() {
                                    relativeProgressChart_main.setVisibility(View.GONE);
                                    chartLine.makeGraph(naList, "Perspectiva: América do Norte - " + naList.size() + " paises");
                                }
                            });
                        }
                    }
                }.start();
                break;
            case R.id.btn_2:
                relativeProgressChart_main.setVisibility(View.VISIBLE);
                final List<Covid> nsList = new ArrayList<>();
                String ns[] = getResources().getStringArray(R.array.ns_english);
                for (Covid c : globalList) {
                    for (String e : ns) {
                        if (e.equals(c.getCountry()))
                            nsList.add(c);
                    }
                }
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            relativeProgressChart_main.post(new Runnable() {
                                @Override
                                public void run() {
                                    relativeProgressChart_main.setVisibility(View.GONE);
                                    chartLine.makeGraph(nsList, "Perspectiva: América do Sul - " + nsList.size() + " paises");
                                }
                            });
                        }
                    }
                }.start();
                break;
            case R.id.btn_3:
                relativeProgressChart_main.setVisibility(View.VISIBLE);
                final List<Covid> asiaList = new ArrayList<>();
                String asia[] = getResources().getStringArray(R.array.asia_english);
                for (Covid c : globalList) {
                    for (String e : asia) {
                        if (e.equals(c.getCountry()))
                            asiaList.add(c);
                    }
                }
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            relativeProgressChart_main.post(new Runnable() {
                                @Override
                                public void run() {
                                    relativeProgressChart_main.setVisibility(View.GONE);
                                    chartLine.makeGraph(asiaList, "Perspectiva: Asia - " + asiaList.size() + " paises");
                                }
                            });
                        }
                    }
                }.start();
                break;
            case R.id.btn_4:
                relativeProgressChart_main.setVisibility(View.VISIBLE);
                final List<Covid> oceaniaList = new ArrayList<>();
                String oceania[] = getResources().getStringArray(R.array.oceania_english);
                for (Covid c : globalList) {
                    for (String e : oceania) {
                        if (e.equals(c.getCountry()))
                            oceaniaList.add(c);
                    }
                }
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }finally {
                            relativeProgressChart_main.post(new Runnable() {
                                @Override
                                public void run() {
                                    relativeProgressChart_main.setVisibility(View.GONE);
                                    chartLine.makeGraph(oceaniaList, "Perspectiva: Oceania - " + oceaniaList.size() + " paises");
                                }
                            });
                        }
                    }
                }.start();
                break;
            case R.id.btn_5:
                relativeProgressChart_main.setVisibility(View.VISIBLE);
                final List<Covid> africaList = new ArrayList<>();
                String africa[] = getResources().getStringArray(R.array.africa_english);
                for (Covid c : globalList) {
                    for (String e : africa) {
                        if (e.equals(c.getCountry()))
                            africaList.add(c);
                    }
                }
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }finally {
                            relativeProgressChart_main.post(new Runnable() {
                                @Override
                                public void run() {
                                    relativeProgressChart_main.setVisibility(View.GONE);
                                    chartLine.makeGraph(africaList, "Perspectiva: Africa - " + africaList.size() + " paises");
                                }
                            });
                        }
                    }
                }.start();
                break;
            case R.id.btn_6:
                relativeProgressChart_main.setVisibility(View.VISIBLE);
                final List<Covid> covidFilter = new ArrayList<>();
                String sheets[] = getResources().getStringArray(R.array.sheets);
                for (Covid c : globalList) {
                    if (c.getDeaths() >= 0)
                        covidFilter.add(c);
                    for (String s : sheets) {
                        if (s.equals(c.getCountry()))
                            covidFilter.remove(c);
                    }
                }
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }finally {
                            relativeProgressChart_main.post(new Runnable() {
                                @Override
                                public void run() {
                                    relativeProgressChart_main.setVisibility(View.GONE);
                                    chartLine.makeGraph(covidFilter, "Perspectiva: Global");
                                }
                            });
                        }
                    }
                }.start();
                break;
        }
    }
}
