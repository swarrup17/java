import java.util.Scanner;

class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class CircularLinkedList {

    private static Node head = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option, data, position;

        do {
            System.out.println("\nCircular Linked List Operations");
            System.out.println("1. Insert at the beginning");
            System.out.println("2. Insert at the end");
            System.out.println("3. Insert at a specific position");
            System.out.println("4. Delete from the beginning");
            System.out.println("5. Delete from the end");
            System.out.println("6. Delete from a specific position");
            System.out.println("7. Search");
            System.out.println("8. Display");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter data to insert at the beginning: ");
                    data = scanner.nextInt();
                    insertBeginning(data);
                    break;

                case 2:
                    System.out.print("Enter data to insert at the end: ");
                    data = scanner.nextInt();
                    insertEnd(data);
                    break;

                case 3:
                    System.out.print("Enter data to insert: ");
                    data = scanner.nextInt();
                    System.out.print("Enter position to insert at: ");
                    position = scanner.nextInt();
                    insertAtPosition(data, position);
                    break;

                case 4:
                    deleteBeginning();
                    break;

                case 5:
                    deleteEnd();
                    break;

                case 6:
                    System.out.print("Enter position to delete: ");
                    position = scanner.nextInt();
                    deleteAtPosition(position);
                    break;

                case 7:
                    System.out.print("Enter data to search: ");
                    data = scanner.nextInt();
                    search(data);
                    break;

                case 8:
                    display();
                    break;

                case 9:
                    System.out.println("Exiting the program. Bye!");
                    break;

                default:
                    System.out.println("Invalid option. Please enter a valid choice.");
            }

        } while (option != 9);

        scanner.close();
    }

    private static void insertBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;

        if (head == null) {
            head = newNode;
            head.next = head; // Circular link
        } else {
            Node temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newNode;
            head = newNode;
        }

        System.out.println("Element " + data + " inserted at the beginning successfully.");
    }

    private static void insertEnd(int data) {
        Node newNode = new Node(data);
        newNode.next = head;

        if (head == null) {
            head = newNode;
            head.next = head; // Circular link
        } else {
            Node temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newNode;
        }

        System.out.println("Element " + data + " inserted at the end successfully.");
    }

    private static void insertAtPosition(int data, int position) {
        if (position < 1) {
            System.out.println("Invalid position. Position must be a positive integer.");
            return;
        }

        Node newNode = new Node(data);

        if (head == null) {
            if (position == 1) {
                head = newNode;
                head.next = head; // Circular link
                System.out.println("Element " + data + " inserted at position " + position + " successfully.");
            } else {
                System.out.println("Invalid position. List is empty.");
            }
        } else {
            Node temp = head;
            int i = 1;

            // Traverse the list to the position
            while (i < position - 1 && temp.next != head) {
                temp = temp.next;
                i++;
            }

            // Insert the new node at the specified position
            newNode.next = temp.next;
            temp.next = newNode;

            System.out.println("Element " + data + " inserted at position " + position + " successfully.");
        }
    }

    private static void deleteBeginning() {
        if (head == null) {
            System.out.println("List is empty. Nothing to delete from the beginning.");
            return;
        }

        Node temp = head;

        // If there is only one node in the list
        if (temp.next == head) {
            head = null;
        } else {
            // Traverse the list to the last node
            while (temp.next != head) {
                temp = temp.next;
            }

            // Update the last node's next pointer
            temp.next = head.next;

            // Move the head to the next node
            head = head.next;
        }

        System.out.println("Element deleted from the beginning successfully.");
    }

    private static void deleteEnd() {
        if (head == null) {
            System.out.println("List is empty. Nothing to delete from the end.");
            return;
        }

        Node temp = head, prev = null;

        // Traverse the list to the last node
        while (temp.next != head) {
            prev = temp;
            temp = temp.next;
        }

        // If there is only one node in the list
        if (temp == head) {
            head = null;
        } else {
            // Update the previous node's next pointer
            prev.next = temp.next;
        }

        System.out.println("Element deleted from the end successfully.");
    }

    private static void deleteAtPosition(int position) {
        if (head == null) {
            System.out.println("List is empty. Nothing to delete.");
            return;
        }

        if (position < 1) {
            System.out.println("Invalid position. Position must be a positive integer.");
            return;
        }

        Node temp = head, prev = null;
        int i = 1;

        // Traverse the list to the specified position
        while (i < position && temp.next != head) {
            prev = temp;
            temp = temp.next;
            i++;
        }

        // If the specified position is not valid
        if (temp == head && i < position) {
            System.out.println("Invalid position. Position exceeds the list length.");
            return;
        }

        // If there is only one node in the list
        if (temp == head && temp.next == head) {
            head = null;
        } else if (temp == head) { // If deleting the first node
            // Update the last node's next pointer
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = head.next;

            // Move the head to the next node
            head = head.next;
        } else { // If deleting a node in the middle or end
            // Update the previous node's next pointer
            prev.next = temp.next;
        }

        System.out.println("Element deleted from position " + position + " successfully.");
    }

    private static void search(int data) {
        if (head == null) {
            System.out.println("List is empty. Nothing to search.");
            return;
        }

        Node temp = head;
        int position = 1;

        // Traverse the list to find the data
        do {
            if (temp.data == data) {
                System.out.println("Element " + data + " found at position " + position + ".");
                return;
            }
            temp = temp.next;
            position++;
        } while (temp != head);

        System.out.println("Element " + data + " not found in the list.");
    }

    private static void display() {
        if (head == null) {
            System.out.println("List is empty. Nothing to display.");
            return;
        }

        Node temp = head;

        System.out.print("Circular Linked List: ");
        do {
            System.out.print(temp.data + " ");
            temp = temp.next;
        } while (temp != head);

        System.out.println();
    }
}
