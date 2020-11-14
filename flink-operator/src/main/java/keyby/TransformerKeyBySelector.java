package keyby;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Objects;

/**
 * @ClassName TransformerKeyBySelector
 * @Description TODO
 * @Author dz
 * @Date 2020/10/10 9:39
 * @Version 1.0
 */
public class TransformerKeyBySelector
{

    public static class Person {
        public String name;            // 姓名
        public Integer age;             // 年龄

        public Person() {
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return this.name + ": 年龄 " + this.age;
        }
    }

    public static void main(String[] args) throws Exception {
      final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Person> personDS = env.fromElements(
                new Person("张三", 16),
                new Person("李四", 18),
                new Person("王老五", 35),
                new Person("赵小六", 23),
                new Person("小多米", 12));

        KeyedStream<Person,String>  keyedStream =personDS.keyBy(new KeySelector<Person, String>() {
            @Override
            public String getKey(Person person) throws Exception {
                return person.age>18?"a":"u";
            }
        });

        keyedStream.sum("age").map(new MapFunction<Person, Object>() {
            @Override
            public Object map(Person person) throws Exception {
                return person.age;
            }
        }).print();

        env.execute("KeySelector");
    }


}
