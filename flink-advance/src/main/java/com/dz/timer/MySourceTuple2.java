package com.dz.timer;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.calcite.shaded.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @ClassName MySourceTuple2
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/11/12 23:26
 * @Version 1.0
 */
public class MySourceTuple2 implements SourceFunction<Tuple2<String, Long>> {


    private boolean isRuning=true;
    private Random random = new Random();
    private List<String> names= Lists.newArrayList("李","照","也","田");
    long num=1;
    @Override
    public void run(SourceContext ctx) throws Exception {

        while (isRuning)
        {
            int index = random.nextInt(4);
            ctx.collect(new Tuple2<>(names.get(index),num));
            num+=1;

        }
    }

    @Override
    public void cancel() {
        isRuning=false;
    }
}
