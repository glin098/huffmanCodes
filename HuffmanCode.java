//package huffman;

import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCode implements IHuffmanCode {
    private static final char END_OF_INPUT_TEXT = '\00';
    private Map<Character, String> encodingTable;
    private Map<String, Character> decodingTable;
        
    public HuffmanCode() {
    }
    
    // encode a string
    
    @Override
    public String encode(String text) throws Exception {
        text = text + END_OF_INPUT_TEXT;
                
        createCodingTables(text); 
        
        return encode(text, this.encodingTable);
    }
    
    // decode a binary string
    
    @Override
    public String decode(String code) {
        return decode(code, this.decodingTable);        
    }
        
    // encode a string

    private static String encode(String text, Map<Character, String> encodingTable) throws Exception {
        StringBuilder encoded_text = new StringBuilder();

        //iterate thru each char
        for (char c : text.toCharArray()){
            String code = encodingTable.get(c);
            encoded_text.append(code); //add to text w encoded char
        }
        return encoded_text.toString();
    }
    
    private static String decode(String code, Map<String, Character> decodingTable){

        StringBuilder decoded_text = new StringBuilder();

        String current_code = ""; 

        // iterate thru each bit
        for(char b : code.toCharArray()){

            current_code += b; //add to binary code

            // if table already contains the bit
            if(decodingTable.containsKey(current_code)){
                decoded_text.append(decodingTable.get(current_code));
                current_code = ""; //reset

            }
        }

        return decoded_text.toString();

    }

    private void createCodingTables(String text) {
        PriorityQueue<IHuffmanSymbol> symbolFrequencies = countSymbolFrequencies(text);
        //create tree from symbol freq
        IHuffmanSymbol root = createHuffmanTree(symbolFrequencies);

        this.encodingTable = new Hashtable<>();
        this.decodingTable = new Hashtable<>();

        createEncodingTable(root, "");
        createDecodingTable(root,"");
    }

    private void createEncodingTable(IHuffmanSymbol root, String code) {

        // instanceof is operator tests to see if the prototype property of a 
        //constructor appears anywhere in the prototype chain of an object
        if (root instanceof HuffmanLeaf){ // if node is leaf
            HuffmanLeaf leaf= (HuffmanLeaf) root;
            this.encodingTable.put(leaf.getSymbol(), code);
        } 
        else if (root instanceof HuffmanNode) { // if node is node

            HuffmanNode node = (HuffmanNode) root;

            createEncodingTable(node.getLeftChild(),code+"0");
            createEncodingTable(node.getRightChild(), code+"1");

        }
    }
    
    private void createDecodingTable(IHuffmanSymbol root, String code)    {
        
        
        if (root instanceof HuffmanLeaf){ 
            HuffmanLeaf leaf = (HuffmanLeaf) root;
            this.decodingTable.put(code, leaf.getSymbol());
        } 
        else if (root instanceof HuffmanNode) { // if node is node 

            HuffmanNode node = (HuffmanNode)root;

            createDecodingTable(node.getLeftChild(), code+"0");
            createDecodingTable(node.getRightChild(), code +"1");
        }
    }
    
    // create a priority queue with the Huffman's symbols from the input text
    
    private static PriorityQueue<IHuffmanSymbol> countSymbolFrequencies(String inputText) {
        char symbol;

        // count the frequencies of the input characters
        
        Map<Character, Integer> tableOfSymbolFrequencies = new Hashtable<Character, Integer>();
        
        for (int i=0; i<inputText.length(); i++) {
            symbol = inputText.charAt(i);
            
            if (tableOfSymbolFrequencies.containsKey(symbol)) {
                tableOfSymbolFrequencies.put(symbol, tableOfSymbolFrequencies.get(symbol) + 1);
            } else {
                tableOfSymbolFrequencies.put(symbol, 1);                
            }
        }
        
        // create a priority queue with the symbols and their frequencies
        
        PriorityQueue<IHuffmanSymbol> symbolFrequencies = new PriorityQueue<IHuffmanSymbol>();
        
        for (Map.Entry<Character, Integer> charFrequency : tableOfSymbolFrequencies.entrySet()) {
            symbolFrequencies.add(new HuffmanLeaf(charFrequency.getKey(), charFrequency.getValue()));
        }
        
        return symbolFrequencies;
    }
    
    // create a Huffman's tree with the symbol frequencies
    
    private static IHuffmanSymbol createHuffmanTree(PriorityQueue<IHuffmanSymbol> symbolFrequencies) {
        // the Huffman's tree is created by extracting the two smallest frequencies from the priority queue
        // this process ends when the priority queue contains only one symbol
        // the node remaining in the priority queue becomes the root of the Huffman's tree  
        
        IHuffmanSymbol symbol1, symbol2;
        
        while (symbolFrequencies.size() > 1) {

            // get the first symbol with the smallest frequency
            
            symbol1 = symbolFrequencies.poll();

            // get the second symbol with the smallest frequency
            
            symbol2 = symbolFrequencies.poll();

            // add a new node with the sum of the two smallest frequencies to the priority queue
            
            symbolFrequencies.add(new HuffmanNode(symbol1.getFrequency() + symbol2.getFrequency(), symbol1, symbol2));
        }

        // the node remaining in the priority queue becomes the root of the Huffman's tree
        
        return symbolFrequencies.poll();
    }
}