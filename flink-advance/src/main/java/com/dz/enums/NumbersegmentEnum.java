package com.dz.enums;

import com.dz.constant.Constants;

import java.util.Arrays;
import java.util.List;

public enum NumbersegmentEnum
{
    telecom1("1-1", Arrays.asList("133","149","153","173","177","180")),
    telecom2("1-2", Arrays.asList("181","189","199","193","191","190")),

    moblie1("2-1",Arrays.asList("134","135","136","137","138","139","147","148","150","151","152")),
    moblie2("2-2",Arrays.asList("157","158","159","172","178","182","183","184","187","188","198","197","195")),
    unicom1("3-1",Arrays.asList("130","131","132","145","146","155","156","166","175","176")),
    unicom2("3-2",Arrays.asList("185","186","196","1704","1707","1708","1709","171","167")),
    //移动号段 134,135,136,137,138,139,147,148,150,151,152,157,158,159,172,178,182,183,184,187,188,198,197,195
    //电信号段 133,149,153,173,177,180,181,189,199,193,191,190,1700,1701,1702,162,1410
    //联通号段 130,131,132,145,146,155,156,166,175,176,185,186,196,1704,1707,1708,1709,171,167
   ;
    private String  segment;

   private List<String> value;

    NumbersegmentEnum(String segment, List<String> value) {
        this.segment = segment;
        this.value = value;
    }

    public static String  getSegment(String numberPreix)
    {
        for (NumbersegmentEnum en:
            NumbersegmentEnum.values() ) {
            if(en.value.contains(numberPreix))
            {
                return en.segment;
            }

        }
        return Constants.OtherNumberSegment;
    }

}
