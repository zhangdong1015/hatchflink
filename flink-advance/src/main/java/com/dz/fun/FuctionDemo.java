package com.dz.fun;

import akka.japi.tuple.Tuple3;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @ClassName FuctionDemo
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/11/10 23:52
 * @Version 1.0
 */
public class FuctionDemo
{
    public static void main(String[] args) {
        final StreamExecutionEnvironment environment = new StreamExecutionEnvironment();
        DataStreamSource<Tuple3> singleOutputStreamOperatorv=environment.fromElements(new Tuple3(1,2,3));

        DataStreamSource<Tuple3> singleOutputStreamOperatorv1=environment.fromElements(new Tuple3(1,2,3));

        singleOutputStreamOperatorv.coGroup(singleOutputStreamOperatorv1).where(new KeySelector<Tuple3, Object>() {
            @Override
            public Object getKey(Tuple3 value) throws Exception {
                return value.t2();
            }
        });
    }

}
