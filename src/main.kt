import utils.*
import java.util.Scanner

fun main(args: Array <String>){

    //Declaration d'une liste mutable d'utilisateurs
    val clients: MutableList<Client> = mutableListOf(
        Client("sadate@gmail.com", "Tchamouza", "Sadate",
            "77 725 97 49", "passer", "sadate", arrayOf(Compte(100000, "1123 2212 2231 4567"))),
        Client("john@gmail.com", "wick", "John",
            "77 555 66 33", "passer", "john", arrayOf(Compte(50000, "1123 2212 2001 4567"),
                Compte(numero="4007 5234 5164 9007"))),
        Client("john@gmail.com", "Doe", "John",
            "77 888 99 33", "passer", "doe", arrayOf(Compte(200000, "2003 2212 2231 4567")))
    )

    //Déclaration d'un objet de type Gichetier
    val gichetier: Gichetier = Gichetier("Jean@gmail.com", "Ledoux", "Jean", "77 222 33 11", "entrer", "jean")

    var iteration:Int = 3 //Compteur pour la boucle do{}while()
    var findClient: Client?
    var choix: Int = 0
    val reader = Scanner(System.`in`)

    println("Bienvenue sur le système de gestion de la banque")
    println("Vueillez vous connectez svp")

    do{
        print("Username: ")
        val username: String?  = readLine()
        print("mot de passe : ")
        val password: String? = readLine()
        findClient = clients.find{it.username == username}
        if(username == gichetier.username){
            try{
                if(checkPassword(password, gichetier.password)){
                    menuGichetier()
                    iteration = -1
                }
            }catch (e: Exception){
                println(e.message)
                println("Il vous reste ${iteration--} tentative(s)")
            }
        }else{
            println("Ces informations sont incorrectes")
            iteration--
        }
    }while(iteration >= 0)

    print("Saisissez votre choix : ")
    choix = reader.nextInt()
    while(choix != 0){
        when(choix){
            1 -> {
                val client = ajoutClient()
                clients.add(client)
            }
            2 -> {
                listeDesClients(clients)
            }
            3 -> {
                versementClient(clients)
            }
            4 -> {
                try {
                    virementClient(clients)
                }catch (e: Exception){
                    println(e.message)
                }

            }
            5 -> {
                operationRetrait(clients)
            }
            6 -> {
                infoCompte(clients)
            }
            7 -> {
                affichageEtatCompte(clients)
            }
            8 -> {
                affichageHistoriqueClient(clients)
            }
        }
        menuGichetier()
        print("Saisissez votre choix : ")
        choix = reader.nextInt()
    }

}