package client;

import com.saf.core.Actor;
import com.saf.core.ActorContext;
import com.saf.core.ActorRef;
import com.saf.core.Message;
import com.saf.messages.TalkMessage;
import com.saf.messages.TestMessage;

public class TestActor implements Actor {
    @Override
    public void onReceive(Message message, ActorContext ctx) throws Exception {
        if (message instanceof TestMessage msg) {

            System.out.println("[Daniel] Reçu de : " + ctx.getSender().getName());

            // RÉPONSE AUTOMATIQUE
            // Daniel ne sait pas si Matias est local ou distant, il répond juste !
            ctx.reply(new TestMessage("Bien reçu"+ ctx.getSender().getName()+", ici Daniel !"));
        }
    }
}