package com.automatodev.covids.controller.callback;
import com.automatodev.covids.model.entity.Covid;

import java.util.List;
public interface AllCallback {
   void onResponse(List<Covid> listCovid);
}
