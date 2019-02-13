public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> lld = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            lld.addLast(word.charAt(i));
        }
        return lld;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordlld = wordToDeque(word);
        if (wordlld.isEmpty() || wordlld.size() == 0) {
            return true;
        }
        String backwards = "";
        for (int i = 0; i < word.length(); i++) {
            backwards += wordlld.removeLast();
        }
        return backwards.equals(word);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordlld = wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i++) {
            if (!(cc.equalChars(wordlld.removeLast(), word.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}
