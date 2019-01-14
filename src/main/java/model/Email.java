package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Email {
    private String id;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String text;

    public static Builder newEntity() {
        return new Email().new Builder();
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public String toString() {
        return "Email with " +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", cc='" + cc + '\'' +
                ", bcc='" + bcc + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'';
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(String id) {
            Email.this.id = id;
            return this;
        }

        public Builder withFrom(String from) {
            Email.this.from = from;
            return this;
        }

        public Builder withTo(String to) {
            Email.this.to = to;
            return this;
        }

        public Builder withCc(String cc) {
            Email.this.cc = cc;
            return this;
        }

        public Builder withBcc(String bcc) {
            Email.this.bcc = bcc;
            return this;
        }

        public Builder withSubject(String subject) {
            Email.this.subject = subject + getCurrentDateTime();
            return this;
        }

        public Builder withText(String text) {
            Email.this.text = text + getCurrentDateTime();
            return this;
        }

        public Email build() {
            return Email.this;
        }
    }
}
