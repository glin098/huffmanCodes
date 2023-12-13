//package huffman;

public class HuffmanNode extends HuffmanSymbol {
    private IHuffmanSymbol leftChild, rightChild;
    
    public HuffmanNode(int frequency, IHuffmanSymbol leftChild, IHuffmanSymbol rightChild) {
        super(frequency);
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
    
    public IHuffmanSymbol getLeftChild() {
        return this.leftChild;
    }

    public IHuffmanSymbol getRightChild() {
        return this.rightChild;
    }
    
    @Override
    public String toString() {
        return "(*," + this.getFrequency() + ") ";
    }
}