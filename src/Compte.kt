class Compte(var solde: Int = 0,
             val numero: String,
             val etat: String = "Normal",
             val historiques: MutableList<String> = mutableListOf("Aucune action recente")) {
}