package client;

import com.saf.core.*;
import com.saf.messages.*;

public class ClientTicketActor implements Actor {
    @Override
    public void onReceive(Message msg, ActorContext ctx) throws Exception {
        if (msg instanceof TicketResponse response) {
            System.out.println("[ClientTicketActor] Réponse du Jira:");
            System.out.println("  -> Status: " + response.getStatus());
            System.out.println("  -> Ticket ID: " + response.getTicketId());
            System.out.println("  -> Message: " + response.getMessage());

        } else if (msg instanceof ListTicketsResponse response) {
            System.out.println("[ClientTicketActor] Liste des tickets reçue:");
            System.out.println("  -> Total: " + response.getCount());
            for (String ticket : response.getTickets()) {
                System.out.println("     • " + ticket);
            }
        }
        else {
            System.out.println("erreur");
        }
    }
}
