package com.example.dictionary.merge;

import java.util.List;

public class txtMerge {

    private String reason;
    private int error_code;
    private List<resultMerge> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<resultMerge> getResult() { return result; }

    public void setResult(List<resultMerge> result) {
        this.result = result;
    }

    public static class resultMerge {

        private String id;
        private String pinyin_key;
        private String pinyin;
        private String bihua;
        private String bushou;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPinyin_key() {
            return pinyin_key;
        }

        public void setPinyin_key(String pinyin_key) {
            this.pinyin_key = pinyin_key;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getBihua() {
            return bihua;
        }

        public void setBihua(String bihua) {
            this.bihua = bihua;
        }

        public String getBushou() {
            return bushou;
        }

        public void setBushou(String bushou) {
            this.bushou = bushou;
        }
    }
}
