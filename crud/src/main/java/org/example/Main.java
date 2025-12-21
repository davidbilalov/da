package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDao userDao = new UserDao();
        while (true) {
            System.out.println("Choose an operation: 1. Create 2. Read 3. Update 4. Delete 5. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: // Create
                    User user = new User();
                    System.out.println("Enter name:");
                    user.setName(scanner.nextLine());
                    System.out.println("Enter email:");
                    user.setEmail(scanner.nextLine());
                    System.out.println("Enter age:");
                    user.setAge(Integer.parseInt(scanner.nextLine()));
                    userDao.save(user);
                    break;
                case 2: // Read
                    System.out.println("Enter user ID:");
                    User retrievedUser = userDao.getById(Long.parseLong(scanner.nextLine()));
                    System.out.println(retrievedUser);
                    break;
                case 3: // Update
                    System.out.println("Enter user ID to update:");
                    Long updateId = Long.parseLong(scanner.nextLine());
                    User updateUser = userDao.getById(updateId);
                    if (updateUser != null) {
                        System.out.println("Enter new name:");
                        updateUser.setName(scanner.nextLine());
                        System.out.println("Enter new email:");
                        updateUser.setEmail(scanner.nextLine());
                        System.out.println("Enter new age:");
                        updateUser.setAge(Integer.parseInt(scanner.nextLine()));
                        userDao.update(updateUser);
                    }
                    break;
                case 4: // Delete
                    System.out.println("Enter user ID to delete:");
                    userDao.delete(Long.parseLong(scanner.nextLine()));
                    break;
                case 5: // Exit
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}