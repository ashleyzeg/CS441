#include <iostream>
#include "mpi.h"
/*
thomas:$ module load openmpi-x86_64
thomas:$ mpicxx mpihello.cpp
thomas:$ mpirun -np 10 a.out

use broadcast and reduce for project
reduce on number of primes found and reduce on sum
find primes in an interval and count how many primes there are
*/

using namespace std;

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

  //get the name of the processor
  char name[MPI_MAX_PROCESSOR_NAME];
  int name_len;
  MPI_Get_processor_name(name, &name_len);
  
  cout << "I am "<<id<<" of "<<np<<endl;
  cout << "processor name: "<<name<<endl;
  
  cout << "Hello world!\n";
  
  // done!
  MPI_Finalize();
  return 0;
}