package com.jeonsee.jeonsee.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.jeonsee.jeonsee.util.LocalDateAdapter;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "msgBody")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class MsgBody {
    @XmlElement(name = "totalCount")
    private int totalCount;

    @XmlElement(name = "cPage")
    private int cPage;

    @XmlElement(name = "rows")
    private int rows;

    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "from")
    private LocalDate from;

    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "to")
    private LocalDate to;

    @XmlElement(name = "perforList")
    private List<Exhibition> exhibitionList;
}
