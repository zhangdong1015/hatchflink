package com.dz.timer;

import com.dz.utils.ConcurrentDateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName TimerMain2
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/11/12 23:42
 * https://blog.csdn.net/u012443641/article/details/105114869/
 * @Version 1.0
 */
public class TimerMain2
{

    private static Logger logger = LoggerFactory.getLogger(TimerMain2.class);

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment environment =StreamExecutionEnvironment.createLocalEnvironment();
        environment.setParallelism(1).addSource(new MySourceTuple2()).
                keyBy(new KeySelector<Tuple2<String, Long>, String>() {
                    @Override
                    public String getKey(Tuple2<String, Long> value) throws Exception {
                        return value.f0;
                    }
                })
                .process(
                new KeyedProcessFunction<String , Tuple2<String, Long>, String>() {

                    private boolean first =true;

                    private Map<String,Long> cache = new ConcurrentHashMap<>();


                    private void update(String key,Long value)
                    {
                        cache.put(key,value);
                    }
                    @Override
                    public void processElement(Tuple2<String, Long> value, Context ctx, Collector<String> out) throws Exception {
                     if(first)
                     {
                         first=false;
                         long  time = System.currentTimeMillis();
                         logger.info("第一次注册时间=={}",time);
                         ctx.timerService().registerProcessingTimeTimer(time+5000);
                     }
                     else
                     {
                         //不是第一次
                         if(cache.containsKey(value.f0))
                         {
                             update(value.f0,value.f1);
                         }
                         else
                         {
                             cache.put(value.f0,value.f1);
                         }


                     }
                    }

                    @Override
                    public void onTimer(long timestamp, KeyedProcessFunction<String, Tuple2<String, Long>, String>.OnTimerContext ctx, Collector<String> out) throws Exception {
                        logger.info("定时器触发=={}=={}",timestamp, ConcurrentDateUtil.dateFormat(timestamp,null));
                        ctx.timerService().registerProcessingTimeTimer(timestamp+5000);
                        String a = cache.toString();
                        out.collect(a);

                    }


        }).print("处理结果：");

        environment.execute("timer test");

    }
}
