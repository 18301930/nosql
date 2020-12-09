package com.bjtu.redis;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Count {
    Action action;
    private final ArrayList<String> readCounter;
    private final ArrayList<String> writeCounter;

    private final ArrayList<Counter> readCounterObj;
    private final ArrayList<Counter> writeCounterObj;

    public Count(Action actionplus){
        this.action= actionplus;
        readCounter= actionplus.getReadCounter();
        writeCounter= actionplus.getWriteCounter();
        readCounterObj = new ArrayList<Counter>();
        writeCounterObj = new ArrayList<Counter>();
        initCounters();
    }

    private void initCounters(){
        for(String readName:readCounter){
            Counter counter = new Counter(readName);
         //   new Counter(counter);
            readCounterObj.add(counter);
        }
        for(String writeName:writeCounter){
            Counter counter = new Counter(writeName);
           // new Counter(counter);
            writeCounterObj.add(counter);
        }
    }
    public void Counts(){
        //读所有的read counter
        for(Counter counter : readCounterObj){
            switch (counter.type){
                case "num":
                    System.out.println("The Value Of "+counter.key+" Is "+ RedisUtil.getValueNum(counter.key));
                    break;
                case "hash":
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
                    System.out.print("begin time>>");
                    Scanner ms = new Scanner(System.in);
                    String begin = ms.nextLine();
                    System.out.print("end time>>");
                    Scanner ms2 = new Scanner(System.in);
                    String end = ms2.nextLine();

                    try {
                        Date from = format.parse(begin);
                        Date to = format.parse(end);

                        //得到time-valueField的哈希表
                        Map<String,String> map = RedisUtil.getHashMap(counter.key);
                        Set<String> keys = map.keySet();
                        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        int ret = 0;
                        for(String key:keys){
                            Date it = format2.parse(key);
                            if(it.after(from)&&it.before(to)){
                                String value = map.get(key);
                                ret+=Integer.parseInt(value);
                            }
                        }
                        System.out.println("本时间段count一共增加了"+ret);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    break;
                case "list":
                    List<String> log = RedisUtil.getArray(counter.key);
                    for(String s:log){
                        System.out.println(s);
                    }
                    break;
                case "str":
                    System.out.println(RedisUtil.getValueNum(counter.key));
                    break;
                case "set":
                    Set<String> times = RedisUtil.getSet(counter.key);
                    for(String time : times){
                        System.out.println(time);
                    }
                    break;
                default:
                    System.out.println(counter);
                    break;
            }
        }
        //写所有的write counter
        for(Counter counter : writeCounterObj){
            switch (counter.type){
                case "num":
                    RedisUtil.setIncrNum(counter.key,counter.value);
                    break;
                case "hash":
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    RedisUtil.setHashPush(counter.key,timestamp.toString(),counter.value+"");
                    break;
                case "list":
                    String time = new Timestamp(System.currentTimeMillis()).toString();
                    int value = counter.value;
                    String write = "The counter increase "+value+" at "+time;
                    RedisUtil.writeList(counter.key,write);
                    break;
                case "set":
                    String time2 = new Timestamp(System.currentTimeMillis()).toString();
                    RedisUtil.addSet(counter.key,time2);
                    break;
                default:
                    System.out.println(counter);
                    break;
            }
        }
    }


}
