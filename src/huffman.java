import java.util.*;

abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // the frequency of this tree
    public HuffmanTree(int freq) { frequency = freq; }
    
    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}

class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents
    
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}

class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // subtrees
    
    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

public class huffman 
{
    // input is an array of frequencies, indexed by character code
    public static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
        
        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
            
            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }
    
    public static void printCodes(HuffmanTree tree, StringBuffer prefix, char s, StringBuilder txt) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            if(leaf.value == s){
            // print out character, frequency, and code for this leaf (which is just the prefix)
	            txt.append(prefix);
                //System.out.println(prefix);
		    
	       }
        } 
        else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
            
            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix,s,txt);
            prefix.deleteCharAt(prefix.length()-1);
            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix,s,txt);
            prefix.deleteCharAt(prefix.length()-1);
        }
	}

    public static void getDecode(HuffmanTree tree, StringBuilder txt)
    {
        int p = 0;
        while(p<txt.length())
        {
            p = decode(tree, txt, p);
        }
    }

    public static int decode(HuffmanTree tree, StringBuilder txt, int p)
    {
        if(tree instanceof HuffmanLeaf)
        {
            HuffmanLeaf leaf =(HuffmanLeaf)tree;
            System.out.println(leaf.value);
            return p;
        }
        else if( tree instanceof HuffmanNode)
        {
            HuffmanNode node =(HuffmanNode)tree;
            if(txt.charAt(p)=='1')
                return decode(node.right, txt, p+1);
            else
                return decode(node.left, txt, p+1);
        }
        return 0;

    }
	    
    public static void main(String[] args)
    {
        Scanner moo = new Scanner(System.in);
        System.out.println("enter a sentence:");
        String test = moo.nextLine();
        
        // we will assume that all our characters will have
        // code less than 256, for simplicity
        int[] charFreqs = new int[256];
        // read each character and record the frequencies
        for (char c : test.toCharArray())
            charFreqs[c]++;
        
        // build tree
        HuffmanTree tree = buildTree(charFreqs);
    	StringBuilder txt = new StringBuilder("");    
        // print out results
	    for(char c: test.toCharArray())
        	printCodes(tree, new StringBuffer(), c, txt);
        System.out.println("the encoded text is:");
        for(int i = 0; i<txt.length(); i++)
            System.out.print(txt.charAt(i));
        System.out.println();
        System.out.println("the decoded text is");
	    getDecode(tree, txt);
    }
}