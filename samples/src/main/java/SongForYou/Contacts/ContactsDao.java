package SongForYou.Contacts;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class ContactsDao {

    private AmazonDynamoDBClient dynamoDBClient;
    private DynamoDBMapper mapper;

    public ContactsDao() {
        this.dynamoDBClient = new AmazonDynamoDBClient();
        this.mapper = new DynamoDBMapper(dynamoDBClient);
    }

    public String retrieveContactNumber(final String contactName) throws NullPointerException {
        final ContactsDataItem item = loadItemWith(contactName);
        return item.getPhoneNumber();
    }

    private ContactsDataItem loadItemWith(final String key) {
        final ContactsDataItem item = new ContactsDataItem();
        item.setName(key);
        return mapper.load(item);
    }
}
