package tn.esprit.universite.SMS;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
   // Add SMS
    private final String accountSid = "AC35bab9fceb750f598bc18a7f4c3f6e2c"; // Replace with your actual SID
    private final String authToken = "7b3d08efc8fcb74094d0bbf273675e74"; // Replace with your actual Auth Token
    private final String fromNumber = "++1 859 694 9707"; // Replace with your Twilio phone number

    public SmsService() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String to, String messageBody) {
        // Ensure the phone number is in the correct format for Tunisia
        if (!to.startsWith("+")) {
            if (to.startsWith("0")) {
                to = "+216" + to.substring(1); // Remove leading '0' and add country code
            } else {
                to = "+216" + to; // Prepend country code if no leading '0'
            }
        }

        Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(fromNumber),
                        messageBody)
                .create();
    }
}