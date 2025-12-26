package client;

import com.saf.core.*;
import com.saf.messages.*;

/**
 * ReparationSupervisor - Acteur LOCAL pour démo de blocage
 * 
 * Reçoit et traite des messages de test.
 * Utilisé pour démontrer le blocage/déblocage avec conservation des messages en queue.
 */
public class ReparationSupervisor implements Actor {
    
    private int messageCount = 0;

    @Override
    public void onReceive(Message msg, ActorContext ctx) throws Exception {
        if (msg instanceof TestMessage test) {
            handleTestMessage(test, ctx);
        }
    }

    private void handleTestMessage(TestMessage test, ActorContext ctx) {
        messageCount++;
        
        printSeparator("📨 MESSAGE REÇU ET TRAITÉ");
        System.out.println("Numéro: " + messageCount);
        System.out.println("Contenu: " + test.getContent());
        System.out.println("Expéditeur: " + (ctx.sender() != null ? ctx.sender().getName() : "Anonyme"));
        System.out.println("");
    }

    private void printSeparator(String title) {
        System.out.println("\n" + "─".repeat(70));
        System.out.println("  " + title);
        System.out.println("─".repeat(70));
    }
}
