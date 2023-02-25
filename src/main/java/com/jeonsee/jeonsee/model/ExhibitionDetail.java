package com.jeonsee.jeonsee.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.jeonsee.jeonsee.util.LocalDateAdapter;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@XmlRootElement(name = "perforInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExhibitionDetail {
    @XmlElement(name = "seq")
    private long seq;

    @XmlElement(name = "title")
    private String title;

    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "startDate")
    private LocalDate startDate;

    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "endDate")
    private LocalDate endDate;

    @XmlElement(name = "place")
    private String place;

    @XmlElement(name = "realmName")
    private String realmName;

    @XmlElement(name = "area")
    private String area;

    @XmlElement(name = "subTitle")
    private String subTitle;

    @XmlElement(name = "price")
    private String price;

    @XmlElement(name = "contents1")
    private String contents1;

    @XmlElement(name = "contents2")
    private String contents2;

    @XmlElement(name = "url")
    private String url;

    @XmlElement(name = "phone")
    private String phone;

    @XmlElement(name = "gpsX")
    private float gpsX;

    @XmlElement(name = "gpsY")
    private float gpsY;

    @XmlElement(name = "imgUrl")
    private String imgUrl;

    @XmlElement(name = "placeUrl")
    private String placeUrl;

    @XmlElement(name = "placeAddr")
    private String placeAddr;

    @XmlElement(name = "placeSeq")
    private String placeSeq;

    @Override
    public String toString() {
        return "ExhibitionDetail\n" +
                "title: " + title + '\n' +
                "startDate: " + startDate + '\n' +
                "endDate: " + endDate + '\n' +
                "place: " + place + '\n' +
                "realmName: " + realmName + '\n' +
                "area: " + area + '\n' +
                "subTitle: " + subTitle + '\n' +
                "price: " + price + '\n' +
                "contents1: " + contents1 + '\n' +
                "contents2: " + contents2 + '\n' +
                "url: " + url + '\n' +
                "phone: " + phone + '\n' +
                "imgUrl: " + imgUrl + '\n' +
                "placeUrl: " + placeUrl + '\n' +
                "placeAddress: " + placeAddr + '\n';
    }
}
