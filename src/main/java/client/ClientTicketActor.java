package client;

import com.saf.core.*;
import com.saf.messages.*;

public class ClientTicketActor implements Actor {
    @Override
    public void onReceive(Message msg, ActorContext ctx) throws Exception {
        if (msg instanceof TicketResponse response) {
            System.out.println("[ClientTicketActor] Réponse du Jira:");
            System.out.println("  -> Status: " + response.getTicket().getStatus());
            System.out.println("  -> Ticket ID: " + response.getTicket().getId());

        //} else if (msg instanceof ListTicketsResponse response) {
          //  System.out.println("[ClientTicketActor] Liste des tickets reçus:");
          //  System.out.println("  -> Total: " + response.getCount());
          //  for (String ticket : response.getTickets()) {
          //      System.out.println("     • " + ticket);
          //  }
        }
        else {
            System.out.println("erreur");
        }
    }
}
