package fr.uge.poo.cmdline.ex1;

public class Application {

    static class PaintOptions{
        private boolean legacy=false;
        private boolean bordered=true;

        public void setLegacy(boolean legacy) {
            this.legacy = legacy;
        }

        public boolean isLegacy(){
            return legacy;
        }

        public void setBordered(boolean bordered){
            this.bordered=bordered;
        }

        public boolean isBordered(){
            return bordered;
        }

        @Override
        public String toString(){
            return "PaintOption [ bordered = "+bordered+", legacy = "+ legacy +" ]";
        }
    }
    /*
       1 - On enregistre toutes les options et les retape pour connaitre la saisie du l'utilisateur
       2 - Méthode process -> façon dont les noms de fichiers sont separés des options
       3 -
       Pt général : RegisterOptions ?
       La gestion des options est répartie sur deux classes ! App et CmdLineParser
       = deux classes partagent une responsabilité !
       Faute de frappe entre les options (enresgistrement et le test) = problème
       Classe option à l'intérieur du Parser ? le parser n'est plus ouvert au extensions , non réutilisable
       Passer au parser toute la responsabilité de gérer l'option = on lui passe le nom de l'option et le code à éxécuter
       la classe PaintOptions va être instanciée par CmdLineParser
       OptionBuilder -> créer et renvoi un record (stock temporairemement les champs)

       type de registerOption -> String et lambda , Runnable
       option avec 1  param -> Consumer<String>  a la place du Runnable

       avec deux maps au début si  pas clair
       1 seule map ? -> stocker quelque chose de compatible entre le Consumer et le Runnable
       Toutes les options au même endroit
       Consumer de table de String -> non

       --> Option avec un nb arbitraire de paramètres
       open-closed =  fermé , les devs peuvent ajouter des fonctionnalités avec des lambda (on ouvre pas le module)
       Les tests doivent être de compabilité ascendante

       Stocker des fonctions list de string vers list de String
       Passer un Iterateur de String :  Si il a besoin un args -> next()

     */
    public static void main(String[] args) {
        var options = new PaintOptions();
        String[] arguments={"-legacy","-no-borders","filename1","filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", () -> options.setBordered(false));
        var result = cmdParser.process(arguments);

        System.out.println(options.toString());

    }
}