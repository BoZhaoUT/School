#define PATHLENGTH 128
#define MAXRECORDS 100

// This data structure is used by the workers to prepare the output
// to be sent to the master process.


typedef struct {
	int freq;
	char filename[PATHLENGTH];
} FreqRecord;

void print_freq_records(FreqRecord *frp);
void run_worker(char *dirname, int in, int out);
FreqRecord* init_master_records_array();
void manage_freq_records(FreqRecord *new_record, FreqRecord *master_array);
