package reduce;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * https://blog.csdn.net/xianzhen376/article/details/86774348
 * @ClassName ReduceExample
 * @Description TODO
 * @Author dz
 * @Date 2020/9/30 22:19
 * @Version 1.0
 */
public class ReduceExample {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Score> dataStream = senv.fromElements(
                Score.of("Li", "English", 90), Score.of("Wang", "English", 88),
                Score.of("Li", "Math", 85), Score.of("Wang", "Math", 92),
                Score.of("Liu", "Math", 91), Score.of("Liu", "English", 87));

        DataStream<Score> score = dataStream.keyBy("name").reduce(new MyReduceFunction());
        score.print();
        System.out.println("===================");
       //使用匿名函数
        dataStream.keyBy("name").reduce(new ReduceFunction<Score>() {
            @Override
            public Score reduce(Score score, Score t1) throws Exception {
                return Score.of(score.name,"sum",score.score+t1.score);
            }
        }).print();

        //使用lbam表达式
        dataStream.keyBy("name").reduce((s,s1)->Score.of(s.name,"sum",s.score+s1.score)).print();

        senv.execute("reduce job");

    }


    public static class Score {
        public String name;
        public String course;
        public int score;

        public Score() {
        }

        public Score(String name, String course, int score) {
            this.name = name;
            this.course = course;
            this.score = score;
        }

        public static Score of(String name, String course, int score) {
            return new Score(name, course, score);
        }

        @Override
        public String toString() {
            return "(" + this.name + ", " + this.course + ", " + Integer.toString(this.score) + ")";
        }
    }

    public static class MyReduceFunction implements ReduceFunction<Score> {
        @Override
        public Score reduce(Score s1, Score s2) {
            return Score.of(s1.name, "Sum", s1.score + s2.score);
        }
    }
}
