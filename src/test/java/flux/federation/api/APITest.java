package flux.federation.api;

import com.consol.citrus.TestCaseMetaInfo;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import flux.federation.configurations.EndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import static org.springframework.http.HttpStatus.OK;


@ContextConfiguration(classes = {EndpointConfig.class})
public class APITest extends TestNGCitrusTestDesigner {


    //Endpoint configurations
    @Autowired
    private HttpClient sandboxAPI;


    @Test
    @CitrusTest(name = "Find How many named brands of used cars are available in the TradeMe UsedCar Category")
    public void getUsedCars() {
        description("This test is to get the number of used cars from Trade Me Used Car category");
        author("Vihar Naik");
        status(TestCaseMetaInfo.Status.READY_FOR_REVIEW);


//      Hardcoded authentication for a test user
        http().client(sandboxAPI).
                send().
                get("Search/Motors/Used.JSON").
                header("Authorization", "OAuth oauth_consumer_key=03FD2C72745B80E843F2D064AD37931B,oauth_token=F8958F36A20B986876825DF2DEC87F35,oauth_signature_method=PLAINTEXT,oauth_signature=9D22FB50054444CC34C233CC803A3725%26E7B00EB0CBAB4D638D6BE41D371F7A62");

//      Here expected value is 2. This can be fetched from database to match the API response
        http().client(sandboxAPI).
                receive().
                response().
                messageType(MessageType.JSON).
                status(OK).
                validate("$.TotalCount", 2).
                extractFromPayload("$.TotalCount", "${UsedCar}");

//      Print in console for number of used cards
        echo("#####" + " ${UsedCar}" + " used cars are available in the TradeMe UsedCars Category" + "#####");

    }

    @Test
    @CitrusTest(name = "Verify 'KIA' brand exists and return the current number of Kia cars listed")
    public void getKIACount() {
        description("This test is to check KIA brand exists and return the current number of cars listed");
        author("Vihar Naik");
        status(TestCaseMetaInfo.Status.READY_FOR_REVIEW);

        variable("make", "KIA");

//      Hardcoded authentication for test user
        http().client(sandboxAPI).
                send().
                get("Search/Motors/Used.JSON?" + "make=${make}").
                header("Authorization", "OAuth oauth_consumer_key=03FD2C72745B80E843F2D064AD37931B,oauth_token=F8958F36A20B986876825DF2DEC87F35,oauth_signature_method=PLAINTEXT,oauth_signature=9D22FB50054444CC34C233CC803A3725%26E7B00EB0CBAB4D638D6BE41D371F7A62");

        http().client(sandboxAPI).
                receive().
                response().
                messageType(MessageType.JSON).
                status(OK).
                validate("$.TotalCount", "@greaterThan('0')@").
                extractFromPayload("$.TotalCount", "${KiaCount}");

//      Print in console if KIA branded car exists.
        echo("#####" + " ${KiaCount}" + " Kia car is available in the TradeMe UsedCars Category" + "#####");

    }

    @Test
    @CitrusTest(name = "Verify Hispano Suiza does not exist in the TradeMe UsedCards Category")
    public void noResultForHispanoSuiza() {
        description("This test is to check Kispano Suiza brand does not exist and return 0 number of cars");
        author("Vihar Naik");
        status(TestCaseMetaInfo.Status.READY_FOR_REVIEW);

        variable("make", "'Hispano%20Suiza'");


//      Hardcoded authentication for test user
        http().client(sandboxAPI).
                send().
                get("Search/Motors/Used.JSON?" + "make=${make}").
                header("Authorization", "OAuth oauth_consumer_key=03FD2C72745B80E843F2D064AD37931B,oauth_token=F8958F36A20B986876825DF2DEC87F35,oauth_signature_method=PLAINTEXT,oauth_signature=9D22FB50054444CC34C233CC803A3725%26E7B00EB0CBAB4D638D6BE41D371F7A62");

        http().client(sandboxAPI).
                receive().
                response().
                messageType(MessageType.JSON).
                status(OK).
                validate("$.TotalCount", "0");

//     Verified none of the Hispano Suiza branded car available.
        echo("#####" +" 'HispanoSuiza' brand does not exist in the TradeMe UsedCars Category" + "#####");

    }

}
