import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MastermindNew {
	
	private static List<String> allWords = new ArrayList<String>(); 
	private static int EASY =4;
	private static int MEDIUM =5;
	private static int HARD =6;
	
	private Set<String> dictionary;
	private String gameWord;

	public void readFile(String filePath) throws Exception {
		allWords = (List<String>) Files.readAllLines(Paths.get(filePath));
	}
	
	private void getWordSet(int length) {
		dictionary = new HashSet<String>();
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
	
	private int commonCharsCount(String word, String gameWord) {
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
		Iterator<String> itr = dictionary.iterator();
		while(itr.hasNext()) {
			String dictWord = itr.next();
			if(commonCharsCount(dictWord, word) != numOfCharsMatch) {
				itr.remove();
			}
		}
		if(dictionary.contains(word)) {
			dictionary.remove(word);
		}
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
		MastermindNew game = new MastermindNew();
		
		game.readFile(args[0]);
		Scanner sc = new Scanner(System.in);
		String gameContinue=null;
		do{
			
			System.out.println("Select difficulty (E|M|H): ");
			String difficultyLevel = sc.next();
			if(difficultyLevel.equalsIgnoreCase("E")) game.getWordSet(MastermindNew.EASY);
			if(difficultyLevel.equalsIgnoreCase("M")) game.getWordSet(MastermindNew.MEDIUM);
			if(difficultyLevel.equalsIgnoreCase("H")) game.getWordSet(MastermindNew.HARD);
			game.generateGameWord();
			System.out.println("I have guessed my word!!!"); //+ game.gameWord);
			String word = "";
			boolean playerWon = true;
			do{
				System.out.println("What's your guess word?");
				word = sc.next().toUpperCase();
				
				while(!game.isValid(word)) {
					System.out.println("Please Enter a valid word");
					word = sc.next().toUpperCase();
				}
				
				if(game.isWin(word)) {
					break;
				}
				
				System.out.println("Number of Matches : " + game.commonCharsCount(word));
				String compGuessWord = game.generateGuessWord();
				System.out.println("My guess word is : " + compGuessWord);
				System.out.println("How many matches? Enter \"win\" if I guessed Correct");
				String numOfCharsMatch = sc.next();
				
				if(numOfCharsMatch.equalsIgnoreCase("win")) {
					System.out.println("I Won! My word is "+word);
					playerWon = false;
					break;
				}
				
				game.filterDictionary(compGuessWord, Integer.parseInt(numOfCharsMatch));	
				//System.out.println(game.dictionary.size());
				System.out.println();
				
			}while(!game.isWin(word));
			
			if(playerWon)
				System.out.println("YOU WON!!! Congratulations");
			System.out.println("Do you want to continue? (Yes/No)");
		}while(sc.next().equalsIgnoreCase("Yes"));
		System.out.println("Thank you for playing");
	}
}
