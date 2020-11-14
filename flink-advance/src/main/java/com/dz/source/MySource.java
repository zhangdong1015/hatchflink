package com.dz.source;


import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @ClassName MySource
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/10/26 13:09
 * @Version 1.0
 */
public class MySource
{
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        // 访问 http://localhost:8082 可以看到Flink Web UI
        conf.setInteger(RestOptions.PORT, 8082);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment(3);


        //自定义source函数
        env.addSource(new SourceFunction<Tuple2<String, Integer>>() {

            private int offset = 0;
            private boolean isRunning = true;
            @Override
            public void run(SourceContext<Tuple2<String, Integer>> sourceContext) throws Exception {

                while (isRunning)
                {
                    Thread.sleep(1000);
                    sourceContext.collect(new Tuple2<>(""+offset,offset));
                    offset++;
                    if(offset==100)
                    {
                        isRunning=false;
                    }

                }
            }

            @Override
            public void cancel() {
                isRunning=false;
            }
        }).print();

        env.execute("my source");
        
    }

}
