import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MasterMind {

	private static List<String> allWords = new ArrayList<String>(); 
	private Set<String> dictionary = new HashSet<String>();
	private String gameWord;
	
	private String getSorted(String word) {
		char[] wordArray = word.toCharArray();
		Arrays.sort(wordArray);
		return String.valueOf(wordArray);
	}
	
	public void readFile(String filePath) throws Exception {
		allWords = (List<String>) Files.readAllLines(Paths.get(filePath));
	}
	
	private void getWordSet(List<String> readAllLines, int length) {
		// TODO Auto-generated method stub
		for(String line  : readAllLines) {
			if(line.length() == length && hasUniqueLetters(line)) {
				dictionary.add(line.toUpperCase());
			}
		}
	}

	private boolean hasUniqueLetters(String line) {
		for(char ch : line.toCharArray()) {
			if(line.indexOf(ch) != line.lastIndexOf(ch)) {
				return false;
			}
		}
		return true;
	}
	
	private String processWord(String word) {
		int numOfCommon = commonCharsCount(word);
		return null;
	}
	
	
	
	private int commonCharsCount(String word) {
		String unique = getUnique(word);
		int numCommon = 0;
		for(char ch : unique.toCharArray()) {
			if(gameWord.contains(String.valueOf(ch))) {
				numCommon++;
			}
		}
		return numCommon;
	}
	
	private boolean isWin(String word) {
		return word.equals(gameWord);
	}
	
	private String getUnique(String word) {
		String unique = "";
		for(char ch : word.toCharArray()) {
			if(!unique.contains(String.valueOf(ch))) {
				unique += ch;
			}
		}
		return unique;
	}

	private String generateGuessWord() {
		return dictionary.iterator().next();
	}
	
	private Set<String> getPermutations(String word, int length) {
		return Permutations.permutationFinder(word, length); 
	}
	
	private void filterDictionary(String word, int numOfCharsMatch) {
		if(numOfCharsMatch == 0) {
			filterDictionary(word);
			return;
		}
		Set<String> permutations = getPermutations(word, numOfCharsMatch);
		Iterator<String> itr = dictionary.iterator();
		while(itr.hasNext()) {
			String dictWord = itr.next();
			if(!isPresent(permutations, dictWord)) {
				itr.remove();
			}
		}
	}
	
	private void filterDictionary(String word) {
		for(char ch : word.toCharArray()) {
			Iterator<String> itr = dictionary.iterator();
			while(itr.hasNext()) {
				String dictWord = itr.next();
				if(dictWord.contains(String.valueOf(ch))) {
					itr.remove();
				}
			}
		}
	}

	private boolean isPresent(Set<String> permutations, String dictWord) {
		for(String perm : permutations) {
			String regex = perm.replaceAll("", ".*");
			if(dictWord.matches(regex)) {
				return true;
			}
		}
		return false;
	}

	private void generateGameWord() {
		int randomNum =(int)(Math.random() * (dictionary.size() - 1));
		List<String> list = new ArrayList<String>(dictionary);
		gameWord = list.get(randomNum);
	}
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		MasterMind game = new MasterMind();
		game.readFile(args[0]);
		
		game.getWordSet(allWords, Integer.parseInt(args[1]));
		System.out.println(game.dictionary);
		game.generateGameWord();
		System.out.println(game.gameWord);
		
		game.gameWord = "ABC";
		
		//System.out.println(game.commonCharsCount("BCD"));
		
		//Scanner sc = new Scanner(System.in);
		//System.out.println(choose);
		//System.out.println(Permutations.permutationFinder("ABC"));
	}
}