package client;

import com.saf.core.*;
import com.saf.messages.OrderRequest;
import com.saf.spring.RestRemoteActorRef;
import com.saf.spring.SAF;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"client", "com.saf.spring"})
@EnableDiscoveryClient
public class ClientApp {

    public static void main(String[] args) throws Exception {
        // 1. Code minimum
        ActorSystem system = SAF.start(ClientApp.class, "ms-client", false, args);
        DiscoveryClient dc = SAF.getContext().getBean(DiscoveryClient.class);


        // Je créer un acteur local, un client par exemple ici
        //si jamais je voulais que mon acteur soit un autre acteur,
        // il suffirait de changer ClientActor.class par le nom de l'autre classe d'acteur que je créerai par exemple LivreurActor.class
        system.createActor(ClientActor.class, "Matias");

        // c'est mieux d'attendre 2 secondes pour être sûr que le service Eureka est bien synchronisé
        System.out.println("[INFO] Attente de la synchronisation Eureka (2s)...");
        Thread.sleep(2000);

        // 4. Création de la référence distante, je veux parler à l'acteur tom que j'ai créer chez le restaurant
        // On cible le service MS-RESTAURANT et l'acteur tom
        // dc, c'est eureka
        ActorRef restoRef = new RestRemoteActorRef(dc, "MS-RESTAURANT", "tom");

        // un acteur envoie un message, on peut imaginer qu'il envoie un message de type OrderRequest, mais
        //encore une fois, ça peut être n'impporte quoi, par exemple CreationTicket, etc.
        System.out.println("[ACTION] Envoi de la commande de Pizza...");
        OrderRequest commande = new OrderRequest("Pizza Royale", "Matias");

        // On envoie le message à l'acteur distant simplement
        restoRef.tell(commande);

        System.out.println("[OK] Message envoyé au restaurant via le code.");
    }
}