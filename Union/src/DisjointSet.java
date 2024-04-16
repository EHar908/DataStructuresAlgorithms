import java.util.ArrayList;
import java.util.List;

public class DisjointSet {
    private ArrayList<Integer> set;
    public DisjointSet(int size){
        set = new ArrayList<>(size + 1);
        for(int i = 1; i < size + 1; i++){
            set.add(-1);
        }
    }

    public void union(int node1, int node2){
        int root1 = find(node1);
        int root2 = find(node2);
        if(!(root1 == root2)){
            int combo = set.get(root1) + set.get(root2);
            if(set.get(root2) > set.get(root1)){
                set.set(root1, combo);
                set.set(root2, root1);
            }
            else{
                set.set(root2, combo);
                set.set(root1, root2);
            }
        }
    }

    // find(16)
    public int find(int node){
        if(set.get(node) < 0){
            return node;
        }
        set.set(node, find(set.get(node)));
        return set.get(node);

    }
}
