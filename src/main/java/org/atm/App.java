package org.atm;

import java.sql.SQLException;
import java.util.Scanner;

public class App {

    static Scanner sc = new Scanner(System.in);
    static String pin;
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        Controller controller = new Controller("jdbc:mysql://localhost:3306/atm","root","Sad1996.");

        int count = 3;
        while (count !=0) {
            System.out.print("Enter pin: ");
            String x = sc.next();
            if(!controller.isChecked(x)) {
                count--;
                System.out.println("Wrong PIN, you have "+count+" tries!");

            } else {
                pin = x;
                break;
            }
            if(count == 0)
                System.exit(0);
        }

        while(true) {
            System.out.println("1.Exit");
            System.out.println("2.Check your money");
            System.out.println("3.Withdraw");
            System.out.println("4.Change your PIN");
            int cmd = sc.nextInt();

            switch(cmd) {
                case 1: System.exit(0);
                case 2: {System.out.println("You have "+controller.getMoneyStatus()+ " euro"); break;}
                case 3: {
                    System.out.print("Money: ");
                    int money = sc.nextInt();
                    System.out.println(controller.withdraw(money));
                    break;
                }
                case 4: {
                    System.out.print("Enter your PIN: ");
                    String changePin = sc.next();
                    if(pin.equals(changePin)) {
                        System.out.print("Enter your new PIN: ");
                        changePin = sc.next();
                        controller.changePIN(changePin);
                    }
                    else
                        System.out.println("Wrong PIN!");
                        break;
                }
                default:System.exit(0);
            }
        }
    }
}
