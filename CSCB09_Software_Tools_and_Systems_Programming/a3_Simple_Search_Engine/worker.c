#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <dirent.h>
#include "freq_list.h"
#include "worker.h"


/* Return an array of frequency records with length of MAXRECORD + 1.
* The frequency of all frequency records are 0.
*/
FreqRecord* init_master_records_array() {
    int i;
    FreqRecord *result = malloc((MAXRECORDS + 1) * sizeof(FreqRecord));
    if (result == NULL) {
        perror("malloc failed");
        exit(1);
    }
    for (i = 0; i < (MAXRECORDS + 1); i++) {
        result[i].freq = 0;
    }
    return result;
}


/* Add new_record into master_array. Keep master_array sorted in decreasing order.
* If element in master_array is greater than MAXRECORD + 1, then the frequency
* record with smallest non-zero frequency is dropped. The last frequency record
* in master_array has the frequency value of 0.
*/
void manage_freq_records(FreqRecord *new_record, FreqRecord *master_array) {
    int i = 0;
    int j;
    // find the index of new_record
    while (i < MAXRECORDS && new_record->freq <= master_array[i].freq) {
        i++;
    }
    // if the inde xof new_record is within this array
    if (i < MAXRECORDS) {
        // use insertion sort
        for (j = (MAXRECORDS - 1); j > i; j--){
            master_array[j].freq = master_array[j - 1].freq;
            strncpy(master_array[j].filename, master_array[j - 1].filename, PATHLENGTH);
        }
        master_array[i].freq = new_record->freq;
        strncpy(master_array[i].filename, new_record->filename, PATHLENGTH);
    }
}


/* Return an array of FreqRecord where each FreqRecord contains non-zero
* frequency of argument word and its associate filepath. Added a FreqRecord
* to the end of this array even if no zero-frequency is found. The input
* arguments head is a linked-list of word node, and filenames is a pointer
* to an array filenames.
*/
FreqRecord *get_word(char *word, Node *head, char **filenames) {
	// an array of frequence records
	FreqRecord *result;
    // allocate memory for max number of records and the end of array record
	result = malloc( sizeof(FreqRecord) * (MAXRECORDS + 1));
    if (result == NULL) {
        perror("malloc in get_word failed\n");
        exit(1);
    }
	int keep_searching = 1;
	int compare_words;
	Node *desired_node = NULL; // the node contains word we are looking for
	Node *curr = head;
	int num_record = 0; // count the number of word list
    int num_frequency = 0; // count the number of frequency
    int num_freq_record = 0; // counter the number of freq_record
    int frequency;
	// loop through list nodes and filenames
    while (keep_searching) {
    	// empty list or reach the end of the list
    	if (curr == NULL) {
    		keep_searching = 0;
    	// assume curr is not null
    	} else {
    		compare_words = strncmp(word, curr->word, MAXWORD);
    		// the Node contains word is found
    		if (compare_words == 0) {
    			keep_searching = 0;
    			desired_node = curr;
    		// word cannot be found in the rest of the list
            // because node list is sorted in alphabetical order
    		} else if (compare_words < 0) {
    			keep_searching = 0;
    		} else {
    			curr = curr-> next;
                num_record++;
    		}
    	}
    }
    // if the node contains word is found
    if (desired_node != NULL) {
        // looking for frequency of the word and associate filename
        while (&filenames[num_frequency] != NULL && num_frequency < MAXFILES) {
            frequency = (desired_node->freq)[num_frequency];
            // add the file name with non-zero frequency into result
            if (frequency != 0) {
                result[num_freq_record].freq = frequency;
                strncpy(result[num_freq_record].filename, filenames[num_frequency], PATHLENGTH);
                num_freq_record++;
            }
            num_frequency++;
        }
    }
    // to indicate this is the end of array of FreqRecord
    result[num_freq_record].freq = 0;
    memset(result[num_freq_record].filename, '\0', PATHLENGTH);
    if (num_freq_record != MAXRECORDS) {
        result = realloc(result, sizeof(FreqRecord) * (num_freq_record + 1));
        if (result == NULL) {
            perror("realloc in get_word failed\n");
            exit(1);
        }
    }
	return result;
}


/* Print to standard output the frequency records for a word.
* Used for testing.
*/
void print_freq_records(FreqRecord *frp) {
	int i = 0;
	while(frp != NULL && frp[i].freq != 0) {
		printf("%d    %s\n", frp[i].freq, frp[i].filename);
		i++;
	}
}


/* run_worker
* - load the index found in dirname
* - read a word from the file descriptor "in"
* - find the word in the index list
* - write the frequency records to the file descriptor "out"
*/
void run_worker(char *dirname, int in, int out) {
    Node *head = NULL;
    char **filenames = init_filenames();
    // create a file path to index in dirname
    char list_file[PATHLENGTH];
    strncpy(list_file, dirname, PATHLENGTH);
    strncat(list_file, "/index", PATHLENGTH - strlen(dirname) - 1);
    // create a file path to filenames in dirname
    char name_file[PATHLENGTH];
    strncpy(name_file, dirname, PATHLENGTH);
    strncat(name_file, "/filenames", PATHLENGTH - strlen(dirname) - 1);
    // load index and filenames into a data structure
    read_list(list_file, name_file, &head, filenames);
    int num_bytes;
    char current_word[MAXWORD];
    memset(current_word, '\0', MAXWORD);
    FreqRecord *word_freq_record;
    int counter;
    int write_freq_record; // check wheter writing of a freq record succeed
    int write_last_zero_record; // check wheter writing of the the zero freq record succeed
    // read words from the file descriptor "in"
    while ((num_bytes = read(in, current_word, MAXWORD)) > 0) {
        current_word[num_bytes - 1] = '\0';
        word_freq_record = get_word(current_word, head, filenames);
        counter = 0;
        // loop through the array of freqency record of current word
        while (word_freq_record[counter].freq != 0) {
            write_freq_record = write(out, &word_freq_record[counter], sizeof(FreqRecord));
            if (write_freq_record < 0) {
                perror("write failed");
                exit(1);
            }
            // printf("%d    %s\n", word_freq_record[counter].freq, word_freq_record[counter].filename);
            counter++;
        }
        // write a FreqRecord of 0 frequency to indicate eof
        write_last_zero_record = write(out, &word_freq_record[counter], sizeof(FreqRecord));
        if (write_last_zero_record < 0) {
            perror("write failed");
            exit(1);
        }
        memset(current_word, '\0', MAXWORD);
    }
    if (num_bytes < 0) {
        perror("read failed");
        exit(1);
    }
}
