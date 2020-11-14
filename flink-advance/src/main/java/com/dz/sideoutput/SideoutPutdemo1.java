package com.dz.sideoutput;

import com.dz.entity.WordCountData;
import com.dz.operator.Tokenizer;
import com.dz.outputag.RejectedWordsTag;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * 侧边流
 * @ClassName SideoutPutdemo1
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/10/17 11:38
 * @Version 1.0
 */
public class SideoutPutdemo1
{
    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env =  StreamExecutionEnvironment.getExecutionEnvironment();

        //进入flink系统
        env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        DataStream<String> dataStream   =  env.fromElements(WordCountData.WORDS);

        SingleOutputStreamOperator singleOutputStreamOperator =  dataStream.keyBy(new KeySelector<String,Integer>() {
            @Override
            public Integer getKey(String s) throws Exception {
                return 0;
            }
        }).process(new Tokenizer());

        //输出边流 对应变流
        singleOutputStreamOperator.getSideOutput(RejectedWordsTag.rejectedWordsTag).map(new MapFunction<String,String>() {
            @Override
            public String  map(String value) throws Exception {
                return "rejected: " + value;
            }
        }).print();

        //统计主流中的数据
        SingleOutputStreamOperator<Tuple2<String ,Integer>>   out =  singleOutputStreamOperator
                .keyBy(0)
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .sum(1);

        out.print();

        env.execute("test sideoutPut");
    }

}
