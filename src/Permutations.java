import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Permutations {
	public static Set<String> permutationOfSameLength(String str) {
		Set<String> perm = new HashSet<String>();
		if (str == null) {
			return null;
		} else if (str.length() == 0) {
			perm.add("");
			return perm;
		}
		char initial = str.charAt(0);
		String rem = str.substring(1);
		Set<String> words = permutationFinder(rem);
		for (String strNew : words) {
			for (int i = 0; i <= strNew.length(); i++) {
				perm.add(charInsert(strNew, initial, i));
			}
		}
		return perm;
	}

	public static Set<String> permutationOfSameLength(String str, int length) {
		Set<String> perm = permutationFinder(str);
		Iterator<String> itr = perm.iterator();
		while (itr.hasNext()) {
			String s = itr.next();
			if (s.length() != length) {
				itr.remove();
			}
		}
		return perm;
	}

	public static Set<String> permutationFinder(String str) {
		Set<String> words = new HashSet<String>();
		for (int i = 0; i < str.length(); i++) {
			words = addLetter(str.charAt(i), words);
		}
		return words;
	}

	public static Set<String> permutationFinder(String str, int length) {
		Set<String> words = new HashSet<String>();
		for (int i = 0; i < str.length(); i++) {
			words = addLetter(str.charAt(i), words);
		}
		Iterator<String> itr = words.iterator();
		while (itr.hasNext()) {
			String s = itr.next();
			if (s.length() != length) {
				itr.remove();
			}
		}
		return words;
	}

	private static Set<String> addLetter(char charAt, Set<String> words) {
		Set<String> newWords = new HashSet<String>();
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				StringBuilder str = new StringBuilder(word);
				newWords.add(str.insert(i, charAt).toString());
			}
		}
		newWords.add(String.valueOf(charAt));
		newWords.addAll(words);
		return newWords;
	}

	public static String charInsert(String str, char c, int j) {
		String begin = str.substring(0, j);
		String end = str.substring(j);
		return begin + c + end;
	}

}
