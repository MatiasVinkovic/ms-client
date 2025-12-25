package client;

import com.saf.core.*;
import com.saf.messages.*;
import com.saf.spring.RestRemoteActorRef;
import com.saf.spring.SAF;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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

        // ========== LISTER TOUS LES TICKETS ==========
        printPhase("PHASE 7: Affichage de la liste complète des tickets");
        Thread.sleep(2000);
        jiraRemote.tell(new ListTicketsRequest(), clientAlice);

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
        System.out.println("  • Tous les tickets ont été catégorisés automatiquement");
        System.out.println("  • Des réparateurs ont été assignés à chaque ticket");
        System.out.println("  • Chaque réparation a utilisé une stratégie appropriée");
        System.out.println("  • Les logs montrent tous les détails du processus");
        System.out.println("\n");
    }
}