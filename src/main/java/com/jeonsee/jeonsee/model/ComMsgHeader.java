package com.jeonsee.jeonsee.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "comMsgHeader")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComMsgHeader {

    @XmlElement(name = "SuccessYN")
    private String successYn;

    @XmlElement(name = "ReturnCode")
    private String returnCode;

    @XmlElement(name = "ErrMsg")
    private String errMsg;

    public String getSuccessYn() {
        return successYn;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public boolean isValid() {
        return getSuccessYn().equalsIgnoreCase("Y");
    }
}
