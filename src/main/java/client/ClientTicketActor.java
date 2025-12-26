package client;

import com.saf.core.*;
import com.saf.messages.*;

/**
 * ClientTicketActor - Reçoit et affiche les réponses du serveur Jira
 * Observer Pattern: observe les mises à jour de tickets
 */
public class ClientTicketActor implements Actor {
    
    @Override
    public void onReceive(Message msg, ActorContext ctx) throws Exception {
        if (msg instanceof TicketResponse response) {
            handleTicketResponse(response, ctx);
        } else if (msg instanceof ListTicketsResponse response) {
            handleListTicketsResponse(response, ctx);
        } else if (msg instanceof TalkMessage talk) {
            handleTalkMessage(talk, ctx);
        } else {
            System.out.println("⚠️  Message non reconnu: " + msg.getClass().getSimpleName());
        }
    }

    /**
     * Gère les réponses de création de tickets
     */
    private void handleTicketResponse(TicketResponse response, ActorContext ctx) {
        System.out.println("\n" + "┌" + "─".repeat(76) + "┐");
        System.out.println("│ 📬 RÉPONSE REÇUE DU SERVEUR JIRA");
        System.out.println("├" + "─".repeat(76) + "┤");
        
        if (response.getTicket() != null) {
            TicketDTO ticket = response.getTicket();
            System.out.println("│ ✓ " + response.getMessage());
            System.out.println("│ ID du ticket: " + ticket.getId());
            System.out.println("│ Titre: " + ticket.getTitle());
            System.out.println("│ Statut: " + ticket.getStatus());
            System.out.println("│ Priorité: " + ticket.getPriority());
        } else {
            System.out.println("│ ⚠️  " + response.getMessage());
        }
        
        System.out.println("└" + "─".repeat(76) + "┘\n");
    }

    /**
     * Gère l'affichage de la liste des tickets
     */
    private void handleListTicketsResponse(ListTicketsResponse response, ActorContext ctx) {
        System.out.println("\n" + "┌" + "─".repeat(76) + "┐");
        System.out.println("│ 📊 LISTE COMPLÈTE DES TICKETS REÇUE");
        System.out.println("├" + "─".repeat(76) + "┤");
        System.out.println("│ Total tickets: " + response.getCount());
        System.out.println("│");
        
        for (String ticket : response.getTickets()) {
            System.out.println("│ " + ticket);
        }
        
        System.out.println("│");
        System.out.println("└" + "─".repeat(76) + "┘\n");
    }

    /**
     * Gère les messages de conversation
     */
    private void handleTalkMessage(TalkMessage talk, ActorContext ctx) {
        System.out.println("\n💬 Message reçu: " + talk.getContent());
        System.out.println("   De: " + (ctx.sender() != null ? ctx.sender().getName() : "Inconnu") + "\n");
    }
}
