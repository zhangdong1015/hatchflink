package com.dz.operator;

import com.dz.outputag.RejectedWordsTag;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * 将单词转成元组 Tuple2(word,1)
 * @ClassName Tokenizer
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/10/17 11:39
 * @Version 1.0
 */
public class Tokenizer  extends ProcessFunction<String , Tuple2<String,Integer>>
{

    @Override
    public void processElement(String value, Context ctx, Collector<Tuple2<String, Integer>> out) throws Exception {

        String[] tokens = value.toLowerCase().split("\\W+");
        for (String token : tokens) {
            if (token.contains("wh")) {

                // 边流输出
                ctx.output(RejectedWordsTag.rejectedWordsTag, token);
            } else if (token.length() > 0) {
                //主流输出
                out.collect(new Tuple2<>(token, 1));
            }
        }
    }
}
