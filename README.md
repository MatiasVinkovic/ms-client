<h1 align="center">🖥️ SAF - Microservice Client</h1>

<p align="center">
  <strong>Interface de démonstration et point d'entrée des requêtes utilisateurs.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Service-Client-blue?style=for-the-badge" alt="Service Client">
  <img src="https://img.shields.io/badge/Status-Demonstration-success?style=for-the-badge" alt="Status">
</p>

---

### 📝 Description
Le `ms-client` est le point de départ de la démo SAF. Il simule l'activité de plusieurs utilisateurs (Alice, Bob, Charlie) qui interagissent avec le système de tickets distribué. 

Il met en évidence :
* La création d'acteurs locaux pour les utilisateurs.
* L'envoi de messages distants (`tell`) vers le serveur Jira.
* Les requêtes synchrones (`ask`) pour récupérer la liste des tickets.
* La démonstration des mécanismes de **blocage** et de **supervision**.

---

### ⚙️ Configuration & Installation
> ⚠️ **Toutes les étapes de configuration (fichiers `.properties`, ports, Eureka) sont centralisées dans la documentation officielle.**

<p align="center">
  <a href="https://steadfast-joke-7dd.notion.site/Documentation-utilisateur-2d13c854685d807c9b54d04518b6be74?source=copy_link">
    <img src="https://img.shields.io/badge/Consulter_la_Configuration-000000?style=for-the-badge&logo=notion&logoColor=white" alt="Lien Notion">
  </a>
</p>

---

### 🚀 Lancement Rapide
1. Assurez-vous qu'Eureka est démarré.
2. Lancez `ms-jira`.
3. Exécutez la classe `ClientApp.java`.

```bash
mvn spring-boot:run
