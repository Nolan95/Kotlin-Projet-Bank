class Client(_email: String,
             _nom: String,
             _prenom: String,
             _telephone: String,
             _password: String,
             _username: String, val comptes: Array<Compte>) : User(_email, _nom, _prenom, _telephone, _password, _username) {


}