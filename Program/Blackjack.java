//////////////////////////////////////////////////////////////////////////
// Program Name: Blackjack                                              //
// Programmer: Thanushan Karunaharan                                    //
// Course: ICS3U1                                                       //
// Teacher: Mr.Lin                                                      //
// Date Created: June 6th - June 18th 2011                              //
// Description: The game of Blackjack created using Java Applet to      //
//              simulate a variation of game played at Casinos          //
// RECOMMENDED DIMENSIONS OF APPLET:  width: 750  height: 475           //
//////////////////////////////////////////////////////////////////////////
// The "Blackjack" class.
import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Blackjack extends Applet implements ActionListener
{

    // Place instance variables here
    Image menuScreen, gameScreen, helpScreen;
    JButton playButton, helpButton, menuButton, newRoundButton;
    JButton quitButton, drawButton, hitButton, standButton, doubleButton;
    JButton chip1Button, chip5Button, chip10Button, chip25Button;
    ImageIcon menu = new ImageIcon ("Images\\Game Buttons\\MenuButton.jpg");
    ImageIcon quit = new ImageIcon ("Images\\Game Buttons\\QuitButton.jpg");

    int totalChips, bankTotal = 200;
    JButton clickChip;

    ImageIcon[] [] cards = new ImageIcon [4] [13];
    byte cardSuit, cardValue;
    JButton displayCard = null, displayCard2 = null, displayCard3 = null, displayCard4 = null, displayBackCard = null;
    JButton displayCard5 = null, displayCard6 = null, displayCard7 = null, displayCard8 = null, displayCard9 = null, displayCard10 = null;
    ImageIcon selectedCard, selectedCard2, selectedCard3, selectedCard4, selectedCard5, selectedCard6, selectedCard7, selectedCard8, selectedCard9, selectedCard10;

    byte screen, error = 0, message = 0, calledAction, numCalledHit = 0, numCalledStand = 0, dealerFirstCard, totalHand, hand;

    byte totalHandDraw = 0, totalHandHit1 = 0, totalHandHit2 = 0, totalHandHit3 = 0, totalDealerDraw = 0, totalDealerHit1 = 0, totalDealerHit2 = 0, totalDealerHit3 = 0;
    boolean bust, bustDealer, if17, is17;

    byte winner = 0, finalPlayerHand = 1, finalDealerHand = 1, origBankCount = 0, aceCounter = 0;

    int origBankTotal = 0;

    AudioClip background, chipsAudio, dealingAudio;

    //This method initializes the game
    public void init ()
    {
	mainMenu ();

	//Import the music files
	background = getAudioClip (getCodeBase (), "Audio\\G6.wav");
	chipsAudio = getAudioClip (getCodeBase (), "Audio\\chips.wav");
	dealingAudio = getAudioClip (getCodeBase (), "Audio\\dealing.wav");

	//Play background sound repeatedly
	background.loop ();

    } // init method


    //This method initilazes the buttons and images used to displays the rules screen
    public void rulesScreen ()
    {
	//fires layout manager so buttons can be placed anywhere
	setLayout (null);

	helpScreen = getImage (getCodeBase (), "Images\\Screens\\HelpScreen.jpg");

	//Menu Button from rules to menu screen
	menuButton = new JButton (menu);
	menuButton.setBounds (586, 426, 165, 49);
	menuButton.addActionListener (this);
	add (menuButton);
	menuButton.setBorderPainted (false);

    }


    //This method initilazes the buttons and images used to displays the Game table
    public void gameTable ()
    {
	ImageIcon draw, round, hit, stand, doubleDown, chip1, chip5, chip10, chip25;

	setLayout (null);

	gameScreen = getImage (getCodeBase (), "Images\\Screens\\GameScreen.jpg");

	//Quit Button to go back to menu screen
	quit = new ImageIcon ("Images\\Game Buttons\\QuitButton.png");
	quitButton = new JButton (quit);
	quitButton.setBounds (650, 440, 103, 40);
	quitButton.addActionListener (this);
	add (quitButton);
	quitButton.setBorderPainted (false);

	//New Round Button to play another round after the previous round is over
	round = new ImageIcon ("Images\\Game Buttons\\NewRoundButton.png");
	newRoundButton = new JButton (round);
	newRoundButton.setBounds (0, 383, 90, 33);
	newRoundButton.addActionListener (this);
	add (newRoundButton);
	newRoundButton.setBorderPainted (false);

	//Buttons for the options the user can choose
	draw = new ImageIcon ("Images\\Game Buttons\\Draw.jpg");
	hit = new ImageIcon ("Images\\Game Buttons\\Hit.jpg");
	stand = new ImageIcon ("Images\\Game Buttons\\Stand.jpg");
	doubleDown = new ImageIcon ("Images\\Game Buttons\\Double.jpg");

	drawButton = new JButton (draw);
	hitButton = new JButton (hit);
	standButton = new JButton (stand);
	doubleButton = new JButton (doubleDown);

	drawButton.setBounds (396, 365, 75, 82);
	hitButton.setBounds (491, 350, 75, 82);
	standButton.setBounds (583, 318, 75, 82);
	doubleButton.setBounds (668, 281, 75, 82);

	drawButton.addActionListener (this);
	hitButton.addActionListener (this);
	standButton.addActionListener (this);
	doubleButton.addActionListener (this);

	add (drawButton);
	add (hitButton);
	add (standButton);
	add (doubleButton);

	drawButton.setBorderPainted (false);
	hitButton.setBorderPainted (false);
	standButton.setBorderPainted (false);
	doubleButton.setBorderPainted (false);

	standButton.setEnabled (false);
	doubleButton.setEnabled (false);
	hitButton.setEnabled (false);

	//Chip Buttons used to initialize the wage for the round
	chip1 = new ImageIcon ("Images\\Game Buttons\\Chips1.jpg");
	chip5 = new ImageIcon ("Images\\Game Buttons\\Chips5.jpg");
	chip10 = new ImageIcon ("Images\\Game Buttons\\Chips10.jpg");
	chip25 = new ImageIcon ("Images\\Game Buttons\\Chips25.jpg");

	chip1Button = new JButton (chip1);
	chip5Button = new JButton (chip5);
	chip10Button = new JButton (chip10);
	chip25Button = new JButton (chip25);

	chip1Button.setBounds (32, 301, 32, 29);
	chip5Button.setBounds (79, 330, 31, 28);
	chip10Button.setBounds (124, 352, 31, 28);
	chip25Button.setBounds (173, 369, 31, 28);

	chip1Button.addActionListener (this);
	chip5Button.addActionListener (this);
	chip10Button.addActionListener (this);
	chip25Button.addActionListener (this);

	add (chip1Button);
	add (chip5Button);
	add (chip10Button);
	add (chip25Button);

	chip1Button.setBorderPainted (false);
	chip5Button.setBorderPainted (false);
	chip10Button.setBorderPainted (false);
	chip25Button.setBorderPainted (false);
    }


    //This method initilazes the buttons and images used to displays the menu screen
    public void mainMenu ()
    {

	bankTotal = 200; //If someone quits game and plays again, refreshes bank back to 200
	screen = 1;

	ImageIcon play, help, highScore;

	setLayout (null);

	menuScreen = getImage (getCodeBase (), "Images\\Screens\\MenuScreen.jpg");

	//Buttons used to wither go play BlackJack or view the Rules
	play = new ImageIcon ("Images\\Game Buttons\\PlayButton.jpg");
	help = new ImageIcon ("Images\\Game Buttons\\RulesButton.jpg");

	playButton = new JButton (play);
	helpButton = new JButton (help);

	playButton.setBounds (284, 104, 160, 60);
	helpButton.setBounds (285, 184, 160, 60);

	playButton.addActionListener (this);
	helpButton.addActionListener (this);

	add (playButton);
	add (helpButton);

	playButton.setBorderPainted (false);
	helpButton.setBorderPainted (false);

    }


    //This method is used to initilize the cards played for the round and displays them
    //depending on what the user clicks as their options.
    public void selectCard (byte action)
    {
	//Assigns card pictures onto an array
	cards [0] [0] = new ImageIcon ("Images\\Cards\\Clubs\\2clubs.png");
	cards [0] [1] = new ImageIcon ("Images\\Cards\\Clubs\\3clubs.png");
	cards [0] [2] = new ImageIcon ("Images\\Cards\\Clubs\\4clubs.png");
	cards [0] [3] = new ImageIcon ("Images\\Cards\\Clubs\\5clubs.png");
	cards [0] [4] = new ImageIcon ("Images\\Cards\\Clubs\\6clubs.png");
	cards [0] [5] = new ImageIcon ("Images\\Cards\\Clubs\\7clubs.png");
	cards [0] [6] = new ImageIcon ("Images\\Cards\\Clubs\\8clubs.png");
	cards [0] [7] = new ImageIcon ("Images\\Cards\\Clubs\\9clubs.png");
	cards [0] [8] = new ImageIcon ("Images\\Cards\\Clubs\\10clubs.png");
	cards [0] [9] = new ImageIcon ("Images\\Cards\\Clubs\\Aceclubs.png");
	cards [0] [10] = new ImageIcon ("Images\\Cards\\Clubs\\Jackclubs.png");
	cards [0] [11] = new ImageIcon ("Images\\Cards\\Clubs\\Queenclubs.png");
	cards [0] [12] = new ImageIcon ("Images\\Cards\\Clubs\\Kingclubs.png");

	cards [1] [0] = new ImageIcon ("Images\\Cards\\Diamonds\\2diamonds.png");
	cards [1] [1] = new ImageIcon ("Images\\Cards\\Diamonds\\3diamonds.png");
	cards [1] [2] = new ImageIcon ("Images\\Cards\\Diamonds\\4diamonds.png");
	cards [1] [3] = new ImageIcon ("Images\\Cards\\Diamonds\\5diamonds.png");
	cards [1] [4] = new ImageIcon ("Images\\Cards\\Diamonds\\6diamonds.png");
	cards [1] [5] = new ImageIcon ("Images\\Cards\\Diamonds\\7diamonds.png");
	cards [1] [6] = new ImageIcon ("Images\\Cards\\Diamonds\\8diamonds.png");
	cards [1] [7] = new ImageIcon ("Images\\Cards\\Diamonds\\9diamonds.png");
	cards [1] [8] = new ImageIcon ("Images\\Cards\\Diamonds\\10diamonds.png");
	cards [1] [9] = new ImageIcon ("Images\\Cards\\Diamonds\\Acediamonds.png");
	cards [1] [10] = new ImageIcon ("Images\\Cards\\Diamonds\\Jackdiamonds.png");
	cards [1] [11] = new ImageIcon ("Images\\Cards\\Diamonds\\Queendiamonds.png");
	cards [1] [12] = new ImageIcon ("Images\\Cards\\Diamonds\\Kingdiamonds.png");

	cards [2] [0] = new ImageIcon ("Images\\Cards\\Hearts\\2hearts.png");
	cards [2] [1] = new ImageIcon ("Images\\Cards\\Hearts\\3hearts.png");
	cards [2] [2] = new ImageIcon ("Images\\Cards\\Hearts\\4hearts.png");
	cards [2] [3] = new ImageIcon ("Images\\Cards\\Hearts\\5hearts.png");
	cards [2] [4] = new ImageIcon ("Images\\Cards\\Hearts\\6hearts.png");
	cards [2] [5] = new ImageIcon ("Images\\Cards\\Hearts\\7hearts.png");
	cards [2] [6] = new ImageIcon ("Images\\Cards\\Hearts\\8hearts.png");
	cards [2] [7] = new ImageIcon ("Images\\Cards\\Hearts\\9hearts.png");
	cards [2] [8] = new ImageIcon ("Images\\Cards\\Hearts\\10hearts.png");
	cards [2] [9] = new ImageIcon ("Images\\Cards\\Hearts\\Acehearts.png");
	cards [2] [10] = new ImageIcon ("Images\\Cards\\Hearts\\Jackhearts.png");
	cards [2] [11] = new ImageIcon ("Images\\Cards\\Hearts\\Queenhearts.png");
	cards [2] [12] = new ImageIcon ("Images\\Cards\\Hearts\\Kinghearts.png");

	cards [3] [0] = new ImageIcon ("Images\\Cards\\Spades\\2spades.png");
	cards [3] [1] = new ImageIcon ("Images\\Cards\\Spades\\3spades.png");
	cards [3] [2] = new ImageIcon ("Images\\Cards\\Spades\\4spades.png");
	cards [3] [3] = new ImageIcon ("Images\\Cards\\Spades\\5spades.png");
	cards [3] [4] = new ImageIcon ("Images\\Cards\\Spades\\6spades.png");
	cards [3] [5] = new ImageIcon ("Images\\Cards\\Spades\\7spades.png");
	cards [3] [6] = new ImageIcon ("Images\\Cards\\Spades\\8spades.png");
	cards [3] [7] = new ImageIcon ("Images\\Cards\\Spades\\9spades.png");
	cards [3] [8] = new ImageIcon ("Images\\Cards\\Spades\\10spades.png");
	cards [3] [9] = new ImageIcon ("Images\\Cards\\Spades\\Acespades.png");
	cards [3] [10] = new ImageIcon ("Images\\Cards\\Spades\\Jackspades.png");
	cards [3] [11] = new ImageIcon ("Images\\Cards\\Spades\\Queenspades.png");
	cards [3] [12] = new ImageIcon ("Images\\Cards\\Spades\\Kingspades.png");

	//Displays image of the dealers face down card
	ImageIcon backCard = new ImageIcon ("Images\\Cards\\BackofCard.jpg");
	displayBackCard = new JButton (backCard);
	add (displayBackCard);
	displayBackCard.setBorderPainted (false);
	displayBackCard.setEnabled (false);
	displayBackCard.setDisabledIcon (backCard);

	if (action == 1)
	{
	    //Randomizes the cards and doesnt allow the same card used for a round
	    byte checkRandom[] [] = new byte [4] [13];

	    for (byte random = 0 ; random < 10 ; random++)
	    {
		if (random == 0)
		{
		    cardSuit = (byte) (Math.random () * 3 + 1); //Randomize the Suit of the card
		    cardValue = (byte) (Math.random () * 12 + 1); //Randomize the Value of the card
		}

		//Assigns the selected card suit and value number onto another array.
		//In that other array, a random number is assigned using the card suit and value number
		checkRandom [cardSuit] [cardValue] = 22;

		if (random != 0)
		{
		    do
		    {
			cardSuit = (byte) (Math.random () * 3 + 1);
			cardValue = (byte) (Math.random () * 12 + 1);
		    }
		    while (checkRandom [cardSuit] [cardValue] == 22); //Checks if the card has already been selected in the other array

		}

		// Assigns 10 buttons a randomized card
		if (random == 0)
		{
		    selectedCard = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard = new JButton (selectedCard);
		    add (displayCard);
		    displayCard.setBorderPainted (false);
		    displayCard.setEnabled (false);
		    displayCard.setDisabledIcon (selectedCard);
		}
		if (random == 1)
		{
		    selectedCard2 = (ImageIcon) (cards [cardSuit] [cardValue]);

		    displayCard2 = new JButton (selectedCard2);
		    add (displayCard2);
		    displayCard2.setBorderPainted (false);
		    displayCard2.setEnabled (false);
		    displayCard2.setDisabledIcon (selectedCard2);

		}
		if (random == 2)
		{
		    selectedCard3 = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard3 = new JButton (selectedCard3);
		    add (displayCard3);
		    displayCard3.setBorderPainted (false);
		    displayCard.setEnabled (false);
		    displayCard.setDisabledIcon (selectedCard);
		    displayCard3.setEnabled (false);
		    displayCard3.setDisabledIcon (selectedCard3);

		}
		if (random == 3)
		{
		    selectedCard4 = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard4 = new JButton (selectedCard4);
		    add (displayCard4);
		    displayCard4.setBorderPainted (false);
		    displayCard4.setEnabled (false);
		    displayCard4.setDisabledIcon (selectedCard4);

		}
		if (random == 4)
		{
		    selectedCard5 = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard5 = new JButton (selectedCard5);
		    add (displayCard5);
		    displayCard5.setBorderPainted (false);
		    displayCard5.setEnabled (false);
		    displayCard5.setDisabledIcon (selectedCard5);
		}
		if (random == 5)
		{
		    selectedCard6 = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard6 = new JButton (selectedCard6);
		    add (displayCard6);
		    displayCard6.setBorderPainted (false);
		    displayCard6.setEnabled (false);
		    displayCard6.setDisabledIcon (selectedCard6);

		}
		if (random == 6)
		{
		    selectedCard7 = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard7 = new JButton (selectedCard7);
		    add (displayCard7);
		    displayCard7.setBorderPainted (false);
		    displayCard7.setEnabled (false);
		    displayCard7.setDisabledIcon (selectedCard7);

		}
		if (random == 7)
		{
		    selectedCard8 = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard8 = new JButton (selectedCard8);
		    add (displayCard8);
		    displayCard8.setBorderPainted (false);
		    displayCard8.setEnabled (false);
		    displayCard8.setDisabledIcon (selectedCard8);

		}
		if (random == 8)
		{
		    selectedCard9 = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard9 = new JButton (selectedCard9);
		    add (displayCard9);
		    displayCard9.setBorderPainted (false);
		    displayCard9.setEnabled (false);
		    displayCard9.setDisabledIcon (selectedCard9);

		}
		if (random == 9)
		{
		    selectedCard10 = (ImageIcon) (cards [cardSuit] [cardValue]);
		    displayCard10 = new JButton (selectedCard9);
		    add (displayCard10);
		    displayCard10.setBorderPainted (false);
		    displayCard10.setEnabled (false);
		    displayCard10.setDisabledIcon (selectedCard10);

		}
	    } //for loop using to randomize cards
	}

	//Executed when player draws
	if (action == 1)
	{
	    //Checking individual cards hand value of player's cards
	    handValue (selectedCard);
	    handValue (selectedCard2);
	    totalHandDraw = totalHand; //Assigns total hand value of two cards

	    totalHand = 0;

	    handValue (selectedCard4);
	    handValue (selectedCard);
	    handValue (selectedCard2);
	    aceCounter = 1; //Determine progression of player in game to help Ace method

	    Ace (); //Checks if there is an ace in the cards
	    totalHandHit1 = totalHand; //Assigns total hand value of three cards

	    totalHand = 0;
	    handValue (selectedCard);
	    handValue (selectedCard2);
	    handValue (selectedCard4);
	    handValue (selectedCard5);
	    aceCounter = 2;

	    Ace ();
	    totalHandHit2 = totalHand; //Assigns total hand value of four cards

	    totalHand = 0;
	    handValue (selectedCard);
	    handValue (selectedCard2);
	    handValue (selectedCard4);
	    handValue (selectedCard5);
	    handValue (selectedCard6); //Assigns total hand value of five cards
	    aceCounter = 3;

	    Ace ();
	    totalHandHit3 = totalHand;

	    totalHand = 0;

	    //Checking individual cards hand value of player's cards
	    handValue (selectedCard3);
	    dealerFirstCard = totalHand; //Assigns dealers first cards hand value so its continuously displayed during players turn
	    handValue (selectedCard7);
	    totalDealerDraw = totalHand;
	    aceCounter = 4;

	    totalHand = 0;

	    handValue (selectedCard3);
	    handValue (selectedCard7);
	    handValue (selectedCard8);
	    aceCounter = 5;

	    Ace ();
	    totalDealerHit1 = totalHand;

	    totalHand = 0;

	    handValue (selectedCard3);
	    handValue (selectedCard7);
	    handValue (selectedCard8);
	    handValue (selectedCard9);
	    aceCounter = 6;

	    Ace ();
	    totalDealerHit2 = totalHand;

	    totalHand = 0;

	    handValue (selectedCard3);
	    handValue (selectedCard7);
	    handValue (selectedCard8);
	    handValue (selectedCard9);
	    handValue (selectedCard10);
	    aceCounter = 7;

	    Ace ();
	    totalDealerHit3 = totalHand;

	    //Displays the players two cards and dealers one card face up and one card face down
	    displayCard.setBounds (300, 200, 70, 95);
	    displayCard2.setBounds (400, 200, 70, 95);
	    displayCard3.setBounds (400, 50, 70, 95);
	    displayBackCard.setBounds (300, 50, 70, 95);

	    checkBust (totalHandDraw); // Checks whether the hand is over 21
	}

	//Executed if player clicks Hit
	if (action == 2)
	{
	    //If Hit clicked once displays the original cards plus an extra added card
	    if (numCalledHit == 1)
	    {
		repaint ();
		displayCard.setBounds (250, 200, 70, 95);
		displayCard2.setBounds (350, 200, 70, 95);
		displayCard4.setBounds (450, 200, 70, 95);
		displayBackCard.setBounds (300, 50, 70, 95);

		checkBust (totalHandHit1);

	    }

	    //If Hit clicked 2nd time the original three cards plus an extra added card
	    else if (numCalledHit == 2)
	    {
		totalHand = 0;
		repaint ();
		displayCard.setBounds (200, 200, 70, 95);
		displayCard2.setBounds (300, 200, 70, 95);
		displayCard4.setBounds (400, 200, 70, 95);
		displayCard5.setBounds (500, 200, 70, 95);
		displayBackCard.setBounds (300, 50, 70, 95);

		checkBust (totalHandHit2);
	    }

	    //If Hit clicked third time the original four cards plus an extra added card
	    else if (numCalledHit == 3)
	    {
		repaint ();
		displayCard.setBounds (150, 200, 70, 95);
		displayCard2.setBounds (250, 200, 70, 95);
		displayCard4.setBounds (350, 200, 70, 95);
		displayCard5.setBounds (450, 200, 70, 95);
		displayCard6.setBounds (550, 200, 70, 95);
		displayBackCard.setBounds (300, 50, 70, 95);

		checkBust (totalHandHit3);
	    }
	}

	//Executed if player clicks Stand (dealers turn)
	else if (action == 3)
	{
	    //Displays dealers both cards are face up
	    if (numCalledStand == 1)
	    {
		repaint ();
		displayCard3.setBounds (300, 50, 70, 95);
		displayCard7.setBounds (400, 50, 70, 95);

		checkBust (totalDealerDraw); //Checks if dealers hand is over 21
		is17 = dealerCondition (totalDealerDraw); //Checks if the hand value is 17

	    }

	    //Displays dealers both cards and an extra added card
	    else if (numCalledStand == 2)
	    {
		repaint ();
		displayCard3.setBounds (250, 50, 70, 95);
		displayCard7.setBounds (350, 50, 70, 95);
		displayCard8.setBounds (450, 50, 70, 95);

		checkBust (totalDealerHit1);
		is17 = dealerCondition (totalDealerHit1);
	    }

	    //Displays dealers three cards and an extra added card
	    else if (numCalledStand == 3)
	    {
		repaint ();
		displayCard3.setBounds (200, 50, 70, 95);
		displayCard7.setBounds (300, 50, 70, 95);
		displayCard8.setBounds (400, 50, 70, 95);
		displayCard9.setBounds (500, 50, 70, 95);

		checkBust (totalDealerHit2);
		is17 = dealerCondition (totalDealerHit2);
	    }

	    //Displays dealers four cards and an extra added card
	    else if (numCalledStand == 4)
	    {
		repaint ();
		displayCard3.setBounds (150, 50, 70, 95);
		displayCard7.setBounds (250, 50, 70, 95);
		displayCard8.setBounds (350, 50, 70, 95);
		displayCard9.setBounds (450, 50, 70, 95);
		displayCard10.setBounds (550, 50, 70, 95);

		checkBust (totalDealerHit3);
		is17 = dealerCondition (totalDealerHit3);

	    }
	}
    }


    //This method checks whether there is an ace in the round. It also changes the
    //value of ace from 11 to 1 if it is necessary to continue playing without bust.
    public void Ace ()
    {
	//Counter used to check every ace suit
	for (int aceCount = 0 ; aceCount < 4 ; aceCount++)
	{
	    //Turns the card that is ace to 1 by substracting 10 from the total hand value
	    //only if the player/dealer would have busted without turning ace to 1

	    //Checking players Draw cards
	    if (totalHand > 21 && (selectedCard == cards [aceCount] [9]))
	    {
		totalHand -= 10;
	    }
	    if (totalHand > 21 && (selectedCard2 == cards [aceCount] [9]))
	    {
		totalHand -= 10;
	    }

	    //Checking players total hand value for every hit
	    if (aceCounter == 1)
	    {
		if (totalHand > 21 && (selectedCard4 == cards [aceCount] [9]))
		{
		    totalHand -= 10;
		}
	    }
	    else if (aceCounter == 2)
	    {
		if (totalHand > 21 && (selectedCard4 == cards [aceCount] [9] || selectedCard5 == cards [aceCount] [9]))
		{
		    totalHand -= 10;
		}
	    }
	    else if (aceCounter == 3)
	    {
		if (totalHand > 21 && (selectedCard4 == cards [aceCount] [9] || selectedCard5 == cards [aceCount] [9] || selectedCard6 == cards [aceCount] [9]))
		{
		    totalHand -= 10;
		}
	    }

	    //Checking dealers total hand value for their draw and every hit
	    if (aceCounter == 4)
	    {
		if (totalHand > 21 && (selectedCard4 == cards [aceCount] [9] || selectedCard5 == cards [aceCount] [9] || selectedCard6 == cards [aceCount] [9] ||
			    selectedCard3 == cards [aceCount] [9] || selectedCard7 == cards [aceCount] [9]))
		{
		    totalHand -= 10;
		}
	    }
	    else if (aceCounter == 5)
	    {
		if (totalHand > 21 && (selectedCard4 == cards [aceCount] [9] || selectedCard5 == cards [aceCount] [9] || selectedCard6 == cards [aceCount] [9] ||
			    selectedCard3 == cards [aceCount] [9] || selectedCard7 == cards [aceCount] [9] || selectedCard8 == cards [aceCount] [9]))
		{
		    totalHand -= 10;
		}
	    }
	    else if (aceCounter == 6)
	    {
		if (totalHand > 21 && (selectedCard4 == cards [aceCount] [9] || selectedCard5 == cards [aceCount] [9] || selectedCard6 == cards [aceCount] [9] ||
			    selectedCard3 == cards [aceCount] [9] || selectedCard7 == cards [aceCount] [9] || selectedCard8 == cards [aceCount] [9] || selectedCard9 == cards [aceCount] [9]))
		{
		    totalHand -= 10;
		}
	    }
	    else if (aceCounter == 7)
	    {
		if (totalHand > 21 && (selectedCard4 == cards [aceCount] [9] || selectedCard5 == cards [aceCount] [9] || selectedCard6 == cards [aceCount] [9] ||
			    selectedCard3 == cards [aceCount] [9] || selectedCard7 == cards [aceCount] [9] || selectedCard8 == cards [aceCount] [9] || selectedCard9 == cards [aceCount] [9]
			    || selectedCard10 == cards [aceCount] [9]))
		{
		    totalHand -= 10;
		}
	    }
	} //for loop
    }


    //This method sets what happens when the Draw button is clicked
    public void Draw ()
    {
	calledAction = 1;
	selectCard (calledAction); //calls method to display players first 2 cards

	//Allows the three options to be clicked
	standButton.setEnabled (true);
	doubleButton.setEnabled (true);
	hitButton.setEnabled (true);

	//Checks if the first two cards equal 21 (Blackjack)
	if (totalHandDraw == 21)
	{
	    message = 7;
	    bankTotal += (totalChips * 2); // wins amount equal to wage
	    repaint ();

	    //Disables the choices now that round is over
	    hitButton.setEnabled (false);
	    standButton.setEnabled (false);
	    doubleButton.setEnabled (false);
	    newRoundButton.setEnabled (true);

	}
    }


    //This method sets what happens when the Double button is clicked
    public void DoubleDown ()
    {
	//Calls only one extra card to be added to total hand value
	calledAction = 2;
	numCalledHit = 1;

	//Wage doubles and bank total drops by amount of wage increase
	bankTotal -= totalChips;
	totalChips += totalChips;
	repaint ();

	selectCard (calledAction);

	finalPlayerHand = totalHandHit1; //Varaible used to eventually display the hand value when double is clicked
    }


    //This method sets what happens when the Hit button is clicked
    public void Hit ()
    {
	numCalledHit++; //Increments each time Hit is clicked
	calledAction = 2;
	selectCard (calledAction); //calls one card to be displayed

	//When five cards are called without the hand value going over 21 (Five Card Charlie)
	if (totalHandHit3 <= 21 && numCalledHit == 3)
	{
	    message = 8;
	    winner = 1; //assigns winner a number which later is used to identify that player won the round
	    repaint ();

	    //Disables the choices now that round is over
	    hitButton.setEnabled (false);
	    standButton.setEnabled (false);
	    doubleButton.setEnabled (false);
	    newRoundButton.setEnabled (true);
	}
    }


    //This method sets what happens when the Stand button is clicked
    public void Stand ()
    {
	calledAction = 3;

	numCalledStand++; //Increments each time Stand is called
	selectCard (calledAction);

	doubleButton.setEnabled (false);
	hitButton.setEnabled (false);
	standButton.setEnabled (false);
	drawButton.setEnabled (false);

    }


    //Method that assigns the hand value of individual card
    public void handValue (ImageIcon card)
    {
	hand = 0;

	//Counter used to go through every suit
	for (int count1 = 0 ; count1 < 4 ; count1++)
	{
	    //Hand value for cards from 2 - 9
	    for (int count = 0 ; count < 9 ; count++)
	    {
		if (card == cards [count1] [count])
		{
		    hand = (byte) (count + 2);
		}

	    }
	    //Hand value for the cards 10, Jack, Queen, and King
	    for (int count2 = 10 ; count2 <= 12 ; count2++)
	    {
		if (card == cards [count1] [count2])
		{
		    hand = 10;
		}
	    }

	    //Hand value for the card Ace
	    if (card == cards [count1] [9])
	    {
		if (totalHand < 11) //Normally during draw, assigns ace's hand value as 11
		{
		    hand = 11;
		}
		else
		{
		    hand = 1; //Asigned if total hand value would have gone over 21 with 11
		}
	    }
	}
	totalHand += hand; //Determines total hand value by adding individual cards hand value
    }


    //Method checks whethere the total hand value is over 21 for both dealer and player
    public void checkBust (byte currentHand)
    {
	bust = false;
	bustDealer = false;

	//Checking if dealer busts
	if (calledAction == 3 && currentHand > 21)
	{
	    bustDealer = true;
	    repaint ();
	    hitButton.setEnabled (false);
	    standButton.setEnabled (false);
	    doubleButton.setEnabled (false);
	}

	//Checking if player busts
	else if (calledAction == 2 && currentHand > 21)
	{
	    bust = true;
	    repaint ();
	    hitButton.setEnabled (false);
	    standButton.setEnabled (false);
	    doubleButton.setEnabled (false);
	    newRoundButton.setEnabled (true);
	}
    }


    //Method checks if dealers hand is 17 or over. If so, dealer automatically stands
    public boolean dealerCondition (byte currentHand)
    {
	if17 = false;

	if (currentHand >= 17)
	{
	    if17 = true;
	}

	return if17;
    }


    //Method used to identify both dealers and players final total hand value and checks
    //if either player wins, loses, or if its a draw
    public void Winner ()
    {
	//determining players last total hand value
	if (calledAction == 1)
	{
	    finalPlayerHand = totalHandDraw;
	}

	if (numCalledHit == 1)
	{
	    finalPlayerHand = totalHandHit1;
	}
	else if (numCalledHit == 2)
	{
	    finalPlayerHand = totalHandHit2;
	}
	else if (numCalledHit == 3)
	{
	    finalPlayerHand = totalHandHit3;
	}

	//determining dealers last total hand value
	if (numCalledStand == 1)
	{
	    finalDealerHand = totalDealerDraw;
	}
	else if (numCalledStand == 2)
	{
	    finalDealerHand = totalDealerHit1;
	}
	else if (numCalledStand == 3)
	{
	    finalDealerHand = totalDealerHit2;
	}
	else if (numCalledStand == 4)
	{
	    finalDealerHand = totalDealerHit3;
	}

	//Determining winner of round
	if (finalPlayerHand > 0 && finalDealerHand > 0)
	{
	    //If player wins
	    if (finalPlayerHand > finalDealerHand)
	    {
		winner = 1;
	    }

	    //If player loses
	    else if (finalPlayerHand < finalDealerHand)
	    {
		winner = 2;
	    }

	    //If there is a draw
	    else
	    {
		winner = 3;
	    }
	}
    }


    //Method that determines the wage and bank total
    public void wageCount (JButton clicked)
    {
	//Stores the players bank total before they begin waging for the round
	origBankCount++;

	if (origBankCount == 1)
	{
	    origBankTotal = bankTotal;
	}

	//Updates wage and bank whenever chips clicked
	if (clicked == chip1Button)
	{
	    totalChips += 1;
	    bankTotal -= 1;
	}
	else if (clicked == chip5Button)
	{
	    totalChips += 5;
	    bankTotal -= 5;
	}
	else if (clicked == chip10Button)
	{
	    totalChips += 10;
	    bankTotal -= 10;
	}
	else if (clicked == chip25Button)
	{
	    totalChips += 25;
	    bankTotal -= 25;
	}

	//If illegal wager is made, doesnt allow wage or bank to be updated
	if (totalChips > 50 || totalChips > origBankTotal)
	{
	    //If player is trying to bet over table max of 50
	    if (totalChips > 50)
	    {
		error = 1;
	    }

	    //If player is trying to bet more than the amount they have in the bank
	    if (totalChips > origBankTotal)
	    {
		message = 0;
		error = 3;
	    }

	    //Updates wage and bank total as player continues to click on chips
	    if (clicked == chip1Button)
	    {
		totalChips -= 1;
		bankTotal += 1;
	    }
	    else if (clicked == chip5Button)
	    {
		totalChips -= 5;
		bankTotal += 5;
	    }
	    else if (clicked == chip10Button)
	    {
		totalChips -= 10;
		bankTotal += 10;
	    }
	    else if (clicked == chip25Button)
	    {
		totalChips -= 25;
		bankTotal += 25;
	    }
	}
    }


    //Method used to reintialize the variables and conditions before the round began
    public void newRound ()
    {
	displayCard = null;
	displayCard2 = null;
	displayCard3 = null;
	displayCard4 = null;
	displayBackCard = null;
	displayCard5 = null;
	displayCard6 = null;
	displayCard7 = null;
	displayCard8 = null;
	displayCard9 = null;
	displayCard10 = null;

	totalHand = 0;
	error = 0;
	calledAction = 0;
	message = 0;
	totalChips = 0;
	origBankCount = 0;

	bust = false;
	bustDealer = false;

	numCalledHit = 0;
	numCalledStand = 0;
	totalHandDraw = 0;
	totalHandHit1 = 0;
	totalHandHit2 = 0;
	totalHandHit3 = 0;
	totalDealerDraw = 0;
	totalDealerHit1 = 0;
	totalDealerHit2 = 0;
	totalDealerHit3 = 0;
    }


    //Method is run everyime player clicks a button
    public void actionPerformed (ActionEvent e)
    {
	//Screens

	//Sets a a number to screen varaible and calls that screens buttons and images
	//so that different screens are displayed when button is clicked.
	if (e.getSource () == playButton)
	{
	    screen = 2; // represents game screen
	    removeAll ();
	    gameTable ();
	    repaint ();
	}
	else if (e.getSource () == helpButton)
	{
	    screen = 3; // represents help screen
	    removeAll ();
	    rulesScreen ();
	    repaint ();
	}

	if (e.getSource () == menuButton || e.getSource () == quitButton)
	{
	    removeAll ();
	    screen = 1; //represents menu screen
	    mainMenu ();
	    repaint ();
	    newRound (); // reintialize game variables and conditions
	}

	// Chips

	//enters body of if whenever the wage amount is no greater than 50
	if (totalChips < 51)
	{
	    if (e.getSource () == chip1Button || e.getSource () == chip5Button || e.getSource () == chip10Button || e.getSource () == chip25Button)
	    {
		chipsAudio.play (); //Audio when chip is clicked
		newRoundButton.setEnabled (false);

		//Executes if player does not have enough chips to continue playing at the table
		if (bankTotal < 5 && totalChips == 0)
		{
		    message = 6;
		    repaint ();
		    drawButton.setEnabled (false);
		}

		//Calls wageCount method so bank and wage can be updated
		else
		{
		    clickChip = (JButton) e.getSource ();
		    wageCount (clickChip);
		    repaint ();
		}
	    }
	}

	//Game Buttons

	if (e.getSource () == drawButton)
	{
	    dealingAudio.play (); //Audio when cards dealt

	    //Allow player to play only if their wage is greater than the table limit of $5
	    if (totalChips >= 5)
	    {
		chip1Button.setEnabled (false);
		chip5Button.setEnabled (false);
		chip10Button.setEnabled (false);
		chip25Button.setEnabled (false);

		error = 0;
		message = 1;
		repaint ();
		Draw ();
		drawButton.setEnabled (false);

	    }
	    else
	    {
		error = 2;
		repaint ();
	    }
	}

	//Calls Hit method whenever Hit is clicked
	if (e.getSource () == hitButton)
	{
	    dealingAudio.play ();
	    Hit ();
	}

	//Allows double to be clicked only if player has enough money in the bank to double their wager
	if (e.getSource () == doubleButton && (totalChips * 2) > origBankTotal)
	{
	    //Displays message and sets game back to the original conditions before the round was played
	    error = 4;
	    repaint ();
	    hitButton.setEnabled (false);
	    standButton.setEnabled (false);
	    newRoundButton.setEnabled (true);
	    bankTotal = origBankTotal;
	}
	else
	{
	    ///Runs whenever stand or double is clicked
	    if (e.getSource () == standButton || e.getSource () == doubleButton)
	    {
		Winner (); // Checks the outcome of the round

		if (e.getSource () == doubleButton)
		{
		    dealingAudio.play ();
		    DoubleDown ();

		}

		//Checks whether the player busted before the dealer begins their turn
		if (bust == false)
		{
		    Stand ();

		    //Dealer continues hitting unless they bust or if their total hand value is 17 or more
		    if (bustDealer == false && if17 == false)
		    {
			Stand ();
			if (bustDealer == false && if17 == false)
			{
			    Stand ();
			    if (bustDealer == false && if17 == false)
			    {
				Stand ();
			    }
			    else if (if17 == true) //Checks Winner, whenever dealer stands wihtout busting
			    {
				Winner ();
			    }
			}
			else if (if17 == true) //Checks Winner, whenever dealer stands wihtout busting
			{
			    Winner ();
			}
		    }
		    else if (if17 == true) //Checks Winner, whenever dealer stands wihtout busting
		    {
			Winner ();
		    }

		    //If player wins, message displayed and gains amount of wage
		    if (winner == 1 || bustDealer == true)
		    {
			message = 4;
			bankTotal += (totalChips * 2);
			repaint ();
			newRoundButton.setEnabled (true);
		    }

		    //if player loses, message is displayed and there is no increase in bank total
		    else if (winner == 2)
		    {
			message = 3;
			newRoundButton.setEnabled (true);
		    }

		    //if draw, player gets back amount of wage back
		    else if (winner == 3)
		    {
			message = 5;
			bankTotal += totalChips;
			repaint ();
			newRoundButton.setEnabled (true);
		    }
		}

		//If player busts, displays message
		else
		{
		    message = 3;
		}
	    }
	}

	//Executed when player clicks New Round
	if (e.getSource () == newRoundButton)
	{
	    repaint ();
	    newRound ();

	    chip1Button.setEnabled (true);
	    chip5Button.setEnabled (true);
	    chip10Button.setEnabled (true);
	    chip25Button.setEnabled (true);
	    drawButton.setEnabled (true);
	}
    }


    //Method used to display messages, total hand values, and cards
    public void paint (Graphics g)
    {
	//Displays Screen
	if (screen == 1) //Menu Screen
	{
	    g.drawImage (menuScreen, 0, 0, this);
	}


	if (screen == 2) //Game Screen
	{
	    g.drawImage (gameScreen, 0, 0, this);

	    //Covers message box
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    //Displays message whenever play button selected
	    g.setFont (new Font ("MonoSpaced", Font.BOLD, 12));
	    g.setColor (Color.BLACK);
	    g.drawString ("Click the chips to enter your wage, once done click DRAW", 190, 465);

	    //Updates Bank Total and Wage Total
	    g.setFont (new Font ("MonoSpaced", Font.BOLD, 12));
	    g.setColor (Color.WHITE);
	    g.drawString ("$ " + totalChips, 55, 440);
	    g.drawString ("$ " + bankTotal, 55, 466);

	}
	else if (screen == 3) //Help Screen
	{
	    g.drawImage (helpScreen, 0, 0, this);
	}

	//Messages

	//Displayed after draw is clicked, tells options for player
	if (message == 1)
	{
	    //Covers previous messages
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("You can now either, HIT, STAND, or DOUBLE", 245, 465);
	}

	//Displayed whenever new round button is clicked
	if (message == 2)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("Click the chips to enter your wage, once done click DRAW", 190, 465);

	}

	//Displayed whenever player loses the round
	if (message == 3 || bust == true)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("LOSE: Sorry, you lost the round", 300, 465);

	}

	//Displayed whenever player wins the round
	if (message == 4 || bustDealer == true)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("WIN: Congratulations, you won the round", 250, 465);
	}

	//Displayed whenever their is a draw
	if (message == 5)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("PUSH: Its a draw!", 350, 465);
	}

	//Displays whenver the player does not even enough chips to continue playing at the table
	if (message == 6)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("You dont have enough chips to continue playing at the table.", 173, 465);
	}

	//Displays when the player first two cards is 21
	if (message == 7)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("BLACKJACK: a hand value of 21 on the draw! you WIN the round", 173, 465);
	}

	//Displays when the player has 5 cards up with a total hand value of 21 or less
	if (message == 8)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("5 Card Charlie: 5 cards up without busting! you WIN the round", 170, 465);
	}

	//Error Messages

	//Displays if the user is trying to bet over the max wage, of $50, for the the table
	if (error == 1)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("The maximum wage for this table is $50", 245, 465);

	}

	//Displays if the player tries to draw when their wage is less than the tables minimum of $5
	else if (error == 2)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("You have to bet more than $5", 285, 465);
	}

	//Displays if player tries to bet over the amount they have in the bank
	else if (error == 3)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("Illegal Wager: bet must be less or equal to the amount in your bank", 150, 465);
	}

	//Displays whenever player does not have enough money in the bank to double their wager
	else if (error == 4)
	{
	    g.setColor (Color.WHITE);
	    g.fillRect (150, 450, 470, 20);

	    g.setColor (Color.BLACK);
	    g.drawString ("Illegal Wager: if click Double, bet must be twice as less than bank", 150, 465);
	}

	//Allows cards to be displayed on the table
	if (calledAction == 1 || calledAction == 2 || calledAction == 3)
	{
	    displayBackCard.repaint ();
	    displayCard.repaint ();
	    displayCard2.repaint ();
	    displayCard3.repaint ();
	    displayCard4.repaint ();
	    displayCard5.repaint ();
	    displayCard6.repaint ();
	    displayCard7.repaint ();
	    displayCard8.repaint ();
	    displayCard9.repaint ();
	    displayCard10.repaint ();
	}

	//Hand Value

	//Displays hand value for the players draw hand
	if (calledAction == 1 || finalPlayerHand == totalHandDraw)
	{
	    g.setColor (Color.WHITE);
	    g.drawString (" " + totalHandDraw, 370, 325);
	}

	//Displays total hand value after every hit by the player
	else if (calledAction == 2 || calledAction == 3)
	{
	    if (numCalledHit == 1 || finalPlayerHand == totalHandHit1)
	    {
		g.setColor (Color.WHITE);
		g.drawString (" " + totalHandHit1, 370, 325);
	    }
	    else if (numCalledHit == 2 || finalPlayerHand == totalHandHit2)
	    {
		g.setColor (Color.WHITE);
		g.drawString (" " + totalHandHit2, 370, 325);
	    }
	    else if (numCalledHit == 3 || finalPlayerHand == totalHandHit3)
	    {
		g.setColor (Color.WHITE);
		g.drawString (" " + totalHandHit3, 370, 325);
	    }
	}

	//Continues to display dealers face up card, while its still the players turn
	if (calledAction == 1 || calledAction == 2)
	{
	    g.setColor (Color.WHITE);
	    g.drawString (" " + dealerFirstCard, 370, 35);
	}

	//Displays dealers total hand value
	else if (calledAction == 3)
	{
	    //Displays everytime dealer hits
	    if (numCalledStand == 1)
	    {
		g.setColor (Color.WHITE);
		g.drawString (" " + totalDealerDraw, 370, 35);
	    }
	    else if (numCalledStand == 2)
	    {
		g.setColor (Color.WHITE);
		g.drawString (" " + totalDealerHit1, 370, 35);
	    }
	    else if (numCalledStand == 3)
	    {
		g.setColor (Color.WHITE);
		g.drawString (" " + totalDealerHit2, 370, 35);
	    }
	    else if (numCalledStand == 4)
	    {
		g.setColor (Color.WHITE);
		g.drawString (" " + totalDealerHit3, 370, 35);
	    }
	}

    } // paint method
} // Blackjack class


