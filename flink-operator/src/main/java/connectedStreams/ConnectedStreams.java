package connectedStreams;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

/**
 * 连接流
 * union的区别：
 * .ConnectedStreamed只能连接两个流，而union可以连接多于两个流
 * union操作连接的流类型必须一致，ConnectedStream连接的两个流可以不一致
 * ConnectedStreams会对两个流的数据应用不同的处理方法，并且双流之间可以共享状态
 * @ClassName ConnectedStreams
 * @Description TODO
 * @Author dz
 * @Date 2020/10/11 21:21
 * @Version 1.0
 */
public class ConnectedStreams
{
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream1 =env.fromElements("1","2","3","4");
        DataStream<Integer> dataStream2 =env.fromElements(5,7,9,11);

        dataStream1.connect(dataStream2).map(new CoMapFunction<String, Integer, Object>() {
            @Override
            public Object map1(String s) throws Exception {
                return s;
            }

            @Override
            public Object map2(Integer integer) throws Exception {
                return integer;
            }
        }).print();

        env.execute("ConnectedStreams-job");
    }
}
