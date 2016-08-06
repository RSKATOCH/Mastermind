import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MasterMind {

	private Set<String> dictionary = new HashSet<String>();
	private String gameWord;
	
	private String getSorted(String word) {
		char[] wordArray = word.toCharArray();
		Arrays.sort(wordArray);
		return String.valueOf(wordArray);
	}
	
	public void readFile(String filePath, int length) throws Exception {
		getWordSet((ArrayList<String>) Files.readAllLines(Paths.get(filePath)), length);
	}
	
	private void getWordSet(ArrayList<String> readAllLines, int length) {
		// TODO Auto-generated method stub
		for(String line  : readAllLines) {
			if(line.length() == length && hasUniqueLetters(line)) {
				dictionary.add(line);
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
	
	private void filterDictionary(String word, int numOfCharsMatch) {
		
	}
	
	private void generateGameWord() {
		
	}
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		MasterMind game = new MasterMind();
		
		game.readFile(args[0], Integer.parseInt(args[1]));
		System.out.println(game.dictionary);
		//Scanner sc = new Scanner(System.in);
		//System.out.println(choose);
		//System.out.println(Permutations.permutationFinder("ABC"));
	}
}