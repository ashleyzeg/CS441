/*
Ashley Zegiestowsky
CS441 Organization of Programming Languages
Jon Sorenson
December 4, 2015
MPI Program: This program uses MPI to count the number of primes up to n 
(and in the sample run n=1 billion). The work is divided amongst the processors using chunking,
and MPI_Bcast and MPI_Reduce are used to communicate between the different processors

Note: Program functions correctly, but the way in which primality is checked could be optimized
to allow the program to run faster
*/

#include <iostream>
#include "mpi.h"
#include <cmath>

using namespace std;

//function checks for primality and returns true if prime, and false if not prime
bool isPrime(int num){
  if (num <= 1) return false;
  if (num == 2) return true;
  if (num%2 == 0) return false;
  int sr = sqrt(num);

  for (int j=3; j<=sr; j+=2) {
    if (num%j == 0) return false;
  }

  return true;
}

int main(int argc, char *argv[])
{
  // start up MPI
  MPI_Init(&argc,&argv);
  
  // get number of processors
  int np;
  MPI_Comm_size(MPI_COMM_WORLD, &np);

  // get my ID number
  int id;
  MPI_Comm_rank(MPI_COMM_WORLD, &id);
  
  // get n from the user
  int n;
  if(id==0) // root!
  {
    cout << "Please enter n: ";
    cin >> n;
  while(n%np!=0) n++;// guarantee integer blockSize
  }

  // share n with broadcast
  MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);
  
  //sum stores the sum for each interval of size n/np
  int sum=0;
  int block = n/np;
  bool prime;

  //loops through each block of integers and calls isPrime function
  //to check for primality
  for(int i=block*id+1; i<=block*id+block; i++) {
    prime = isPrime(i);
    if (prime) sum=sum+1;
  }

  //totalsum stores the sum of all of the sums from np processors
  int totalsum=0;
  MPI_Reduce(&sum, &totalsum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

  // finish up with 0
  if(id==0)
    cout << "Number of primes from 1 to "<<n<<" is "<< totalsum << endl;
  
  // done!
  MPI_Finalize();
  return 0;
}

/*
Sample Run
thomas% module load openmpi-x86_64
thomas% mpicxx mpiprimes.cpp
thomas% mpirun -np 100 a.out
Please enter n: 1000000000
Number of primes from 1 to 1000000000 is 50847534
*/