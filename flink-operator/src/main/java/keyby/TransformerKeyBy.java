package keyby;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple0;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * age
 * @ClassName TransformerKeyBy
 * @Description TODO
 * @Author dz
 * @Date 2020/10/9 22:04
 * @Version 1.0
 */
public class TransformerKeyBy
{


    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env =  StreamExecutionEnvironment.getExecutionEnvironment();

        //加载数据源 并执行flatMap转换
        DataStream<String> dataStream = env.fromElements("one two three,four five six one one  four ").flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                for (int i = 0; i < s.split("\\W+").length; i++) {
                    collector.collect(s.split("\\W+")[i]);
                }
            }
        });

        //将数据转换成Tuple
        DataStream<Tuple2<String,Integer>> dataStream1 = dataStream.map(new MapFunction<String, Tuple2<String,Integer>>() {
            @Override
            public Tuple2<String,Integer> map(String s) throws Exception {

                return new Tuple2<String,Integer>(s,1);
            }
        });

        //两个值计算相加
        dataStream1.keyBy(0).reduce((w,w1)->new Tuple2 (w.f0,w.f1+w1.f1)).print();


        env.execute("keyby");
    }
}
