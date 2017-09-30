package SongForYou.Notifications;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioClient {


    private static final String ACCOUNT_SID = "ACe2b8f26382a73cd9082699d899f68bf1";
    private static final String AUTH_TOKEN = "35b3b94d56c3d9a113f65069a666dd93";
    private static final String MY_PHONENUMBER = "7348871398";

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
