#define N 10
#define P 2

int workersRunning = P
int shouldCollect = 0;
bool canWork = false;

int i = 0;
int A[N];
int R[N];

active proctype main(){
	int main_i = 0;
	printf("Filling array\n");
	do
	:: main_i >= N ->break;
	:: else -> A[main_i] = (main_i % (P + 1)); printf("%d ", A[main_i]); main_i++;
	od
	printf("\n");
	canWork = true;
	workersRunning == 0;
	shouldCollect++;
	
	shouldCollect == P + 1;

	printf("Result:\n");

	main_i = 0;
	do
	:: main_i >= N -> break;
	:: else -> printf("%d ", R[main_i]); main_i++;
	od
	printf("\nDONE\n");
	
}

active [P] proctype worker(){
	canWork == true;
	int p_count = 0;
	int worker_i = 0;
	do
	:: worker_i >= N -> break;
	:: worker_i < N && A[worker_i] == _pid -> p_count++; worker_i++;
	:: else -> worker_i++;
	od

	workersRunning --;
	printf("Worker (%d) waiting to collect...\n", _pid);
	shouldCollect == _pid;
	printf("Worker (%d) collecting\n", _pid);

	worker_i = 0;
	
	do
	:: worker_i >= p_count -> break;
	:: else -> R[i] = _pid;
		i++;
		worker_i++;
	od

	shouldCollect++;
	printf("Worker (%d) collected\n", _pid);
}