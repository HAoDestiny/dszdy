package net.gzhqlf.dszdy.util;

import com.alibaba.fastjson.JSON;

/**
 * Created by DESTINY on 2017/10/19.
 */
public class Json<T> {

    public static String getJSONValue(String data, String key) {
        return JSON.parseObject(data).getString(key);
    }

    public Object parseObject(String data, java.lang.Class<T> clazz) {

        return JSON.parseObject(data, clazz);
    }
}
