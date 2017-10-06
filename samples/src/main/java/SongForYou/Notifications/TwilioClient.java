package SongForYou.Notifications;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioClient {


    private static final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
    private static final String MY_PHONENUMBER = System.getenv("MY_PHONENUMBER");

    public TwilioClient() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public String sendSMS(String phoneNumber, String content)
    {
        Message message = Message
                .creator(new PhoneNumber(phoneNumber), new PhoneNumber(MY_PHONENUMBER), content)
                .create();
        return message.getSid();
    }
}
