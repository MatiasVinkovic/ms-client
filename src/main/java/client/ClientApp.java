package client;

import com.saf.core.*;
import com.saf.messages.*;
import com.saf.spring.RestRemoteActorRef;
import com.saf.spring.SAF;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * ClientApp - Application de démonstration complète
 * 
 * OBJECTIFS DE LA DÉMO:
 * ✅ Plusieurs clients (Alice, Bob, Charlie)
 * ✅ Plusieurs tâches par client (variées)
 * ✅ Logs détaillés et lisibles
 * ✅ Design patterns intégrés
 * ✅ Différentes catégories de réparation
 * ✅ Différentes priorités
 * ✅ Communication synchrone (ask) avec réponses
 * ✅ Suppression de tickets
 * ✅ Communication locale entre acteurs
 * ✅ Supervision et gestion d'erreurs
 */
@SpringBootApplication(scanBasePackages = {"client", "com.saf.spring"})
@EnableDiscoveryClient
public class ClientApp {

    public static void main(String[] args) throws Exception {
        // Démarrage du framework
        ActorSystem system = SAF.start(ClientApp.class, "ms-client", false, args);
        DiscoveryClient dc = SAF.getContext().getBean(DiscoveryClient.class);

        printHeader();

        // Créer les clients
        ActorRef clientAlice = system.createActor(ClientTicketActor.class, "alice-dupont");
        ActorRef clientBob = system.createActor(ClientTicketActor.class, "bob-martin");
        ActorRef clientCharlie = system.createActor(ClientTicketActor.class, "charlie-bernard");

        // Obtenir la référence distante vers JiraActor
        ActorRef jiraRemote = new RestRemoteActorRef(dc, "ms-restaurant", "jira-manager");

        // ========== DÉMONSTRATION COMPLÈTE ==========
        
        printPhase("PHASE 1: Tickets de sécurité (Priorité: HIGH)");
        createAndSendTicket(jiraRemote, clientAlice,
            "Vulnérabilité SQL Injection détectée",
            "Une faille critique permet l'injection SQL dans le formulaire de connexion. Sécurité",
            TicketPriority.HIGH);
        Thread.sleep(800);

        printPhase("PHASE 2: Tickets de performance (Priorité: HIGH)");
        createAndSendTicket(jiraRemote, clientBob,
            "Application très lente - Performance dégradée",
            "L'application met 10 secondes pour charger la page. Performance critique.",
            TicketPriority.HIGH);
        Thread.sleep(800);

        printPhase("PHASE 3: Tickets de base de données (Priorité: MEDIUM)");
        createAndSendTicket(jiraRemote, clientCharlie,
            "Optimisation requise pour les requêtes BD",
            "Les requêtes sur la base de données sont trop lentes. Nécessite optimisation.",
            TicketPriority.MEDIUM);
        Thread.sleep(800);

        printPhase("PHASE 4: Tickets de réseau (Priorité: HIGH)");
        createAndSendTicket(jiraRemote, clientAlice,
            "Problème de connexion réseau intermittent",
            "Les connexions réseau entre services sont instables. Réseau",
            TicketPriority.HIGH);
        Thread.sleep(800);

        printPhase("PHASE 5: Tickets de bugfix (Priorité: MEDIUM)");
        createAndSendTicket(jiraRemote, clientBob,
            "Bug critique: Application crash au démarrage",
            "L'application crash immédiatement après le lancement. Bug logiciel.",
            TicketPriority.MEDIUM);
        Thread.sleep(800);

        printPhase("PHASE 6: Tickets de hardware (Priorité: LOW)");
        createAndSendTicket(jiraRemote, clientCharlie,
            "Disque dur serveur presque full",
            "L'espace disque du serveur est à 95%. Maintenance préventive.",
            TicketPriority.LOW);
        Thread.sleep(800);

        // ========== LISTER TOUS LES TICKETS (avec ask) ==========
        printPhase("PHASE 7: Affichage de la liste complète des tickets (communication synchrone)");
        Thread.sleep(2000);
        try {
            CompletableFuture<ListTicketsResponse> future = jiraRemote.ask(new ListTicketsRequest(), ListTicketsResponse.class);
            ListTicketsResponse response = future.get(5, TimeUnit.SECONDS);
            System.out.println("📊 Réponse reçue:");
            System.out.println("   Total: " + response.getCount() + " ticket(s)");
            for (String ticketInfo : response.getTickets()) {
                System.out.println("   " + ticketInfo);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des tickets: " + e.getMessage());
        }

        // ========== SUPPRESSION D'UN TICKET ==========
        printPhase("PHASE 8: Suppression d'un ticket");
        Thread.sleep(2000);
        jiraRemote.tell(new DeleteTicketRequest("JIRA-1001"), clientAlice);
        Thread.sleep(1000);

        // ========== LISTER À NOUVEAU POUR VÉRIFIER ==========
        printPhase("PHASE 9: Vérification après suppression");
        Thread.sleep(2000);
        try {
            CompletableFuture<ListTicketsResponse> future2 = jiraRemote.ask(new ListTicketsRequest(), ListTicketsResponse.class);
            ListTicketsResponse response2 = future2.get(5, TimeUnit.SECONDS);
            System.out.println("📊 Liste mise à jour:");
            System.out.println("   Total: " + response2.getCount() + " ticket(s)");
            for (String ticketInfo : response2.getTickets()) {
                System.out.println("   " + ticketInfo);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification: " + e.getMessage());
        }

        // ========== COMMUNICATION LOCALE ENTRE ACTEURS ==========
        printPhase("PHASE 9: Communication locale entre acteurs");
        Thread.sleep(2000);
        System.out.println("Alice envoie un message à Bob...");
        clientAlice.tell(new TalkMessage("Salut Bob, comment ça va ?"), clientBob);
        Thread.sleep(1000);
        System.out.println("Bob répond à Alice...");
        clientBob.tell(new TalkMessage("Ça va bien Alice, merci ! Et toi ?"), clientAlice);
        Thread.sleep(1000);

        // ========== SIMULATION D'ERREUR (SUPERVISION) ==========
        printPhase("PHASE 10: Simulation d'erreur pour démontrer la supervision");
        Thread.sleep(2000);
        System.out.println("Envoi d'une requête invalide (ticket null) pour provoquer une exception...");
        CreateTicketRequest invalidRequest = new CreateTicketRequest(null);
        jiraRemote.tell(invalidRequest, clientAlice);
        Thread.sleep(2000); // Laisser le temps à la supervision de redémarrer l'acteur

        // Attendre que tout se termine
        Thread.sleep(5000);

        printConclusion();
        System.exit(0);
    }

    /**
     * Crée et envoie un ticket
     */
    private static void createAndSendTicket(ActorRef jiraRemote, ActorRef client, 
                                            String title, String description, TicketPriority priority) {
        TicketCreateDTO ticket = new TicketCreateDTO(title, description, priority);
        System.out.println("\n👤 Client: " + client.getName());
        System.out.println("📝 Titre: " + title);
        System.out.println("📋 Description: " + description);
        System.out.println("⚡ Priorité: " + priority);
        System.out.println("➡️  Envoi vers Jira...\n");
        jiraRemote.tell(new CreateTicketRequest(ticket), client);
    }

    // ===== AFFICHAGE =====

    private static void printHeader() {
        System.out.println("\n" + "█".repeat(80));
        System.out.println("█" + " ".repeat(78) + "█");
        System.out.println("█" + String.format("%s%-76s%s", " ", 
            "🚀 DÉMO COMPLÈTE - SYSTÈME DE TICKETS JIRA AVEC ACTEURS", " ") + "█");
        System.out.println("█" + " ".repeat(78) + "█");
        System.out.println("█".repeat(80));
        System.out.println("\n📊 Cette démo démontre:");
        System.out.println("  ✓ Plusieurs clients simultanés");
        System.out.println("  ✓ Plusieurs tâches et priorités différentes");
        System.out.println("  ✓ Catégorisation automatique des tickets");
        System.out.println("  ✓ Assignation à des réparateurs");
        System.out.println("  ✓ Design Patterns (Factory, Observer, Strategy)");
        System.out.println("  ✓ Communication distribuée via Akka");
        System.out.println("  ✓ Communication synchrone (ask) avec réponses");
        System.out.println("  ✓ Suppression de tickets");
        System.out.println("  ✓ Communication locale entre acteurs");
        System.out.println("  ✓ Supervision et redémarrage automatique en cas d'erreur");
        System.out.println("  ✓ Logs détaillés et lisibles");
        System.out.println("\n" + "─".repeat(80) + "\n");
    }

    private static void printPhase(String phase) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("  " + phase);
        System.out.println("=".repeat(80));
    }

    private static void printConclusion() {
        System.out.println("\n" + "█".repeat(80));
        System.out.println("█" + " ".repeat(78) + "█");
        System.out.println("█" + String.format("%s%-76s%s", " ", 
            "✅ DÉMO TERMINÉE AVEC SUCCÈS", " ") + "█");
        System.out.println("█" + " ".repeat(78) + "█");
        System.out.println("█".repeat(80));
        System.out.println("\n📌 Résumé:");
        System.out.println("  • 3 clients créés: Alice, Bob, Charlie");
        System.out.println("  • 6 tickets créés avec différentes priorités");
        System.out.println("  • Liste des tickets récupérée via communication synchrone (ask)");
        System.out.println("  • 1 ticket supprimé (JIRA-1)");
        System.out.println("  • Communication locale entre acteurs (Alice ↔ Bob)");
        System.out.println("  • Erreur simulée et supervision activée (redémarrage automatique)");
        System.out.println("  • Tous les tickets ont été catégorisés automatiquement");
        System.out.println("  • Des réparateurs ont été assignés à chaque ticket");
        System.out.println("  • Chaque réparation a utilisé une stratégie appropriée");
        System.out.println("  • Les logs montrent tous les détails du processus");
        System.out.println("\n");
    }
}