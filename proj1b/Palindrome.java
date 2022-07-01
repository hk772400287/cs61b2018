public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque <Character> L = new LinkedListDeque();
        for(int i = 0; i < word.length(); i++) {
            L.addLast(word.charAt(i));
        }
        return L;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> L = wordToDeque(word);
        return isPalindromehelper(L);
    }

    private boolean isPalindromehelper(Deque<Character> L) {
        if (L.size() == 0 || L.size() == 1) {
            return true;
        }
        if (L.removeFirst() == L.removeLast()) {
            return isPalindromehelper(L);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> L = wordToDeque(word);
        return isPalindromehelper2(L, cc);
    }

    private boolean isPalindromehelper2(Deque<Character> L, CharacterComparator cc) {
        if (L.size() == 0 || L.size() == 1) {
            return true;
        }
        if (cc.equalChars(L.removeFirst(), L.removeLast())) {
            return isPalindromehelper2(L, cc);
        } else {
            return false;
        }
    }


}
