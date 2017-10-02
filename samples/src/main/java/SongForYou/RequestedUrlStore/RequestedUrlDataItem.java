package SongForYou.RequestedUrlStore;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "SongForYouRequestedUrl")
public class RequestedUrlDataItem {

    private String requestedFor;
    private String requestedBy;
    private String url;

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "RequestedFor")
    public String getRequestedFor() { return requestedFor; }

    public void setRequestedFor(String requestedFor) { this.requestedFor = requestedFor; }

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "RequestedBy")
    public String getRequestedBy() { return requestedBy; }

    public void setRequestedBy(String requestedBy) {this.requestedBy = requestedBy; }

    @DynamoDBAttribute(attributeName = "Url")
    public String getUrl() { return this.url; }

    public void setUrl(String url) {this.url = url; }
}
