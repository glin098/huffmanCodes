//package huffman;

public class HuffmanSymbol implements IHuffmanSymbol, Comparable<IHuffmanSymbol> {
    private int frequency;
    
    public HuffmanSymbol(int frequency) {
        this.frequency = frequency;
    }
    
    @Override
    public int getFrequency() {
        return this.frequency;
    }
    
    @Override
    public int compareTo(IHuffmanSymbol s) {
        return (this.getFrequency() - s.getFrequency());
    }
}