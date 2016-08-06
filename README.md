# Mastermind
Game of Mastermind

Concept : You think of a word, secret word, no repetitions in this word. Opponent tells a word which can have a repeated letters. The player tells the opponent whether he has won or the number of letters the two words have in common (repetitions counted only once)

Dictionary : sowpods.txt

Approach : Dictionary contains 4 letter words (for easy), provide a random word and check how many letters are in common. If there are 0 letters in common, we remove all words that have these letters and valid anagrams of it. If some x characters are in common, remove the words which do not have any of these letters and keep only those which have any of the 4px permutations of the word, since only they can be an answer.

update 10:00AM 
- Added Tester main function
- Tested one sided (user guesses computer's word) functionality
- Second side filter code being worked on.

