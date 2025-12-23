
// Elevator returns to the state "down with doors open" indefinitely often.
// Liveness
// ltl {[]<> (state == 1 && doors_open)}

// When the doors are open, the elevator is either in the "up" or "down" state.
// Safety
// ltl { [](doors_open -> (state == 1 || state == 3))}

// Once the doors are closed, eventually the elevator will start moving.
// Liveness
// ltl { !doors_open -> <>(state == 2 || state == 4)}

bool doors_open = true;
int state = 1;

// 1 = down, 2 = moving up, 3 = up, 4 = moving down

active proctype main()
{
do
:: state == 1 && doors_open -> doors_open = false; state = 2;
:: state == 2 -> state = 3;
:: state == 3 && !doors_open -> doors_open = true;
:: state == 3 && doors_open -> doors_open = false; state = 4;
:: state == 4 -> state = 1;
:: state == 1 && !doors_open -> doors_open = true;
od;
} 
