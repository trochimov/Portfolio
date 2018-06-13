package bestgymever;

import bestgymever.controller.*;
import bestgymever.models.*;
import bestgymever.repository.*;
import bestgymever.view.*;
import java.time.*;
import java.util.*;

public class BestGymEver {
    
    public static void main(String[] args) {
        BestGymEver go = new BestGymEver();
        go.go();
    }

    private void go() {
        Repository repository = new Repository();
        SuperModel model = new SuperModel();
        ConsoleView view = new ConsoleView();
        Options();
        Scanner sc = new Scanner(System.in);
        
        choice:
        while(true){
        String choice = sc.nextLine();
        switch (choice){
            case "1":
                AdministratorController administratorController = new AdministratorController(model, view, repository);
                administratorController.updateModel("");
                break choice;
                
            case "2":
                ReceptionistController receptionistController = new ReceptionistController(model, view, repository);
                receptionistController.updateModel("");
                break choice;
                
            case "3":
                MemberController memberController = new MemberController(model, view, repository);
                memberController.updateModel("");
                break choice;
                
            case "4":
                PersonalTrainerController personalTrainerController = new PersonalTrainerController(model, view, repository);
                personalTrainerController.updateModel("");
                break choice;
      
            default:
                System.out.println("No Client match, try again!\n");
                Options();
                break;
        }
        }
    }

    private void Options() {
        System.out.println("Choose type of user\n"
                + "1. Administrator\n"
                + "2. Receptionist\n"
                + "3. Member\n"
                + "4. PersonalTrainer\n");
    }
}
