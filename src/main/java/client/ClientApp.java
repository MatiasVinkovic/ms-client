package client;

import com.saf.core.*;
import com.saf.messages.OrderRequest;
import com.saf.messages.TalkMessage;
import com.saf.messages.TestMessage;
import com.saf.spring.RestRemoteActorRef;
import com.saf.spring.SAF;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"client", "com.saf.spring"})
@EnableDiscoveryClient
public class ClientApp {

    public static void main(String[] args) throws Exception {
        // 1. Code minimum
        ActorSystem system = SAF.start(ClientApp.class, "ms-client", false, args);
        DiscoveryClient dc = SAF.getContext().getBean(DiscoveryClient.class);


        // On crée un acteur local "Matias" dans le microservice client
        ActorRef matias = system.createActor(ClientActor.class, "Matias");
        ActorRef daniel = system.createActor(TestActor.class, "Daniel");

        //envoyer un message à matias  de la part de daniel
        matias.tell(new TestMessage("Salut Daniel, ça va ?"), daniel);



        ActorRef tom = new RestRemoteActorRef(dc, "MS-RESTAURANT", "tom");

        //envoyer un message à tom de la part de matias
        tom.tell(new OrderRequest("Pizza Margherita"), matias);


    }
}