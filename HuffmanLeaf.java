//package huffman;

public class HuffmanLeaf extends HuffmanSymbol {
    private char symbol;
    
    public HuffmanLeaf(char symbol, int frequency) {
        super(frequency);
        this.symbol = symbol;
    }
    
    public char getSymbol() {
        return this.symbol;
    }
    
    @Override
    public String toString() {
        return "(" + this.symbol + "," + this.getFrequency() + ") ";
    }
}