import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import java.util.Scanner;

import java.util.List;

import java.util.Set;

public class MasterMind {

	private Set<String> dictionary = new HashSet<String>();
	private String gameWord;
	/*Function to get sorted strings */
	private String getSorted(String word) {
		char[] wordArray = word.toCharArray();
		Arrays.sort(wordArray);
		return String.valueOf(wordArray);
	}
	
	/*Function to get read file*/
	public void readFile(String filePath, int length) throws Exception {
		getWordSet((ArrayList<String>) Files.readAllLines(Paths.get(filePath)), length);
	}
	
	/*Function to create dictionary*/
	private void getWordSet(ArrayList<String> readAllLines, int length) {
		// TODO Auto-generated method stub
		for(String line  : readAllLines) {
			if(line.length() == length && hasUniqueLetters(line)) {
				dictionary.add(line);
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
	private boolean isValid(String word) {
		return dictionary.contains(word);
	}
	private void generateGameWord() {
		int randomNum =(int)(Math.random() * (dictionary.size() - 1));
		List<String> list = new ArrayList<String>(dictionary);
		gameWord = list.get(randomNum);
	}
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		MasterMind game = new MasterMind();
		Scanner sc = new Scanner(System.in);
		game.readFile(args[0], Integer.parseInt(args[1]));
		
		game.generateGameWord();
		//System.out.println(game.gameWord);
		String word="";
		do{
			/*User moves first*/
			
			word = sc.next();
			if(!game.isValid(word)) {
				System.out.println("Please Enter a valid word");
				continue;
			}
			System.out.println(game.commonCharsCount(word));
			
		}while(!game.isWin(word));
			

		/*System.out.println(game.dictionary);
		game.generateGameWord();
		System.out.println(game.gameWord);
*/
		//Scanner sc = new Scanner(System.in);
		//System.out.println(choose);
		//System.out.println(Permutations.permutationFinder("ABC"));
	}
}