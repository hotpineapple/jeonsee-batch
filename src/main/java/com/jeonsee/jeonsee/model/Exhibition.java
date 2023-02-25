package com.jeonsee.jeonsee.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.jeonsee.jeonsee.util.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Document(collection = "exhibition")
@XmlRootElement(name = "perforList")
@XmlAccessorType(XmlAccessType.FIELD)
public class Exhibition {
    @Id
    private BigInteger _id;

    @XmlElement(name = "seq")
    private int seq;

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

    @XmlElement(name = "thumbnail")
    private String thumbnail;

    @XmlElement(name = "gpsX")
    private float gpsX;

    @XmlElement(name = "gpsY")
    private float gpsY;

    private ExhibitionDetail detail;

    public void setDetail(ExhibitionDetail detail){
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exhibition that = (Exhibition) o;
        return seq == that.seq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
