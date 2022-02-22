package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Region region = Region.AP_SOUTH_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
                try {
                    //Create a DynamoDbTable object
                    DynamoDbTable<Employee> mappedTable = enhancedClient.table("Employee", TableSchema.fromBean(Employee.class));
        
                    //Create a KEY object
                    Key key = Key.builder()
                            .partitionValue("vaibhav")
                            .sortValue(11)
                            .build();
        
                    // Get the item by using the key
                    Employee result = mappedTable.getItem(r->r.key(key));
                    return "The email value is "+result.toString();
        
                } catch (DynamoDbException e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }

        return "Hello RESTEasy";
    }
}