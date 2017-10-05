package SongForYou.Notifications;

public class NotificationSender {
    private TwilioClient twilioClient;

    private static String MESSAGE_CONTENT = "Your friend has sent you a song, please ask Alexa to 'Play the songs for me'";

    public NotificationSender() {
        this.twilioClient = new TwilioClient();
    }

    public String sendNotificationTo(String phoneNumber) {
        return twilioClient.sendSMS(phoneNumber, MESSAGE_CONTENT);
    }
}
