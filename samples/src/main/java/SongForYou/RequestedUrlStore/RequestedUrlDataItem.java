package SongForYou.RequestedUrlStore;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.HashMap;
import java.util.Map;

@DynamoDBTable(tableName = "SongForYouRequestedUrl")
public class RequestedUrlDataItem {

    private String requestedFor;
    private Map<String, String> url;

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "RequestedFor")
    public String getRequestedFor() { return requestedFor; }

    public void setRequestedFor(String requestedFor) { this.requestedFor = requestedFor; }

    @DynamoDBAttribute(attributeName = "Url")
    public Map<String, String> getUrl() { return this.url; }

    public void setUrl(String requestedBy, String url) {
        if (this.url == null) {
            this.url = new HashMap<>();
        }
        this.url.put(requestedBy, url);
    }

    public void setUrl(Map<String, String> url) { this.url = url; }
}
