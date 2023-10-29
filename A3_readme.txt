How to run code: "gradle clean build run".


Features which have been implemented in the extension:


- Difficulty Level: There are three levels: Easy, Medium and Hard. The default level when the game starts is "Easy", player can switch back and forth between three levels by pressing corresponding keyboard keys: <1> for the Easy level, <2> for the Medium one and <3> for the Hard one. 

Note that each time the player switches to a level (even with the same level), the time and score will be reset to 0, as if the user has restarted the game.


- Time and Score: The game is clocked with format "0:00" where the first "0" indicates minutes and the second "00" indicates seconds. The time is shown on the upper left corner of the screen and the score is shown on the upper right corner. The clock will stop if either the player destroys all enemies (won the game) or the player is destroyed (lose the game).


- Undo: Each time the player shoots (i.e., pressing the spacebar), the current state of the game (game configuration, time, score, player's position, player's projectiles (excluding the last shot), enemies, enemies positions, enemies projectiles, bunkers and bunkers states) will be saved by the game. Also, there is only one state, the subsequent saves will overwrite the existing one.

The player can undo by pressing the <R> key on the keyboard.

Note that the game allows player to undo between different levels. For example, the player is playing at "Easy" level with "3:00" clock and 12 scores, then he accidentally presses the <2> key and goes to level 2 with "0:00" clock and 0 score. Now, as long as the player does not shoot (i.e., pressing the spacebar), he can press <R> to goes back to level 1 with time and score before his last shot ("2:55" clock and 11 score for example, depending on the time of his last shot and the types of objects that he hit (or missed)).

The player cannot save nor undo if the player loses the game.


- Cheat: There are four cheating options.

Pressing <A> will remove all slow projectiles on the game screen and add 1 to the player's score for each slow projectile being removed.

Pressing <S> will remove all fast projectiles on the game screen and add 2 to the player's score for each fast projectile being removed.

Pressing <D> will remove all slow enemies on the game screen and add 3 to the player's score for each slow enemies being removed.

Pressing <F> will remove all fast enemies on the game screen and add 4 to the player's score for each fast enemies being removed.

The player cannot cheat if the player loses the game.


Design Patterns:


- Prototype:

Client is the "ConfigReader" class, Prototype is the "Cloneable" interface, Concrete Prototype is the "Level" class.


- Observer:

Subject is the "Subject" interface. Concrete Subject is the "GameEngine" class.
Observer is the "Observer" interface. Concrete observers are the "Clock" and "Score" classes.


- Memento:

Originators are the "GameEngine", "Clock", and "Score" classes.
Mementos are the "GameEngineMemento", "ClockMemento", and "ScoreMemento" classes.
CareTaker is the "CareTakerImp" class.
