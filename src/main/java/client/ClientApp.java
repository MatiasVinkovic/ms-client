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
        ActorRef userClient2 = system.createActor(ClientTicketActor.class, "utilisateur-2");

        // Obtenir la référence distante vers le JiraActor sur ms-restaurant
        ActorRef jiraRemote = new RestRemoteActorRef(dc, "ms-restaurant", "jira-manager");

        //System.out.println("\n[CLIENT] Création de 3 tickets...\n");

        // 1. Créer le premier ticket
        TicketCreateDTO t1 = new TicketCreateDTO("Bug dans la page de login","La page de login ne charge pas correctement sur Firefox",TicketPriority.HIGH);
        System.out.println("\n[CLIENT] Création d'un premier ticket...\n");
        System.out.println(t1.toString());
        jiraRemote.tell(new CreateTicketRequest(t1), userClient);

        // Attendre un peu
        Thread.sleep(500);

        // 2. Créer le deuxième ticket
        TicketCreateDTO t2 = new TicketCreateDTO("Amélioration: Ajouter un mode sombre", "Les utilisateurs demandent un mode sombre pour l'interface", TicketPriority.MEDIUM);
        System.out.println("\n[CLIENT] Création d'un deuxième ticket...\n");
        System.out.println(t2.toString());
        jiraRemote.tell(new CreateTicketRequest(t2), userClient2);

        //Thread.sleep(500);

        // 3. Créer le troisième ticket
        TicketCreateDTO t3 = new TicketCreateDTO("Feature: API REST pour les rapports", "Créer une API REST pour générer des rapports personnalisés", TicketPriority.LOW);
        System.out.println("\n[CLIENT] Création d'un troisième ticket...\n");
        System.out.println(t3.toString());
        jiraRemote.tell(new CreateTicketRequest(t3), userClient);

        //Thread.sleep(500);

        // 4. Lister tous les tickets
        //System.out.println("\n[CLIENT] Demande de la liste de tous les tickets...\n");
        //jiraRemote.tell(new ListTicketsRequest("ALL"), userClient);

        //Thread.sleep(500);

        // 5. Supprimer un ticket (on suppose que le premier est JIRA-1001)
//        System.out.println("\n[CLIENT] Suppression du ticket JIRA-1001...\n");
//        jiraRemote.tell(new DeleteTicketRequest("JIRA-1001"), userClient);
//
//        //Thread.sleep(500);
//
//        System.out.println("\n[CLIENT] Suppression du ticket JIRA-1002...\n");
//        jiraRemote.tell(new DeleteTicketRequest("JIRA-1002"), userClient);
//
//        //Thread.sleep(500);
//
//        System.out.println("\n[CLIENT] Suppression du ticket JIRA-1003...\n");
//        jiraRemote.tell(new DeleteTicketRequest("JIRA-1003"), userClient);

        // 6. Lister les tickets à nouveau
        //System.out.println("\n[CLIENT] Affichage de la liste mise à jour...\n");
        //jiraRemote.tell(new ListTicketsRequest("ALL"), userClient);

        Thread.sleep(2000);

        System.out.println("\n========================================");
        System.out.println("Démonstration terminée!");
        System.out.println("========================================");
    }
}