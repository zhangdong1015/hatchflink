package com.dz.outputag;

import com.dz.constant.Constants;

public enum NumberOutTagEnum
{
    //电信1号段
    telecom1("1-1","telecom1"),
    //电信2号段
    telecom2("1-2","telecom2"),
    //移动1号段
    moblie1("2-1","moblie1"),
    //移动2号段
    moblie2("2-2","moblie2"),

    //联通1号段
    unicom1("3-1","unicom1"),
    unicom2("3-2","unicom2"),
    ;
    private String type;
  private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NumberOutTagEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public  static String  getTag(String type)
    {
        for (NumberOutTagEnum numberOutTagEnum:
        NumberOutTagEnum.values()) {
            if(type.equals(numberOutTagEnum.type))
            {
              return   numberOutTagEnum.getName();
            }

        }
       return Constants.OtherNumberSegment;
    }
}
