package com.suntak.eightdisciplines.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回的类
 *
 * @author Administrator
 */
public class Msg {
    //状态码 100-成功 200失败
    private int code;
    //提示信息
    private String msg;

    //用户要返回给浏览器的数据
    private Map<String, Object> extend = new HashMap<String, Object>();

    public static Msg success() {
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("处理成功！ ");
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("处理失败！ ");
        return result;
    }

    public Msg add(String key, Object value) {
        this.getExtend().put(key, value);
        /*
         * put(K key,V value)
         *将指定的值与此映射中的指定键关联（可选操作）。
         *如果此映射以前包含一个该键的映射关系，
         *则用指定值替换旧值（当且仅当 m.containsKey(k)
         *返回 true 时，才能说映射 m 包含键 k 的映射关系）。
         */
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }


}
