public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie bt = (BinaryTrie) or.readObject();
        int numOfSymbols = (int) or.readObject();
        BitSequence encodedSeq = (BitSequence) or.readObject();
        char[] charArray = new char[numOfSymbols];
        for (int i = 0; i < charArray.length; i++) {
            Match m = bt.longestPrefixMatch(encodedSeq);
            charArray[i] = m.getSymbol();
            encodedSeq = encodedSeq.allButFirstNBits(m.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], charArray);
    }
}
