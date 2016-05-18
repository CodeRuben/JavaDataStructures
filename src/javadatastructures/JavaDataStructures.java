
package javadatastructures;
import Data_Structures.LinkedList;
import Data_Structures.SplayTree;

public class JavaDataStructures {

    public static void main(String[] args) {
        SplayTree<Integer, Integer> splayTree = new SplayTree<>();

        int random = 0;
        for(int i = 0; i < 100; i++) {
            splayTree.add(i, i); 
        }
        
        splayTree.contains(50);
        //System.out.println(splayTree.contains(1));
        //splayTree.inorder(splayTree.root);
        System.out.println();
        splayTree.breadthTraversal();
    }
    
}
