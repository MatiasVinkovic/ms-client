package client;

import com.saf.core.*;
import com.saf.messages.OrderRequest;
import com.saf.messages.OrderResponse;
import com.saf.spring.RestRemoteActorRef;
import com.saf.spring.SAF;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"client", "com.saf.spring"})
@EnableDiscoveryClient
public class ClientApp {

    public static void main(String[] args) throws Exception {
        // 1. Démarrage du système (Console désactivée)
        ActorSystem system = SAF.start(ClientApp.class, "ms-client", false, args);
        DiscoveryClient dc = SAF.getContext().getBean(DiscoveryClient.class);

        System.out.println("[SAF] Attente de synchronisation Eureka...");
        Thread.sleep(5000);

        // 1. Création de la référence vers l'acteur distant
        ActorRef resto = new RestRemoteActorRef(dc, "ms-restaurant", "tom");

        // 2. Test du mode SYNCHRONE (ask) exigé par le PDF
        System.out.println("[CLIENT] Envoi d'une commande synchrone...");
        resto.ask(new OrderRequest("Pizza", "Matias"), OrderResponse.class)
                .thenAccept(res -> {
                    System.out.println("[CLIENT] Confirmation reçue : " + res.getStatus());
                });

//        // 2. Récupération du DiscoveryClient (nécessaire pour la résolution Eureka)
//        DiscoveryClient dc = SAF.getContext().getBean(DiscoveryClient.class);
//
//        // 3. Création de l'acteur local
//        system.createActor(ClientActor.class, "Matias");
//
//        // --- ATTENTION : On laisse le temps à Eureka de découvrir le restaurant ---
//        System.out.println("[INFO] Attente de la synchronisation Eureka (10s)...");
//        Thread.sleep(2000);
//
//        // 4. Création de la référence distante (Équivalent de la commande 'ref')
//        // On cible le service MS-RESTAURANT et l'acteur ChefGusto
//        ActorRef restoRef = new RestRemoteActorRef(dc, "MS-RESTAURANT", "tom");
//
//        // 5. Création et envoi du message (Équivalent de 'msg' + 'tell')
//        System.out.println("[ACTION] Envoi de la commande de Pizza...");
//        OrderRequest commande = new OrderRequest("Pizza Royale", "Matias");
//
//        restoRef.tell(commande);
//
//        System.out.println("[OK] Message envoyé au restaurant via le code.");
    }
}