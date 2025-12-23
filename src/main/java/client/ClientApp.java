package client;

import com.saf.core.*;
import com.saf.messages.*;
import com.saf.spring.RestRemoteActorRef;
import com.saf.spring.SAF;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"client", "com.saf.spring"})
@EnableDiscoveryClient
public class ClientApp {

    public static void main(String[] args) throws Exception {
        // Démarrage du framework
        ActorSystem system = SAF.start(ClientApp.class, "ms-client", false, args);
        DiscoveryClient dc = SAF.getContext().getBean(DiscoveryClient.class);

        System.out.println("========================================");
        System.out.println("Client JIRA - Application de démonstration");
        System.out.println("========================================");

        // Créer un acteur local qui représente le client
        ActorRef userClient = system.createActor(ClientTicketActor.class, "utilisateur-1");

        // Obtenir la référence distante vers le JiraActor sur ms-restaurant
        ActorRef jiraRemote = new RestRemoteActorRef(dc, "ms-restaurant", "jira-manager");

        System.out.println("\n[CLIENT] Création de 3 tickets...\n");

        // 1. Créer le premier ticket
        userClient.tell(new CreateTicketRequest(
            "Bug dans la page de login",
            "La page de login ne charge pas correctement sur Firefox",
            "HIGH"
        ), userClient);

        jiraRemote.tell(new CreateTicketRequest(
            "Bug dans la page de login",
            "La page de login ne charge pas correctement sur Firefox",
            "HIGH"
        ), userClient);

        // Attendre un peu
        Thread.sleep(500);

        // 2. Créer le deuxième ticket
        System.out.println("\n[CLIENT] Création d'un deuxième ticket...\n");
        jiraRemote.tell(new CreateTicketRequest(
            "Amélioration: Ajouter un mode sombre",
            "Les utilisateurs demandent un mode sombre pour l'interface",
            "MEDIUM"
        ), userClient);

        Thread.sleep(500);

        // 3. Créer le troisième ticket
        System.out.println("\n[CLIENT] Création d'un troisième ticket...\n");
        jiraRemote.tell(new CreateTicketRequest(
            "Feature: API REST pour les rapports",
            "Créer une API REST pour générer des rapports personnalisés",
            "LOW"
        ), userClient);

        Thread.sleep(500);

        // 4. Lister tous les tickets
        System.out.println("\n[CLIENT] Demande de la liste de tous les tickets...\n");
        jiraRemote.tell(new ListTicketsRequest("ALL"), userClient);

        Thread.sleep(500);

        // 5. Supprimer un ticket (on suppose que le premier est JIRA-1001)
        System.out.println("\n[CLIENT] Suppression du ticket JIRA-1001...\n");
        jiraRemote.tell(new DeleteTicketRequest("JIRA-1001"), userClient);

        Thread.sleep(500);

        // 6. Lister les tickets à nouveau
        System.out.println("\n[CLIENT] Affichage de la liste mise à jour...\n");
        jiraRemote.tell(new ListTicketsRequest("ALL"), userClient);

        Thread.sleep(1000);

        System.out.println("\n========================================");
        System.out.println("Démonstration terminée!");
        System.out.println("========================================");
    }
}