// At least one eater eventually starts eating.
// Liveness
// ltl {<> (eat == 1)}

// Nobody is eating indefinitely often (in other words, there is no guarantee that the next meal will happen).
// Safety
// ltl {!([]<> (eat == 1))}

// It is possible to reach a situation when nobody is eating.
// Liveness
// ltl {<> (eat == 0)}

int free = 0 // free sticks
int eat = 0 // how many processes eat

active[2] proctype Eater() {
    int my = 0 // my sticks

    // The process with the pid 0 starts out eating
    if
    :: _pid == 0 -> my = 2;
    :: else
    fi

    do
    :: free >= 1 -> atomic{ free--; my++ }
    :: free == 0 && my == 1 -> atomic{ my--; free++ }
    :: else-> eat++;
        eat--;
        atomic{ free = 2; my = 0 }
    od
}