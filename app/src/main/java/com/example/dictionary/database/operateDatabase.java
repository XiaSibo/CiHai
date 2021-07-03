package com.example.dictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.example.dictionary.merge.ChineseCharacter;
import com.example.dictionary.merge.infoMerge;

import java.util.ArrayList;
import java.util.List;

public class operateDatabase {
    private static SQLiteDatabase db;
    public static void initDatabase(Context context){
        openDatabase opendb = new openDatabase(context);
        db = opendb.getWritableDatabase();
    }

    public static void insertObjToInfo(ChineseCharacter.resultMsg.character cmsg){
        ContentValues cv = new ContentValues();
        cv.put("id",cmsg.getId());
        cv.put("zi",cmsg.getZi());
        cv.put("py",cmsg.getPy());
        cv.put("wubi",cmsg.getWubi());
        cv.put("pinyin",cmsg.getPinyin());
        cv.put("bushou",cmsg.getBushou());
        cv.put("bihua",cmsg.getBihua());
        //long loc =
        db.insert("info", null, cv);
    }

    public static void insertObjsToInfo(List<ChineseCharacter.resultMsg.character> clist){
        if (clist.size()>0) {
            for (int i = 0; i < clist.size(); i++) {
                ChineseCharacter.resultMsg.character cmsg = clist.get(i);
                try {
                    insertObjToInfo(cmsg);
                }catch (Exception e){
                    Log.i("animee", "insertObjsToInfo: "+cmsg.getZi()+"is existed!");
                }
            }
        }
    }

    public static List<ChineseCharacter.resultMsg.character>findCharacterByBopomofo(String bopomofo,int page,int pageSize){
        List<ChineseCharacter.resultMsg.character> clist = new ArrayList<>();
        String sql = "select * from info where py=? or py like ? or py like ? or py like ? limit ?,?";
        int start = (page - 1)*pageSize;
        int end = page * pageSize;
        String temp1 = bopomofo+",%";
        String temp2 = "%,"+bopomofo+",%";
        String temp3 = "%,"+bopomofo;
        Cursor cursor = db.rawQuery(sql, new String[]{bopomofo, temp1, temp2, temp3, start + "", end + ""});
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String zi = cursor.getString(cursor.getColumnIndex("zi"));
            String py = cursor.getString(cursor.getColumnIndex("py"));
            String wubi = cursor.getString(cursor.getColumnIndex("wubi"));
            String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
            String bushou = cursor.getString(cursor.getColumnIndex("bushou"));
            String bihua = cursor.getString(cursor.getColumnIndex("bihua"));
            ChineseCharacter.resultMsg.character cmsg = new ChineseCharacter.resultMsg.character(id, zi, bopomofo, wubi, pinyin, bushou, bihua);
            clist.add(cmsg);
        }
        return clist;
    }

    public static List<ChineseCharacter.resultMsg.character>findCharacterByRadical(String radical,int page,int pageSize){
        List<ChineseCharacter.resultMsg.character> clist = new ArrayList<>();
        String sql = "select * from info where bushou=? limit ?,?";
        int start = (page - 1)*pageSize;
        int end = page * pageSize;
        Cursor cursor = db.rawQuery(sql, new String[]{radical, start + "", end + ""});
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String zi = cursor.getString(cursor.getColumnIndex("zi"));
            String py = cursor.getString(cursor.getColumnIndex("py"));
            String wubi = cursor.getString(cursor.getColumnIndex("wubi"));
            String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
            String bushou = cursor.getString(cursor.getColumnIndex("bushou"));
            String bihua = cursor.getString(cursor.getColumnIndex("bihua"));
            ChineseCharacter.resultMsg.character cmsg = new ChineseCharacter.resultMsg.character(id, zi, py, wubi, pinyin, bushou, bihua);
            clist.add(cmsg);
        }
        return clist;
    }

    public static void insertObjToMsg(infoMerge.resultData rdata){
        ContentValues cv = new ContentValues();
        cv.put("id",rdata.getId());
        cv.put("zi",rdata.getZi());
        cv.put("py",rdata.getPy());
        cv.put("wubi",rdata.getWubi());
        cv.put("pinyin",rdata.getPinyin());
        cv.put("bushou",rdata.getBushou());
        cv.put("bihua",rdata.getBihua());
        String jijie = listToString(rdata.getJijie());
        cv.put("jijie",jijie);
        String xiangjie = listToString(rdata.getXiangjie());
        cv.put("xiangjie",xiangjie);
        db.insert("character_msg",null,cv);
    }

    public static infoMerge.resultData findMsgByMsg(String c){
        String sql = "select * from character_msg where zi=?";
        Cursor cursor = db.rawQuery(sql, new String[]{c});
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String zi = cursor.getString(cursor.getColumnIndex("zi"));
            String py = cursor.getString(cursor.getColumnIndex("py"));
            String wubi = cursor.getString(cursor.getColumnIndex("wubi"));
            String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
            String bushou = cursor.getString(cursor.getColumnIndex("bushou"));
            String bihua = cursor.getString(cursor.getColumnIndex("bihua"));
            String jijie = cursor.getString(cursor.getColumnIndex("jijie"));
            String xiangjie = cursor.getString(cursor.getColumnIndex("xiangjie"));
            List<String> jijielist = stringToList(jijie);
            List<String> xiangxilist = stringToList(xiangjie);
            infoMerge.resultData rdata = new infoMerge.resultData(id, zi, py, wubi, pinyin, bushou, bihua, jijielist, xiangxilist);
            return rdata;
        }
        return null;
    }

    public static List<String>stringToList(String msg){
        List<String>list = new ArrayList<>();
        if (!TextUtils.isEmpty(msg)) {
            String[] arr = msg.split("\\|");
            for (int i = 0; i < arr.length; i++) {
                String s = arr[i].trim();
                if (!TextUtils.isEmpty(s)) {
                    list.add(s);
                }
            }
        }
        return list;
    }
    public static String listToString(List<String>list){
        StringBuilder sb = new StringBuilder();
        if (list!=null&&!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String msg = list.get(i);
                msg+="|";
                sb.append(msg);
            }
        }
        return sb.toString();
    }

    public static void insertMsgToCollect(String zi){
        ContentValues cv = new ContentValues();
        cv.put("zi",zi);
        db.insert("collect_msg",null, cv);
    }

    public static void deleteMsgFromCollect(String zi){
        String sql = "delete from collect_msg where zi = ?";
        db.execSQL(sql,new Object[]{zi});
    }

    public static boolean isExistMsgInCollect(String zi){
        String sql = "select * from collect_msg where zi = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{zi});
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public static List<String>findMsgInCollect(){
        String sql = "select * from collect_msg";
        Cursor cursor = db.rawQuery(sql,null);
        List<String>list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String zi = cursor.getString(cursor.getColumnIndex("zi"));
            list.add(zi);
        }
        return list;
    }
}
