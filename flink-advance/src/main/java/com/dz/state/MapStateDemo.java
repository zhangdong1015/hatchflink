package com.dz.state;

import com.dz.entity.Record;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * MapState
 * @ClassName MapState
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/11/3 13:22
 * @Version 1.0
 */
public class MapStateDemo
{
    public static void main(String[] args) throws Exception {
       final StreamExecutionEnvironment  executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        executionEnvironment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        //根据UserId分组
        KeyedStream<Record, String> keyedStream  =  executionEnvironment.addSource(new RecordSource())
                .keyBy(Record::getUuid);

        keyedStream.flatMap(new MapStateFunction()).print();
        executionEnvironment.execute("mapState job");
    }



    public static  class  MapStateFunction extends RichFlatMapFunction<Record, Tuple3<String,String,Long>>{


        /**
         * MapState
         */
        private MapState<String,Long> optState;

        @Override
        public void open(Configuration parameters) throws Exception {
            MapStateDescriptor<String, Long> behaviorMapStateDescriptor = new MapStateDescriptor<String, Long>("optMap", Types.STRING, Types.LONG);
            optState = getRuntimeContext().getMapState(behaviorMapStateDescriptor);
        }

        @Override
        public void flatMap(Record record, Collector<Tuple3<String, String, Long>> collector) throws Exception {
          long count =1;
          if(optState.contains(record.getOpType()))
          {
              count = optState.get(record.getOpType())+1;
          }
            optState.put(record.getOpType(),count);
            collector.collect(Tuple3.of(record.getUuid(),record.getOpType(),count));

        }
    }


}
