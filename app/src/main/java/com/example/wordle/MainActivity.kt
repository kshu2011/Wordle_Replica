package com.example.wordle

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val words = FourLetterWordList
    var wordToGuess = words.getRandomFourLetterWord()
    val numberOfGuesses = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var start = 0
        var submissions = 0

        Log.d("my", wordToGuess)
        val answerDisplay = findViewById<TextView>(R.id.answer)
        answerDisplay.setText(wordToGuess)

        //grab all the editText and button
        val editText1 = findViewById<EditText>(R.id.editText1)
        val editText2 = findViewById<EditText>(R.id.editText2)
        val editText3 = findViewById<EditText>(R.id.editText3)
        val editText4 = findViewById<EditText>(R.id.editText4)

        val editText5 = findViewById<EditText>(R.id.editText5)
        val editText6 = findViewById<EditText>(R.id.editText6)
        val editText7 = findViewById<EditText>(R.id.editText7)
        val editText8 = findViewById<EditText>(R.id.editText8)

        val editText9 = findViewById<EditText>(R.id.editText9)
        val editText10 = findViewById<EditText>(R.id.editText10)
        val editText11 = findViewById<EditText>(R.id.editText11)
        val editText12 = findViewById<EditText>(R.id.editText12)

        //add all buttons into an arraylist and disable all not needed buttons first
        val editTextList = ArrayList<EditText>()
        editTextList.add(editText1)
        editTextList.add(editText2)
        editTextList.add(editText3)
        editTextList.add(editText4)
        editTextList.add(editText5)
        editTextList.add(editText6)
        editTextList.add(editText7)
        editTextList.add(editText8)
        editTextList.add(editText9)
        editTextList.add(editText10)
        editTextList.add(editText11)
        editTextList.add(editText12)

        //disable all the editText fields not needed yet, so 5-12
        enableEditText(editTextList, 4, editTextList.size - 1, false)

        val submitButton = findViewById<Button>(R.id.button)
        val resetButton = findViewById<Button>(R.id.reset)

        submitButton.setOnClickListener {
            //this is where users input gets "submitted" so need logic to check their submission
            // only get to submit so many times though
            if (submissions < numberOfGuesses) {
                var usersGuess = ""

                //get the word that the user guessed here
                for ((index, editTextItem) in editTextList.withIndex()) {
                    if (index >= start && index <= (start + 3)) {
                        usersGuess += editTextItem.getText().toString()
                    }
                }
                // check the users guess, see what they got right and update the color
                // red is wrong, green is right, yellow is partial
                val guessResult = checkGuess(usersGuess.uppercase())
                colorChange(guessResult, editTextList, start)
                if (guessResult.equals("OOOO")) {
                    Toast.makeText(getApplicationContext(), "CORRECT!", Toast.LENGTH_SHORT).show();
                }
                start += 4 //that's where next word would start
                //need to enable next "guess"
                enableEditText(editTextList, start, (start + 3), true)
            }
            submissions += 1
            if (submissions == 3) {
                answerDisplay.visibility = View.VISIBLE
            }
        }

        resetButton.setOnClickListener {
            for (editTextItem in editTextList) {
                editTextItem.setText("")
            }
        }
    }

    /** This function will enable or disable the editText fields*/
    private fun enableEditText(editTextList: ArrayList<EditText>, start: Int, end: Int, enable: Boolean) {
        for ((index, editTextItem) in editTextList.withIndex()) {
            if ((index >= start) && (index <= end)) {
                editTextItem.setEnabled(enable)
            }
        }
    }

    /** This function will be used to change the color of the characters */
    private fun colorChange(guessResult: String, userLetters: ArrayList<EditText>, startIndex: Int) {
        for (i in 0..3) {
            var currentText = userLetters[i + startIndex]
            if (guessResult[i] == 'O') {
                //green
                currentText.setTextColor(Color.GREEN)
            }
            else if (guessResult[i] == '+') {
                //yellow
                currentText.setTextColor(Color.YELLOW)
            }
            else {
                //red
                currentText.setTextColor(Color.RED)
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }


    // author: calren
    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        val fourLetterWords =
            "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

        // Returns a list of four letter words as a list
        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }
        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber].uppercase()
        }
    }
}