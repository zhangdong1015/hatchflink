package com.dz.state;

import com.dz.entity.Record;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Date;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自定义source
 * @ClassName RecordSource
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/11/3 13:32
 * @Version 1.0
 */
public class RecordSource  implements SourceFunction<Record>
{

    private MyThreadLocal<AtomicLong> currentValue = new MyThreadLocal<AtomicLong>();
    private boolean isRunning = true;

    @Override
    public void run(SourceContext<Record> sourceContext) throws Exception {
        //SplittableRandom random = new SplittableRandom(10);

        Random random = new Random();
        while (isRunning)
        {


            //SplittableRandom _random = random.split();

            sourceContext.collect(new Record(String.valueOf(Math.abs(random.nextInt(20))),getType(),new Date().getTime(),"eewweww"));
        }

    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    private String getType()
    {
        long c = 1;
        if(null !=currentValue.get())
        {
            AtomicLong atomicLong   =  (AtomicLong)currentValue.get();
            c = atomicLong.incrementAndGet();
        }
        else
        {
            currentValue.set(new AtomicLong(1));
        }

        if(c%6==0){
            return "U";
        }
        else if(c%6==1){
            return "I";
        }
        else if(c%6==5){
            return "D";
        }
        else
        {
            return "U";
        }
    }
}
