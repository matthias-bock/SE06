#define N 10

int i = 0;
int A[N];

active [N] proctype initA() {
	atomic {
		A[i] = _pid;
		i = i + 1;
	}
}

active proctype main() {
	i == N;	

	// 0: A
	int numA = A[0];
	// 1: B
	int numB = A[1];
	// 2: S
	int numS = A[2];
	// 3: E
	int numE = A[3];
	// 4: L
	int numL = A[4];
	// 5: M
	int numM = A[5];
	// 6: G
	int numG = A[6];

	int base =	numB * 1000 +
			numA * 100 +
			numS * 10 +
			numE;

	int ball =	numB * 1000 +
			numA * 100 +
			numL * 10 +
			numL;

	int games =	numG * 10000 +
			numA * 1000 +
			numM * 100 +
			numE * 10 +
			numS;

	printf("");

	assert(base + ball != games);
}