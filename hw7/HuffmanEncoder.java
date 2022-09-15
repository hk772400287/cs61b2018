import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> ft = new HashMap<>();
        for (char c : inputSymbols) {
            if (ft.containsKey(c)) {
                ft.put(c, ft.get(c) + 1);
            } else {
                ft.put(c, 1);
            }
        }
        return ft;
    }

    public static void main(String[] args) {
        char[] symbols = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(symbols);
        BinaryTrie bt = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(bt);
        ow.writeObject(symbols.length);
        Map<Character, BitSequence> lookUpTable = bt.buildLookupTable();
        ArrayList<BitSequence> bsList = new ArrayList<>();
        for (char c : symbols) {
            bsList.add(new BitSequence(lookUpTable.get(c)));
        }
        BitSequence encodedSeq = BitSequence.assemble(bsList);
        ow.writeObject(encodedSeq);
    }
}
