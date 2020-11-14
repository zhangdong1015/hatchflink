package aggregation;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @ClassName AggregationDemo
 * @Description TODO
 * @Author dz
 * @Date 2020/10/13 8:49
 * @Version 1.0
 */
public class AggregationDemo
{
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple3<Integer, Integer, Integer>> tupleStream = env.fromElements(
                Tuple3.of(0, 0, 0), Tuple3.of(0, 1, 1), Tuple3.of(0, 2, 2),
                Tuple3.of(1, 0, 6), Tuple3.of(1, 1, 7), Tuple3.of(1, 0, 8));

        DataStream<Tuple3<Integer, Integer, Integer>> sumStream = tupleStream.keyBy(0).sum(1);
        sumStream.print();
        env.execute();

    }
}
