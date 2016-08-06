import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
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
	
	private void getWordSet(int length) {
		for(String line  : allWords) {
			if(line.length() == length && hasUniqueLetters(line)) {
				dictionary.add(line.toUpperCase());
			}
		}
	}
	
	/*Dictionary contains only unique characters*/
	private boolean hasUniqueLetters(String line) {
		for(char ch : line.toCharArray()) {
			if(line.indexOf(ch) != line.lastIndexOf(ch)) {
				return false;
			}
		}
		return true;
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
		//System.out.println("Permutaions : " + Permutations.permutationFinder(word, length));
		return Permutations.permutationFinder(word, length); 
	}
	
	private Set<String> getLargerPermutations(String word, int length) {
		//System.out.println("Permutaions : " + Permutations.permutationFinder(word, length));
		return Permutations.permutationLargerFinder(word, length); 
	}
	private void filterDictionary(String word, int numOfCharsMatch) {
		if(numOfCharsMatch == 0) {
			filterDictionary(word);
			return;
		}
		Set<String> permutations = getPermutations(word, numOfCharsMatch);
		Set<String> largerPermuations = getLargerPermutations(word, numOfCharsMatch);
		Iterator<String> itr = dictionary.iterator();
		while(itr.hasNext()) {
			String dictWord = itr.next();
			if(!isPresent(permutations, largerPermuations, dictWord)) {
				itr.remove();
			}
		}
		if(dictionary.contains(word)) {
			dictionary.remove(word);
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

	private boolean isPresent(Set<String> permutations, Set<String> largerPermuations, String dictWord) {
		
		for(String perm : largerPermuations) {
			String regex = perm.replaceAll("", ".*");
			if(dictWord.matches(regex)) {
				return false;
			}
		}
		
		for(String perm : permutations) {
			String regex = perm.replaceAll("", ".*");
			if(dictWord.matches(regex)) {
				return true;
			}
		}
		return false;
	}

	private boolean isValid(String word) {
		return allWords.contains(word);
	}
	
	private void generateGameWord() {
		int randomNum =(int)(Math.random() * (dictionary.size() - 1));
		List<String> list = new ArrayList<String>(dictionary);
		gameWord = list.get(randomNum);
	}
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		MasterMind game = new MasterMind();
		game.readFile(args[0]);
		game.getWordSet(Integer.parseInt(args[1]));
		Scanner sc = new Scanner(System.in);
		game.generateGameWord();
		System.out.println("I have guessed my word!!!" + game.gameWord);
		String word = "";
		boolean playerWon = true;
		do{
			System.out.println("What's your guess word?");
			word = sc.next();
			
			while(!game.isValid(word)) {
				System.out.println("Please Enter a valid word");
				word = sc.next();
			}
			
			System.out.println("Number of Matches : " + game.commonCharsCount(word));
			String compGuessWord = game.generateGuessWord();
			System.out.println("My guess word is : " + compGuessWord);
			System.out.println("How many matches? Enter \"win\" if I guessed Correct");
			String numOfCharsMatch = sc.next();
			
			if(numOfCharsMatch.equalsIgnoreCase("win")) {
				System.out.println("I Won! Thank You for Playing!!!");
				playerWon = false;
				break;
			}
			
			game.filterDictionary(compGuessWord, Integer.parseInt(numOfCharsMatch));	
			//System.out.println(game.dictionary.size());
			System.out.println();
			
		}while(!game.isWin(word));
		
		if(playerWon)
			System.out.println("YOU WON!!! Congratulations");
	}
}