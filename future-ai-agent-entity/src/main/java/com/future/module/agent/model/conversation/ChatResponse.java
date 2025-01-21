package com.future.module.agent.model.conversation;

public class ChatResponse {
    private int retcode;
    private String retmsg;
    private Data data;

    // Getter and Setter
    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String answer;
        private Object reference; // 可以根据实际需要调整类型
        private Object audioBinary; // 可以根据实际需要调整类型
        private Object id; // 可以根据实际需要调整类型

        // Getter and Setter
        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Object getReference() {
            return reference;
        }

        public void setReference(Object reference) {
            this.reference = reference;
        }

        public Object getAudioBinary() {
            return audioBinary;
        }

        public void setAudioBinary(Object audioBinary) {
            this.audioBinary = audioBinary;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }
    }
}