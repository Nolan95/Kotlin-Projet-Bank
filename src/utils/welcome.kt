package utils
import Client
import Gichetier
import Compte
import java.util.*

val reader = Scanner(System.`in`)

fun Gichetier.verificationAcces(username: String?, password: String?) {

    if(this.username === username) {
        try{
            checkPassword(password, this.password)
           //     menuGichetier()
        }catch (e: Exception){
            print(e.message)
        }
    }
}

fun checkPassword(password: String?, password_2: String?) =
    if(password == password_2) true else throw Exception("Mauvais mot de passe")


fun List<Client>.verificationAcces(username: String?, password: String?) {
    val findClient:Client? = this.find{it.username === username}
    try{
        //if(checkPassword(password, findClient?.password))
            menuClient()
    }catch (e: Exception){
        print(e.message)
    }
}


fun menuGichetier(){
    println("Bienvenue, que voulez vous faire ?")
    println("1. Ajouter un client")
    println("2. Liste des clients")
    println("3. Effectuer une operation de versement")
    println("4. Effectuer une operation de virement")
    println("5. Operation de retrait")
    println("6. Information d'un compte")
    println("7. Affichage de l'etat d'un compte")
    println("8. Historique des comptes d'un client")
    println("0. Quitter")
}

fun menuClient(){
    println("1. Informations de mon compte")
    println("2. Affichage de les comptes")
    println("3. Etat de mon compte")
    println("4. Effectuer un virement")
}

fun ajoutClient(): Client{
    print("Email client")
    val emailClient = readLine()!!
    print("Nom client")
    val nomClient = readLine()!!
    print("Prenom client")
    val prenomClient = readLine()!!
    print("Telephone au format xx xxx xx xx: ")
    val telephone = readLine()!!
    print("Nom d'utilisateur : ")
    val username = readLine()!!
    print("Mot de passe : ")
    val password = readLine()!!
    print("Saisir le numero de compte au format xxxx xxxx xxxx xxxx : ")
    val numero = readLine()!!

    val client = Client(emailClient, nomClient,prenomClient,telephone, password, username, arrayOf(Compte(numero = numero)))

    return client
}

fun listeDesClients(clients: List<Client>){
    for(client in clients){
        affichageClient(client)
    }
}

fun versementClient(clients: List<Client>){
    val client = identificationClient(clients)
    if(client != null){
        print("Saisir numero de compte au format xxxx xxxx xxxx xxxx : ")
        val numeroCompte = readLine()
        val compte: Compte? = client.comptes.find{it.numero == numeroCompte}
        if(compte != null){
            print("Saisir la somme à verser : ")
            val somme:Int = reader.nextInt()
            compte.solde += somme
            compte.historiques.add("Vous avez effectué un versement de ${somme}")
        }else{
            println("Ce numéro de compte n'existe pas")
        }
    }else{
        println("Cet utilisateur n'existe pas")
    }
}


fun virementClient(clients: List<Client>){
    println("Saisir les informations du client emetteur ")
    val client = identificationClient(clients)
    if(client != null){
        println("Saisir les informations du client destinataire")
        val clientAVirer = identificationClient(clients)
        if(clientAVirer != null){
            print("Saisir le numero de compte : ")
            val numeroCompte = readLine()
            val compte: Compte? = client.comptes.find{it.numero == numeroCompte}
            if(compte != null){
                print("Saisir le montant à virer :  ")
                val montant = reader.nextInt()
                if(compte.solde > montant){
                    print("Saisir le numero de compte du client vers qi virer : ")
                    val numeroCompteClientAvirer = readLine()
                    val compteClientAVirer = clientAVirer.comptes.find{it.numero == numeroCompteClientAvirer}
                    if(compteClientAVirer != null) {
                        compteClientAVirer.solde += montant
                        compteClientAVirer.historiques.add("Vous avez recu un virement de ${montant}")
                        compte.historiques.add("Vous avez effectué un viremet de ${montant} à ${clientAVirer.nom} ${clientAVirer.prenom}")
                    }
                }else{
                    throw Exception("Désolé votre solde est inférieur au montant à virer")
                }
            }else{
                println("Ce compte n'existe pas")
            }
        }else{
            throw Exception("Cet utilisateur n'existe pas")
        }
    }
}


fun infoCompte(clients: List<Client>){
    val client = identificationClient(clients)
    if(client != null){
       affichageClient(client)
    }else{
        println("Ce utilisateur n'existe pas !")
    }
}

fun affichageCompteClient(clients: List<Client>){
    val client = identificationClient(clients)
    if(client != null){
        for(compte in client.comptes){
            println("Numero : ${compte.numero}")
            println("Solde : ${compte.solde}")
        }
        println("\n")
    }else{
        println("Ce client n'existe pas !")
    }
}


fun operationRetrait(clients: List<Client>){
    val client = identificationClient(clients)
    if(client != null){
        print("Saisir le numero de compte : ")
        val numeroCompte = readLine()
        val compte: Compte? = client.comptes.find{it.numero == numeroCompte}
        if(compte != null){
            print("Saisir le montant à retirer :  ")
            val montant = reader.nextInt()
            if(compte.solde > montant){
                compte.solde -= montant
                compte.historiques.add("Vous avez effectué un retrait de ${montant}")
            }else{
                throw Exception("Désolé votre solde est inférieur au montant à retirer")
            }
        }else{
            println("Ce compte n'existe pas")
        }
    }else{
        println("Cet utilisateur n'existe pas")
    }
}


fun affichageEtatCompte(clients: List<Client>){
    val client = identificationClient(clients)
    if(client != null){
        print("Saisir le numero de compte : ")
        val numeroCompte = readLine()
        val compte: Compte? = client.comptes.find{it.numero == numeroCompte}
        if(compte != null){
            println("L'etat de votre compte est : ${compte.etat}")
        }else{
            println("Ce compte n'existe pas")
        }
    }else{
        println("Cet utilisateur n'existe pas")
    }
}


fun affichageHistoriqueClient(clients: List<Client>){
    val client = identificationClient(clients)
    if(client != null){
        for((numero, compte) in client.comptes.withIndex()){
            println("Historique Compte ${numero+1}")
            for(historique in compte.historiques){
                println("${historique}")
            }
        }
        println("\n")
    }else{
        println("Ce client n'existe pas !")
    }
}
//Fonctions privées

private fun affichageClient(client: Client){
    println("Email : ${client.email}")
    println("Nom : ${client.nom}")
    println("Prenom : ${client.prenom}")
    println("Telephone : ${client.telephone}")
    println("Username : ${client.username}")
    println("Nom : ${client.password}")
    println("Les comptes clients")
    for(compte in client.comptes){
        println("Numero : ${compte.numero}")
        println("Solde : ${compte.solde}")
    }
    println("\n")
}


private fun identificationClient(clients: List<Client>): Client?{
    print("Saisir nom client : ")
    val nomClient = readLine()
    print("Saisir prenom client : ")
    val prenomClient = readLine()
    val client: Client? = clients.find{it.nom==nomClient && it.prenom==prenomClient}

    return client
}