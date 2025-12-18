int choice_A = 0;
int choice_B = 0;

int madeChoice = 0;

active proctype main(){
	
	// Heads -> 1
	// Tails    -> 2
	int coinChoice = 0;

	if
	:: true -> coinChoice = 1;
	:: true -> coinChoice = 2;
	fi

	madeChoice == 2;

	if
	:: choice_A == choice_B && choice_A == coinChoice -> printf("Draw\n");
	:: else -> if
		:: choice_A == coinChoice -> printf("The winner is A\n");
		:: choice_B == coinChoice -> printf("The winner is B\n");
		:: else -> printf("No one wins\n");
		fi
	fi
}

active proctype player_A(){
	if
	:: true -> choice_A = 1;
	:: true -> choice_A = 2;
	fi
	madeChoice++;
}

active proctype player_B(){
	if
	:: true -> choice_B = 1;
	:: true -> choice_B = 2;
	fi
	madeChoice++;
}