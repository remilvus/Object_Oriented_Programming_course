package agh.cs.project.LivingBeingsLogic;

import java.util.*;

public class Genes {
    private int[] genes;
    private final Random rand = new Random();
    private final int[] counter;

    private Genes(int[] genes, int[] counter){
        List<Integer> intList = new ArrayList<Integer>(genes.length);
        for (int i : genes)
        {
            intList.add(i);
        }
        Collections.shuffle(intList);
        int i=0;
        for(int g: intList){
            genes[i] = g;
            i++;
        }
        this.genes = genes;
        this.counter = counter; // for showing genes in compact form
    }

    public Genes(){
        genes = new int[32];
        counter = new int[8];
        for (int i=0; i<32; i++){
            int g = rand.nextInt(8);
            genes[i] = g;
            counter[g]++;
        }
        int m = find_missing();
        while(m != -1){
            changeRandomTo(m);
            m = find_missing();
        }
       // Arrays.sort(genes);
    }

    private void changeRandomTo(int gene){
        // changes "random" gene to `gene`
        // cannot remove gene with only one copy
        assert gene < 8;
        int change = rand.nextInt(32);
        while(counter[genes[change]] < 2){
            change = rand.nextInt(32);
        }
        counter[genes[change]]--;
        genes[change] = gene;
        counter[gene]++;
    }

    @Override
    public String toString() {
        return Arrays.toString(counter);
    }

    private int find_missing(){
        // searches for gene with 0 copies
        for (int i=0; i<8; i++){
            if (counter[i]==0){
                return i;
            }
        }
        return -1;
    }

    public int decision(){
        return genes[rand.nextInt(32)];
    }

    public Genes combine(Genes other){
        int first_idx = rand.nextInt(29) + 1;
        int len = 1 + rand.nextInt(31 - first_idx);
        int[] genes = this.genes.clone();
        System.arraycopy(other.genes, first_idx, genes, first_idx, len);

        int[] counter = new int[8];
        for(int g: genes){counter[g]++;}
        int m = find_missing();
        while (m != -1){
            changeRandomTo(m);
            m = find_missing();
        }
   //     Arrays.sort(genes);
        return new Genes(genes, counter);
    }
}
