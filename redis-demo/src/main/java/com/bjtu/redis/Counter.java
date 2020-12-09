package com.bjtu.redis;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class Counter {
    public String CounterName;
    public String type;
    public String key;
    public int value;
    public Counter(String counterName){
        this.CounterName=counterName;
        initCounter();
    }

    private void initCounter(){
        try {
            String counterName = CounterName;
            ClassLoader loader = FileUtil.class.getClassLoader();
            InputStream stream = loader.getResourceAsStream("counters.json");
            assert stream != null;
            String text = IOUtils.toString(stream, "utf8");
            JSONObject jsonObject = JSONObject.parseObject(text);
            JSONArray array = jsonObject.getJSONArray("counters");
            for (int i = 0; i < array.size(); i++) {
                JSONObject jo = array.getJSONObject(i);
                String an = jo.getString("countername");
                if (an.equals(counterName)) {
                    setKey(jo.getString("keyfields"));
                    setType(jo.getString("type"));
                    if(jo.getInteger("valuefields")!=null) {
                        setValueField(jo.getInteger("valuefields"));
                    }
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setCounterName(String counterName) {
        this.CounterName = counterName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValueField(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "counterName='" + CounterName + '\'' +
                ", type='" + type + '\'' +
                ", key='" + key + '\'' +
                ", valueField=" + value +
                '}';
    }
}
