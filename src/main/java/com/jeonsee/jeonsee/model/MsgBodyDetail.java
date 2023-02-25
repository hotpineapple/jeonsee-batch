package com.jeonsee.jeonsee.model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "msgBody")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class MsgBodyDetail {
    @XmlElement(name = "seq")
    private int seq;

    @XmlElement(name = "perforInfo")
    private ExhibitionDetail exhibitionDetail;
}
