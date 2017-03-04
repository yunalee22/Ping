package Twillio;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class SmsSender {

  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = "AC227b9235bff122f13b2cbf172e4b9b94";
  public static final String AUTH_TOKEN = "c18fa63d33d8ffff3bf3aa880a88ec6d";

  public static void main(String[] args) throws TwilioRestException {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

    // Build a filter for the MessageList
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "Ping Received!"));
    params.add(new BasicNameValuePair("To", "+16266366010"));
    params.add(new BasicNameValuePair("From", "+13235249046"));

    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    try {
      Message message = messageFactory.create(params);
    } catch (TwilioRestException e) {
      System.out.println(e.getErrorMessage());
    }
  }
}