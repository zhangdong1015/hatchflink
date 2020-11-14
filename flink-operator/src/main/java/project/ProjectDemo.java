package project;

import akka.japi.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.DataOutputStream;

/**
 * project  demo
 * @ClassName ProjectDemo
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/11/9 22:39
 * @Version 1.0
 */
public class ProjectDemo
{
    public static void main(String[] args) {
        final StreamExecutionEnvironment environment = new StreamExecutionEnvironment();
        DataStreamSource<Tuple3> singleOutputStreamOperatorv=environment.fromElements(new Tuple3(1,2,3));

        //选择部分元素
        singleOutputStreamOperatorv.project(2,0).print();
    }
}
