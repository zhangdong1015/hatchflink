package com.dz.outputag;

import org.apache.flink.util.OutputTag;

/**
 * @ClassName RejectedWordsTag
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/10/17 13:02
 * @Version 1.0
 */
public class RejectedWordsTag
{


    public static final OutputTag<String> rejectedWordsTag = new OutputTag<String>("rejected"){};

}
