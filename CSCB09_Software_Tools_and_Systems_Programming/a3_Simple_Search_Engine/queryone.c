#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <dirent.h>
#include "freq_list.h"
#include "worker.h"


int main(int argc, char **argv) {
	
	char ch;
	char path[PATHLENGTH];
	char *startdir = ".";
    // input parameters check
	while ((ch = getopt(argc, argv, "d:")) != -1) {
		switch (ch) {
			// replace optional parameter "." by a given directory if
			case 'd':
			startdir = optarg;
			break;
			// wrong usage of this program
			default:
			fprintf(stderr, "Usage: queryone [-d DIRECTORY_NAME]\n");
			exit(1);
		}
	}

	// Open the directory provided by the user (or current working directory)
	DIR *dirp;
	if ((dirp = opendir(startdir)) == NULL) {
		perror("opendir");
		exit(1);
	}
	
	
	/* For each entry in the directory, eliminate . and .., and check
	* to make sure that the entry is a directory, then call run_worker
	* to process the index file contained in the directory.
 	* Note that this implementation of the query engine iterates
	* sequentially through the directories, and will expect to read
	* a word from standard input for each index it checks.
	*/	
	struct dirent *dp;
	// dp means directory pointer
	while ((dp = readdir(dirp)) != NULL) {
        // ignore all hidden and svn files
		if (strcmp(dp->d_name, ".") == 0 ||
		    strcmp(dp->d_name, "..") == 0 ||
		    strcmp(dp->d_name, ".svn") == 0){
			continue;
		}
		// current working file path to this directory
		strncpy(path, startdir, PATHLENGTH);
		// append "/" to the path to this directory
		strncat(path, "/", PATHLENGTH - strlen(path) - 1);
		// append current file name to the path to this directory
		strncat(path, dp->d_name, PATHLENGTH - strlen(path) - 1);
        // permission check
		struct stat sbuf; // create a struct of stat (shell command) 
		                  // which display all info about a file
		                  // and name it sbuf
		if (stat(path, &sbuf) == -1) {
			//This should only fail if we got the path wrong
			// or we don't have permissions on this entry.
			perror("stat");
			exit(1);
		} 
		// Only call run_worker if it is a directory
		// Otherwise ignore it.
		if (S_ISDIR(sbuf.st_mode)) {
			//create pipe and fork
			//Initialization (the same as queryone)
			// Create one process for each subdirectory (of either the current working directory or the directory passed as a command line argument to the program)
			// while(1)
			// read a word from stdin (it is okay to prompt the user)
			// using pipes, write the word to each worker process
			// while(workers still have data)
			// read one FreqRecord from each worker and add it to the master frequency array
			// print to standard output the frequency array in order from highest to lowest
			// The master process will not terminate until all of the worker processes have terminated.
			printf("the currect subdirectory is %s\n", path);
			run_worker(path, STDIN_FILENO, STDOUT_FILENO);
		}
	}
	return 0;
}
