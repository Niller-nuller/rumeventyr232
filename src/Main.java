import a_ui.GameDisplay;
import b_controller.GameController;
import c_service.GameService;

import java.util.Scanner;

public class Main {
     public static void main(String[] args) {

         GameController gameController = new GameController();
         gameController.startGame();
    }
}