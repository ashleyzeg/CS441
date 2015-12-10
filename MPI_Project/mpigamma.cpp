#include <iostream>
#include "mpi.h"
#include <cmath>

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
  
  // get n from the user
  int n;
  if(id==0) // root!
  {
    cout << "Please enter n: ";
	cin >> n;
	while(n%np!=0) n++;// guarantee integer blocksize
  }
  // share n with broadcast
  MPI_Bcast(&n,1,MPI_INT,0,MPI_COMM_WORLD);
  
  /*
  // compute sum with striping
  double sum=0;
  for(int i=id+1; i<=n; i=i+np)
    sum=sum+1.0/i;
  */
  // compute sun with blocking
  double sum=0;
  int B=n/np;
  for(int i=B*id+1; i<=B*id+B; i++)
    sum=sum+1.0/i;
	
  // reduce to get sum to 0
  double bigsum;
  MPI_Reduce(&sum,&bigsum,1,MPI_DOUBLE,
    MPI_SUM,0,MPI_COMM_WORLD);
	
  // finish up with 0
  if(id==0)
  {
    cout << "Gamma="
	  << bigsum-log(n) << endl;
  }
  
  // done!
  MPI_Finalize();
  return 0;
}