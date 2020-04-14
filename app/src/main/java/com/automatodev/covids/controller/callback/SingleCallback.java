package com.automatodev.covids.controller.callback;
import com.automatodev.covids.model.entity.Covid;
public interface SingleCallback {
    void onResponse(Covid covid);
}
