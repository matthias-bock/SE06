#define N 10

int searched = 0;
bool startSearch = false;

int largest = 0;

int A[N];

active proctype main(){

	A[0] = 1; A[1] = 0; A[2] = 2; A[3] = 5; A[4] = 1; A[5] = 1; A[6] = 10; A[7] = 1; A[8] = 11; A[9] = 12;
	largest = A[0];
	startSearch = true;

	searched == N;

	printf("The largest number is %d\n", largest);

}

active [N] proctype searcher(){

	startSearch == true;

	atomic{
		if
		:: A[_pid-1] > largest -> largest = A[_pid-1];
		:: else -> ;
		fi
	}

	searched++;
}