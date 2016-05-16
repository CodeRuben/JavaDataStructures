
package javadatastructures;
import Data_Structures.LinkedList;
import Data_Structures.SplayTree;

public class JavaDataStructures {

    public static void main(String[] args) {
        SplayTree<Integer, Integer> splayTree = new SplayTree<>();

        for(int i = 0; i < 10000; i++) {
            splayTree.add(i + 1, i  + 1);
            splayTree.add(i / 2, i  + 1);
            splayTree.add(i - 2, i  + 1);
            splayTree.add(i << 3, i  + 1);  
        }
        
        System.out.println(splayTree.contains(60769));
        //splayTree.inorder(splayTree.root);
        System.out.println();
        splayTree.breadthTraversal();
    }
    
}
