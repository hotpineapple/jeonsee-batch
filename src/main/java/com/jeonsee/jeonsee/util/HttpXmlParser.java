package com.jeonsee.jeonsee.util;

import com.jeonsee.jeonsee.model.PerformanceDetailDisplay;
import com.jeonsee.jeonsee.model.PerformanceDisplay;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;

public class HttpXmlParser<T> {
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public PerformanceDisplay parseList(Request request) {
        try {
            String xmlString = CLIENT.newCall(request).execute().body().string();
            JAXBContext jaxbContext = JAXBContext.newInstance(PerformanceDisplay.class);
            Unmarshaller unmarshal = jaxbContext.createUnmarshaller();
            return (PerformanceDisplay) unmarshal.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PerformanceDetailDisplay parseDetail(Request request) {
        try {
            String xmlString = CLIENT.newCall(request).execute().body().string();
            JAXBContext jaxbContext = JAXBContext.newInstance(PerformanceDetailDisplay.class);
            Unmarshaller unmarshal = jaxbContext.createUnmarshaller();
            return (PerformanceDetailDisplay) unmarshal.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
