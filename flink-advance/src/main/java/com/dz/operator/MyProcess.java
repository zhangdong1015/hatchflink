package com.dz.operator;

import com.dz.enums.NumbersegmentEnum;
import com.dz.outputag.NumberOutTagEnum;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.util.Map;

/**
 * @ClassName MyProcess
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/10/18 13:47
 * @Version 1.0
 */
public class MyProcess extends ProcessFunction<Map<String,Object>,Map<String,Object>>
{

  public  MyProcess(){};
    @Override
    public void processElement(Map<String, Object> stringObjectMap, Context context, Collector<Map<String, Object>> collector) throws Exception {
        String numsegment = stringObjectMap.get("numprix").toString();
         String tagId  = NumbersegmentEnum.getSegment(numsegment);

         final OutputTag<String> outputTag = new OutputTag<String>(  NumberOutTagEnum.getTag(tagId)){};
        context.output(outputTag,stringObjectMap.toString());
    }
}
