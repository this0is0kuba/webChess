package pl.edu.agh.webChess.communication;

import pl.edu.agh.webChess.entity.User;

public class Message {

    private String from;
    private String content;

    public Message() {};
    public Message(String from, String content) {

        this.from = from;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
