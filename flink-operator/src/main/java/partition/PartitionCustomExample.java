package partition;

import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName PartitionCustomExample
 * @Description TODO
 * @Author dz
 * @Date 2020/10/10 11:24
 * @Version 1.0
 */
public class PartitionCustomExample
{
    public static void main(String[] args) throws Exception {

        Thread thread =   Thread.currentThread();

        final StreamExecutionEnvironment  env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        DataStream<Tuple2<Integer, String>> dataStream = env.fromElements(
                Tuple2.of(1, "123"), Tuple2.of(2, "abc"),
                Tuple2.of(3, "256"), Tuple2.of(4, "zyx"),
                Tuple2.of(5, "bcd"), Tuple2.of(6, "666"));
        DataStream<Tuple2<Integer, String>> partitioned = dataStream.partitionCustom(new MyPartitioner(), 1);
        partitioned.print();
        env.execute("partitionCustom");
    }

    public static class MyPartitioner implements Partitioner<String>
    {

        private Random rand = new Random();
        private Pattern pattern = Pattern.compile(".*\\d+.*");

        /**
         * key 泛型T 即根据哪个字段进行数据重分配，本例中是Tuple2(Int, String)中的String
         * numPartitons 为当前有多少个并行实例
         * 函数返回值是一个Int 为该元素将被发送给下游第几个实例
         * */
        @Override
        public int partition(String key, int numPartitions) {
            int randomNum = rand.nextInt(numPartitions / 2);

            Matcher m = pattern.matcher(key);
            if (m.matches()) {
                return randomNum;
            } else {
                return randomNum + numPartitions / 2;
            }
        }
    }
}
