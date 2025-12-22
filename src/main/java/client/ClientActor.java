package client;

import com.saf.core.*;
import org.springframework.stereotype.Component;

@Component
public class ClientActor implements Actor {
    @Override
    public void onReceive(Message msg, ActorContext context) {
        System.out.println("[Client] Message reçu sur mon terminal.");
    }
}