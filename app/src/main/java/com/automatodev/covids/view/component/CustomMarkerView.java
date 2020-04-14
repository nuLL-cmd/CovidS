package com.automatodev.covids.view.component;
import android.app.Activity;
import android.widget.TextView;

import com.automatodev.covids.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
public class CustomMarkerView extends MarkerView {

    private TextView tvContent;
    private TextView tvContent2;
    private List<String> mxData;


    public CustomMarkerView(Activity context, int layoutResource, List<String> mxData) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tvContent);
        tvContent2 = findViewById(R.id.tvContent2);
        this.mxData = mxData;

    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Locale locale = new Locale("pt","BR");
        NumberFormat format = NumberFormat.getInstance(locale);
            tvContent.setText("Fatais: "+format.format(e.getY()));
            tvContent2.setText(""+mxData.get((int)e.getX()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}