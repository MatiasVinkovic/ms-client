package client;

import com.saf.core.*;
import com.saf.messages.OrderRequest;
import com.saf.messages.OrderResponse;
import com.saf.messages.TestMessage;
import com.saf.spring.RestRemoteActorRef;
import com.saf.spring.SAF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;



@Component
public class ClientActor implements Actor {



    @Override
    public void onReceive(Message msg, ActorContext context) {
        if (msg instanceof OrderRequest req) {

                System.out.println("[CLIENT] Je passe la commande au restaurant...");


                DiscoveryClient dc = SAF.getContext().getBean(DiscoveryClient.class);
                ActorRef restoRemote = new RestRemoteActorRef(dc, "MS-RESTAURANT", "tom"); // cite: 140, 149
                restoRemote.tell(req, context); // C'est ICI que le sender est injecté

        }
        else if(msg instanceof TestMessage req){
            System.out.println("J'ai reçu un message: " + req.getContent());
            System.out.println("De la part de: " + context.getSender().getName());
        } else if (msg instanceof OrderResponse req) {
            System.out.println("Réponse du restaurant reçue : " + req.getConfirmation());
        }
    }
}