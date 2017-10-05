package SongForYou.Contacts;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "SongForYouContactList")
public class ContactsDataItem {

    private String name;
    private String phoneNumber;

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "Name")
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @DynamoDBAttribute(attributeName = "PhoneNumber")
    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
