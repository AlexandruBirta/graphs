//The "bag" data structure is commonly used to store a collection of elements without any specific order or uniqueness requirement. It allows for efficient insertion and retrieval of elements. Here's an implementation of the bag data structure using adjacency list-based graphs in Java:
//
//        In this implementation, the Bag class represents the bag data structure. It internally uses an ArrayList to store the elements. The add method adds an element to the bag, the contains method checks if an element is present in the bag, and the size method returns the number of elements in the bag. The getAllElements method retrieves all elements from the bag as a list.
//
//        In the main method, a sample bag is created and elements are added to it. The presence of specific elements is checked, the size of the bag is obtained, and all elements are retrieved and printed.
//
//        Feel free to modify the code to suit your specific requirements or add additional functionality as needed.

import java.util.ArrayList;
import java.util.List;

class Bag<T> {
    private List<T> elements;

    public Bag() {
        elements = new ArrayList<>();
    }

    public void add(T element) {
        elements.add(element);
    }

    public boolean contains(T element) {
        return elements.contains(element);
    }

    public int size() {
        return elements.size();
    }

    public List<T> getAllElements() {
        return elements;
    }
}

public class BagDataStructure {
    public static void main(String[] args) {
        Bag<Integer> bag = new Bag<>();

        // Add elements to the bag
        bag.add(5);
        bag.add(10);
        bag.add(7);
        bag.add(3);

        // Check if an element is present in the bag
        System.out.println("Bag contains 10: " + bag.contains(10));
        System.out.println("Bag contains 8: " + bag.contains(8));

        // Get the size of the bag
        System.out.println("Bag size: " + bag.size());

        // Retrieve all elements from the bag
        List<Integer> allElements = bag.getAllElements();
        System.out.println("All elements in the bag: " + allElements);
    }
}
