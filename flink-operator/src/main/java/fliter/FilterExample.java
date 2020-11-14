package fliter;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FilterExample
 * @Description TODO
 * @Author dz
 * @Date 2020/10/12 22:56
 * @Version 1.0
 */
public class FilterExample
{
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
       DataStream<Integer> dataStream = env.fromElements(1,2,4,-5,9);
        dataStream.filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer integer) throws Exception {
                if(integer<0){
                return false;}
                return true;
            }
        }).print();
        dataStream.filter(x->x>0).print();

        env.execute("fliter");
    }
}
