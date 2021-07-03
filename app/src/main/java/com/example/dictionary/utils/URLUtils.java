package com.example.dictionary.utils;

public class URLUtils {

    public static String pinyinurl = "http://v.juhe.cn/xhzd/querypy?key=";

    public static String bushourul = "http://v.juhe.cn/xhzd/querybs?key=";

    public static String wordurl = "http://v.juhe.cn/xhzd/query?key=";

    //public static String chengyuurl = "http://v.juhe.cn/chengyu/query?key=";
    public static String chengyuurl = "http://apis.juhe.cn/idioms/query?key=";

    public static final String DICTKEY = "d0d9499dc4716f8bac6312ab1af7c0fa";

    //public static final String CHENGYUKEY = "e8a46192a557700f9a8c9b21eab233e5";
    public static final String CHENGYUKEY = "a42c7e86037cfcb1b3457721ce99c6ff";

    public static String getChengyuurl(String word){
        String url = chengyuurl+CHENGYUKEY+"&word="+word;
        return url;
    }
    public static String getWordurl(String word){
        String url = wordurl+DICTKEY+"&word="+word;
        return url;
    }

    public static String getPinyinurl(String word,int page,int pagesize){
        String url = pinyinurl+DICTKEY+"&word="+word+"&page="+page+"&pagesize="+pagesize;
        return url;
    }

    public static String getBushouurl(String bs,int page,int pagesize){
        String url = bushourul+DICTKEY+"&word="+bs+"&page="+page+"&pagesize="+pagesize;
        return url;
    }
}
