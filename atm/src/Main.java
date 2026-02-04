import controllers.MainMenuController;
import repositories.ATMRepository;
import repositories.TechnicianRepository;
import repositories.TransactionRepository;
import repositories.UserRepository;
import repositories.json.JsonATMRepository;
import repositories.json.JsonTechnicianRepository;
import repositories.json.JsonTransactionRepository;
import repositories.json.JsonUserRepository;
import services.ATMService;
import services.AuthenticationService;
import services.TechnicianService;
import services.UserService;

public class Main {

    public static void main(String[] args) {

        // ---------------- REPOSITORIES ----------------
        ATMRepository atmRepository = new JsonATMRepository();
        UserRepository userRepository = new JsonUserRepository();
        TechnicianRepository technicianRepository = new JsonTechnicianRepository();
        TransactionRepository transactionRepository = new JsonTransactionRepository();

        // ---------------- SERVICES ----------------
        ATMService atmService = new ATMService(atmRepository);
        UserService userService = new UserService(userRepository, transactionRepository);
        TechnicianService technicianService = new TechnicianService(atmRepository);
        AuthenticationService authenticationService =
                new AuthenticationService(userRepository, technicianRepository);

        // ---------------- CONTROLLER ----------------
        MainMenuController mainMenuController =
                new MainMenuController(authenticationService,
                        userService,
                        atmService,
                        technicianService);

        // ---------------- START APP ----------------
        mainMenuController.start();
    }
}
